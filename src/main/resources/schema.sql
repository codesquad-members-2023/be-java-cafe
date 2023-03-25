drop table if exists member;

create table member (
    userid varchar(64) primary key ,
    password varchar(64) not null,
    name varchar(64) not null,
    email varchar(64) not null
);

drop table if exists article;

create table article (
    id int primary key auto_increment,
    writer varchar(64) not null,
    title varchar(64) not null,
    contents varchar(300) not null,
    timestamp timestamp not null
);
