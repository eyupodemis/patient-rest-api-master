CREATE TABLE patient (
     ID int NOT NULL AUTO_INCREMENT,
     FHIRURL varchar(255) NOT NULL,
     FAMILY varchar(255) NOT NULL,
     GIVEN varchar(255) NOT NULL,
     PREFIX varchar(255),
     SUFFIX varchar(255),
     GENDER varchar(255),
     BIRTHDATE varchar(255),
     PRIMARY KEY (ID),
     INDEX (FHIRURL)
    );