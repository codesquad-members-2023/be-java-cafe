create table users
(
    id       int         primary key auto_increment ,
    user_id  varchar(30) not null unique,
    password varchar(30) not null,
    name     varchar(30) not null,
    email    varchar(30) not null
);

create table article
(
    id         int         primary key auto_increment,
    title      varchar(30) not null,
    contents   varchar(50) not null,
    createDate timestamp,
    user_id    int,
    foreign key (user_id) references users (id)
)
