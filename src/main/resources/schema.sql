DROP TABLE IF EXISTS Article;

DROP TABLE IF EXISTS MEMBER;

CREATE TABLE MEMBER
(
    userId   VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    userName VARCHAR(255) NOT NULL,
    email    VARCHAR(255),
    PRIMARY KEY (userId)
);

CREATE TABLE Article
(
    id         bigint auto_increment,
    author_id  varchar(50)   NOT NULL,
    title      varchar(30)   NOT NULL,
    contents   varchar(3000) NOT NULL,
    write_date timestamp     NOT NULL default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) references member (userId)
);
