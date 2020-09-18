package com.dedalus.restapi.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "patient")
@NamedQueries({
        @NamedQuery(name = "Patient.findAll", query = "SELECT t FROM Patient t"),
        @NamedQuery(name = "Patient.findByFhirUrl", query = "SELECT t FROM Patient t WHERE t.fhirUrl = :fhirUrl")
})
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fhirUrl;
    private String family;
    private String given;
    private String prefix;
    private String suffix;
    private String gender;
    private String birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFhirUrl() {
        return fhirUrl;
    }

    public void setFhirUrl(String fhirUrl) {
        this.fhirUrl = fhirUrl;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", fhirUrl='" + fhirUrl + '\'' +
                ", family='" + family + '\'' +
                ", given='" + given + '\'' +
                ", prefix='" + prefix + '\'' +
                ", suffix='" + suffix + '\'' +
                ", gender='" + gender + '\'' +
                ", birthDate='" + birthDate + '\'' +
                '}';
    }
}
