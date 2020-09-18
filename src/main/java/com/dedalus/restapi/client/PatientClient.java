package com.dedalus.restapi.client;

import com.dedalus.restapi.dao.PatientDAO;
import com.dedalus.restapi.dto.NameDto;
import com.dedalus.restapi.dto.PatientDto;
import com.dedalus.restapi.model.Patient;
import sun.security.provider.certpath.OCSPResponse;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.nio.file.OpenOption;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@RequestScoped
@Path("patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientClient {

    @Inject
    private PatientDAO patientDAO;
    private final Client client = ClientBuilder.newClient();

    @GET
    public Response getAll() {
        return Response.ok(patientDAO.getAll()).build();
    }

    @GET
    public Response getPatient(@QueryParam("fhirUrl") String fhirUrl) {
        Optional<Patient> patient = patientDAO.findByFhirUrl(fhirUrl);

        if(patient.isPresent()) {
            return Response.ok(patient.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(@QueryParam("fhirUrl") String fhirUrl) {
        System.out.println("Incoming parameter : " + fhirUrl);
        Response response = getPatientData(fhirUrl);

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            PatientDto patientData = response.readEntity(PatientDto.class);

            Patient patient =  patientDAO.findByFhirUrl(fhirUrl).orElseGet(Patient::new);
            Response.Status responseStatus = patient.getId() == null? Response.Status.CREATED :
                    Response.Status.OK;

            patient.setFhirUrl(fhirUrl);
            patient.setBirthDate(patientData.getBirthDate());
            patient.setGender(patientData.getGender());

            setParams(patientData, NameDto::getFamily, patient::setFamily);
            setParams(patientData, NameDto::getGiven, patient::setGiven);
            setParams(patientData, NameDto::getPrefix, patient::setPrefix);
            setParams(patientData, NameDto::getSuffix, patient::setSuffix);

            patientDAO.update(patient);

            return Response.status(responseStatus).build();
        } else {
            response.close();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void setParams(PatientDto patientData, Function<NameDto, List<String>> nameList, Consumer<String> consumer) {
        Optional.ofNullable(patientData.getName())
                .map(names -> names.get(0))
                .map(nameList)
                .map(familyNames -> familyNames.get(0))
                .ifPresent(consumer);
    }

    private Response getPatientData(String fhirUrl) {
        return client
                .target(fhirUrl)
                .request()
                .accept("application/fhir+json")
                .get();
    }
}
