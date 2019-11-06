#CREATE DATABASE sparkdb;

USE sparkdb;
#GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'pass' WITH GRANT OPTION;
FLUSH PRIVILEGES;
CREATE USER 'myuser'@'localhost' IDENTIFIED BY 'mypass';
CREATE USER 'myuser'@'%' IDENTIFIED BY 'mypass';
GRANT ALL ON *.* TO 'myuser'@'localhost';
GRANT ALL ON *.* TO 'myuser'@'%';
flush privileges;

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
    id_city int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_city) REFERENCES city(id)
    FOREIGN KEY (id_user) REFERENCES users(id)
);

CREATE TABLE city (
    id int NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);
