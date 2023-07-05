drop table if exists reply;
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

create table reply
(
    reply_sequence       bigint auto_increment primary key,
    reply_contents       varchar(500) not null,
    reply_writer         varchar(64),
    reply_member_number  bigint,
    reply_article_number bigint,
    reply_writtentime    timestamp    not null default current_timestamp(),
    foreign key (reply_writer) references member (member_id),
    foreign key (reply_member_number) references member (member_number),
    foreign key (reply_article_number) references article (article_number)
);
