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
);

insert into users (user_id, password, name, email) values ('Hyun', '1234', '황현', 'ghkdgus29@naver.com');
insert into users (user_id, password, name, email) values ('Yoon', '4321', '황윤', 'ghkddbs28@naver.com');

insert into article (writer, title, contents, createdate) values ('Hyun', '실화냐?', '미안하다 이거 보여주려고 어그로 끌었다.', current_timestamp());
insert into article (writer, title, contents, createdate) values ('Yoon', '엥?', '미안하다 이거 보여주려고 또 어그로 끌었다.', current_timestamp());
