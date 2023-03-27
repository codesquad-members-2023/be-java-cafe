drop table if exists REPLIES;
drop table if exists ARTICLES;
drop table if exists USERS;

create table USERS
(
    userId   varchar(20) not null,
    password varchar(20) not null,
    name     varchar(20) not null,
    email    varchar(20) not null,
    primary key (userId)
);
create table ARTICLES
(
    articleId int auto_increment,
    writer    varchar(20) not null,
    title     TINYTEXT    not null,
    contents  TEXT        not null,
    timeStamp TIMESTAMP default current_timestamp,
    primary key (articleId),
    foreign key (writer) references USERS (userId)
);
create table REPLIES
(
    replyId int auto_increment,
    articleId int not null,
    writer varchar(20) not null,
    content TINYTEXT not null,
    createAt TIMESTAMP default current_timestamp,
    primary key (replyId),
    foreign key (articleId) references ARTICLES (articleId),
    foreign key (writer) references USERS (userId)
)

