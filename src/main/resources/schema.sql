create table articles
(
article_id bigint auto_increment primary key,
writer varchar(255) not null,
title varchar(255) not null,
content varchar(1000) not null,
creation_time timestamp not null default current_timestamp
);
create table members
(
id varchar(255) primary key,
password varchar(255),
email varchar(320),
name varchar(255)
);
