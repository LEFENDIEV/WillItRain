#CREATE DATABASE sparkdb;

USE sparkdb;

CREATE TABLE users (
     id int  NOT NULL,
     name VARCHAR(255),
     PRIMARY KEY (id)
);              

CREATE TABLE location (
    id int NOT NULL,
    longitude FLOAT,
    latitude FLOAT,
    id_user int NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id),
    PRIMARY KEY (id_user, id)
);
