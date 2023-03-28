drop table if exists article;
drop table if exists member;

create table member
(
    member_number   bigint      not null auto_increment,
    member_id       varchar(64) not null,
    member_password varchar(64) not null,
    member_name     varchar(64) not null,
    member_email    varchar(64) not null,
    primary key (member_id, member_number)
);

create table article
(
    article_number      bigint        not null auto_increment,
    article_writer      varchar(64)   not null,
    article_title       varchar(200)  not null,
    article_contents    varchar(1000) not null,
    article_writtentime timestamp     not null default current_timestamp(),
    primary key (article_number)

);
