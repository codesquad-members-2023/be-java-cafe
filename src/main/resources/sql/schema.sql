Drop table if exists article;
Drop table if exists member;

CREATE TABLE member
(
    id           bigint AUTO_INCREMENT primary key,
    user_name    varchar(255) not null,
    password     varchar(255) not null,
    email        varchar(255) not null,
    created_date timestamp default now()
);

CREATE TABLE article
(
    id           bigint AUTO_INCREMENT primary key,
    member_id    bigint,
    writer       varchar(255),
    title        varchar(255),
    content      text,
    created_date timestamp default now(),
    reply_count  integer   default 0,
    FOREIGN KEY (member_id) REFERENCES member (id)
);
