drop table if exists article;
drop table if exists member;

create table member (
    userid varchar(64) primary key ,
    password varchar(64) not null,
    name varchar(64) not null,
    email varchar(64) not null
);


create table article (
    id int primary key auto_increment,
    userid varchar(64) not null,
    title varchar(64) not null,
    contents varchar(300) not null,
    timestamp timestamp not null,
    foreign key (userid) references member(userid)
);
