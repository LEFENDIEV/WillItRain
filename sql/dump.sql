#CREATE DATABASE sparkdb;

USE sparkdb;

CREATE TABLE users (
     id int  NOT NULL,
     name VARCHAR(255),
     PRIMARY KEY (id)
);              

CREATE TABLE locations (
    id int NOT NULL,
    posx FLOAT,
    posy FLOAT,
    id_user int NOT NULL,
    PRIMARY KEY (id_user, id),
    FOREIGN KEY (id_user) REFERENCES users(id)
);
