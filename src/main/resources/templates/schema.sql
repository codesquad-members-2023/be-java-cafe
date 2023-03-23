drop table if exists member, article;

create table member (
    id bigint auto_increment primary key,
    email varchar(255),
    nickName varchar(255) not null,
    password varchar(255) not null,
    signUpDate Date
);


create table article (
    articleId bigint auto_increment primary key,
    writer varchar(255),
    title varchar(255),
    contents text,
    registrationDate Date
);