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
    id_user int NOT NULL,
    FOREIGN KEY (id_user) REFERENCES users(id),
    PRIMARY KEY (id_user, id)
);

-- helloimpassword
INSERT INTO users(login, password, salt) values ("michel", "Jzb8qmD1Go0x0rzh28Lv6Q==", "4459682Fw8FcXWxRug3e0qzktQhGIwKX6jgEPXXlvXhGsbQwsdEW8UhHX2rhVpH4gTiCx+lPGtV+He1MlD/OgC8i2+mwFRqSahbJ6aCtYDMiaIFgXCpg7xMlBaPoqa7c2eFPBH1qjBsUsqISXGueHGSVuTKTJaKDYNF24p0BtaA="); 
-- helloagain
INSERT INTO users(login, password, salt) values ("boris", "cPeRtBAUqkAqX49j0o8HMA==", "yeIsSBs1MVyiAApUCOu1HBKmlqAv8li3v6KLZiCREM9MrVM8UXf31VvrfBiaLUWkYw+8NpOaVpoeRi7TbiSkCsCkkVUaPH8o11PO14ZpUU673XnuV975FOCKRKp1NstZpzlvjlqM2JtgAzTS6grqcRpV3MMSVPs8IePRn8nVuWI=");


INSERT INTO location(id, longitude, latitude, id_user) VALUES (1, 45, 24, 1);
INSERT INTO location(id, longitude, latitude, id_user) VALUES (2, 24, 45, 1);
INSERT INTO location(id, longitude, latitude, id_user) VALUES (3, 75, 89, 1);
INSERT INTO location(id, longitude, latitude, id_user) VALUES (1, 45, 24, 2);
INSERT INTO location(id, longitude, latitude, id_user) VALUES (2, 65, 123, 2);

