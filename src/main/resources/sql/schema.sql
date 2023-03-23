Drop table if exists member;

CREATE TABLE member
(
    id           bigint AUTO_INCREMENT primary key,
    user_name    varchar(255) not null,
    password     varchar(255) not null,
    email        varchar(255) not null,
    created_date timestamp default now()
);

Drop table if exists article;

CREATE TABLE article
(
    id bigint AUTO_INCREMENT primary key,
    writer varchar(255),
    title varchar(255),
    content text,
    created_date timestamp default now(),
    reply_count integer default 0
);
