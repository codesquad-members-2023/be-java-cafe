DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS article;

create table member (
    id  long auto_increment primary key,
    userId   varchar(255) not null,
    nickName varchar(255) not null,
    email   varchar(500) not null,
    password    varchar(255) not null,
    created_at  timestamp default current_timestamp(),
    updated_at  timestamp default current_timestamp()
);

create table article (
    id  long auto_increment primary key,
    title   varchar(255) not null,
    body    text not null,
    writer_id long not null,
    created_at  timestamp default current_timestamp(),
    updated_at  timestamp default current_timestamp(),
    constraint ARTICLE_MEMBER_ID_FK
        foreign key (writer_id) references member (id)
);

INSERT INTO MEMBER (id, userId, password, nickName, email)
VALUES
    (1, 'javajigi', '1234', '자바지기', 'javajigi@slipp.net'),
    (2, 'sanjigi', '1234', '산지기', 'sanjigi@slipp.net');
