drop table if exists USERS;
create table USERS  (
                        userId varchar(20) not null,
                        password varchar(20) not null,
                        name varchar(20) not null,
                        email varchar(20) not null,
                        primary key(userId)
);

drop table if exists ARTICLES;
create table ARTICLES  (
                           articleId int auto_increment,
                           writer varchar(20) not null,
                           title TINYTEXT not null,
                           contents TEXT not null,
                           timeStamp TIMESTAMP default current_timestamp,
                           primary key(articleId)
);
