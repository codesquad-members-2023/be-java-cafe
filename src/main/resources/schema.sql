drop table if exists article;
drop table if exists member;


create table member
(
    member_number   bigint auto_increment primary key,
    member_id       varchar(64) not null unique,
    member_password varchar(64) not null,
    member_name     varchar(64) not null,
    member_email    varchar(64) not null
);

create table article
(
    article_number      bigint auto_increment primary key,
    article_title       varchar(200)  not null,
    article_contents    varchar(1000) not null,
    article_writtentime timestamp     not null default current_timestamp(),
    member_number       bigint,
    foreign key (member_number) references member (member_number)
);


