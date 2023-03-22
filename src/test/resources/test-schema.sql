CREATE TABLE ARTICLES
(
writer VARCHAR(255),
title VARCHAR(255),
contents VARCHAR(500),
id int AUTO_INCREMENT,
creationtime timestamp with time zone,
PRIMARY KEY (id)
);

CREATE TABLE USERS
(
id VARCHAR(255),
password VARCHAR(255),
name VARCHAR(255),
email VARCHAR(255),
idx INT AUTO_INCREMENT,
PRIMARY KEY (idx)
)
