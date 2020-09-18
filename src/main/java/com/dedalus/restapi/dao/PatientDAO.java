package com.dedalus.restapi.dao;


import com.dedalus.restapi.model.Patient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Stateless
public class PatientDAO {
    @PersistenceContext(unitName = "restapi_PU")
    private EntityManager em;

    public List getAll() {
        return em.createNamedQuery("Patient.findAll", Patient.class).getResultList();
    }

    public Optional<Patient> findByFhirUrl(String fhirUrl) {
        Query query = em.createNamedQuery("Patient.findByFhirUrl");
        query.setParameter("fhirUrl", fhirUrl);

        try {
            return Optional.ofNullable((Patient) query.getSingleResult());
        } catch (NoResultException e) {
           return Optional.empty();
        }
    }

    public void create(Patient patient) {
        em.persist(patient);
    }

    public void update(Patient patient) {
        em.merge(patient);
    }
}
