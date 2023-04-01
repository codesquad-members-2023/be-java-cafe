Drop table if exists reply;
Drop table if exists article;
Drop table if exists member;

CREATE TABLE member
(
    id           bigint AUTO_INCREMENT primary key,
    userName    varchar(64) not null unique ,
    password     varchar(64) not null,
    nickName varchar(64) not null,
    email        varchar(64) not null
);

CREATE TABLE article
(
    id           bigint AUTO_INCREMENT primary key,
    member_id    bigint,
    writer       varchar(64),
    title        varchar(255),
    content      text,
    created_date timestamp default now(),
    reply_count  integer   default 0,
    FOREIGN KEY (member_id) REFERENCES member (id)
);

CREATE TABLE reply
(
    id   bigint AUTO_INCREMENT primary key,
    member_id  INT NOT NULL,
    article_id INT NOT NULL,
    content TEXT,
    reply_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted boolean not null,
    FOREIGN KEY (member_id) REFERENCES member (id),
    FOREIGN KEY (article_id) REFERENCES article (id)
);
