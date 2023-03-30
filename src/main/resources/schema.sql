DROP TABLE IF EXISTS REPLY;

DROP TABLE IF EXISTS ARTICLE;

DROP TABLE IF EXISTS MEMBER;

CREATE TABLE MEMBER
(
    userId   VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    userName VARCHAR(255) NOT NULL,
    email    VARCHAR(255),
    PRIMARY KEY (userId)
);

CREATE TABLE ARTICLE
(
    id         bigint auto_increment,
    author_id  varchar(50)   NOT NULL,
    title      varchar(30)   NOT NULL,
    contents   varchar(3000) NOT NULL,
    write_date timestamp     NOT NULL default current_timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) references MEMBER (userId)
);

CREATE TABLE REPLY
(
    reply_id   bigint auto_increment,
    article_id bigint        NOT NULL,
    writer     varchar(30)   NOT NULL,
    contents   varchar(3000) NOT NULL,
    write_time timestamp     NOT NULL default current_timestamp,
    PRIMARY KEY (reply_id),
    FOREIGN KEY (article_id) references ARTICLE (id),
    FOREIGN KEY (writer) references MEMBER (userId)
);

