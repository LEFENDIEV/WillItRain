#CREATE DATABASE sparkdb;

USE sparkdb;

CREATE TABLE users (
     id int  NOT NULL AUTO_INCREMENT,
     login VARCHAR(255),
     password VARCHAR(255),
     salt VARCHAR(255),
     PRIMARY KEY (id)
);              

CREATE TABLE location (
    id int NOT NULL,
    longitude FLOAT,
    latitude FLOAT,
    formatted_adress VARCHAR(255),
    id_user int NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id),
    PRIMARY KEY (id_user, id)
);

-- michel 
INSERT INTO users(login, password, salt) values ("michel", "9gRPTCPqQPydcZEq9WZckQ==", "tvb0yyQdkEssW02LCSBMSVw3PiFXPheL8H0d5UfrUtlokz4A9GOYAoRRUd2QGPaiiwNe+TUMIW80abBbFDVb3OVAuNChkVo6Im3oY6jbUt+TfFvstINsXrekJ3DFPehiSB/UPCBloZvjDH1hgaAszXJilBJKVy6iX1ylg/pGaBY="); 
-- borisss
INSERT INTO users(login, password, salt) values ("boris", "VMD/YzA71YUug4C1vt2TIA==", "IUobPjGUiwwj9pVczxd173vajAZmcgLqsvcpNWoV9TvQpyweK10EjR0f0dRH0Cl2m/N8wXLXIbhlT1nxEFeXvrizHogkhQCrR73+pi4cyw467oUIHPcKa1eQmwXWmDwLEmEqvIzEG3e1mY/QiGzQDG5TBwfAGM+h1ampTDhsoZk=");


INSERT INTO location(id, longitude, latitude, formatted_adress, id_user) VALUES (1, 42.145424, 24.758526, "fsdjflsdf6456q", 1);
INSERT INTO location(id, longitude, latitude, formatted_adress, id_user) VALUES (2, 42.138829, 24.768332,"6465sd1fsd1f6 sdf sdf 4651", 1);
INSERT INTO location(id, longitude, latitude, formatted_adress, id_user) VALUES (3, 44.205375, 17.904493,"fdsfs dfsdf sdf sdf sdf", 1);
INSERT INTO location(id, longitude, latitude, formatted_adress, id_user) VALUES (1, 40.814869, -73.127136," fsdf  ", 2);
INSERT INTO location(id, longitude, latitude, formatted_adress, id_user) VALUES (2, 10.501565, 107.171710,"test qdress" , 2);

