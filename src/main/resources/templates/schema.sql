drop table if exists member;

create table member (
    id bigint auto_increment primary key,
    email varchar(255),
    nickName varchar(255) not null,
    password varchar(255) not null,
    signUpDate Date
);