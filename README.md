# Dedalus Patient Processing 
This is an entity service which stores and retrieves patient data.


## Rest Service details
`GET http://localhost:8080/rest-api-1.0/api/patients?fhirUrl=https://fhir-test.hl7.at/baseDstu2/Patient/16517` - Get single patient record with json format like below;
`
```
    {
       "given": "Thomas",
       "fhirUrl": "https://fhir-test.hl7.at/baseDstu2/Patient/16517",
       "gender": "male",
       "id": 1,
       "family": "David",
       "birthDate": "1965-12-21"
   }
```




`POST http://localhost:8080/rest-api-1.0/api/patients?fhirUrl=https://fhir-test.hl7.at/baseDstu2/Patient/16517` - ADD a patient record to database;
`
- If Receord does not exist, it creates new record and returns 201 status code
- If Record is exist, it makes merge and returns 200 status code 

## Schema/Table

A dockerized Mysql database is created.
Database objects details like below;
```Schema name: myDb
   Schema User: root
   Password: root
   Table name: patient
   Table structure: Check the file src/main/resourcesdb-scripts/create-patient-table-scripts.sql
   
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
```


### sh file(start-application.sh)
Purpose of sh file is to initiate the dockers and to create dockerized mysql container 
and dockerized tomcat server with java code.
If the docker images exists it will skip to download, otherwise it will download all necessary docker images.


## How run the service

Inside the project root directory on the command line, run the command below; 
```    
    ./start-application.sh 
    
```

