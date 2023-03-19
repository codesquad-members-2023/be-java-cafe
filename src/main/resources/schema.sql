DROP TABLE IF EXISTS MEMBER;
CREATE TABLE MEMBER
(
    user_id  VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name   VARCHAR(255),
    email    VARCHAR(255),
    PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS Article;
CREATE TABLE Article
(
    id  bigint NOT NULL auto_increment,
    writer varchar(50) NOT NULL,
    title varchar(30) NOT NULL,
    contents varchar(3000) NOT NULL,
    write_date timestamp NOT NULL,
    PRIMARY KEY (id)
);
