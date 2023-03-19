create table users
(
    id       int         not null auto_increment primary key,
    user_id  varchar(30) not null,
    password varchar(30) not null,
    name     varchar(30) not null,
    email    varchar(30) not null
);

create table article
(
    id         int         not null auto_increment primary key,
    writer     varchar(30) not null,
    title      varchar(30) not null,
    contents   varchar(50) not null,
    createDate timestamp
)