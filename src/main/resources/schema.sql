DROP TABLE IF EXISTS CAFEUSER, ARTICLE;

CREATE TABLE CAFEUSER (
                          USER_ID VARCHAR PRIMARY KEY,
                          PASSWORD VARCHAR NOT NULL,
                          NAME VARCHAR NOT NULL,
                          EMAIL VARCHAR NOT NULL
);

CREATE TABLE ARTICLE (
                         ARTICLE_ID INT PRIMARY KEY,
                         USER_ID VARCHAR NOT NULL,
                         TITLE VARCHAR NOT NULL,
                         CONTENTS VARCHAR NOT NULL,
                         TIME VARCHAR NOT NULL
);
