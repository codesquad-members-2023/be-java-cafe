DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS member;

create table member (
                        id  long auto_increment primary key,
                        userId   varchar(255) not null unique,
                        nickName varchar(255) not null,
                        email   varchar(500) not null unique,
                        password    varchar(255) not null,
                        created_at timestamp default current_timestamp(),
                        updated_at  timestamp default current_timestamp()
);

create table article (
                         id  long auto_increment primary key,
                         title   varchar(255) not null,
                         contents    text not null,
                         user_id long not null,
                         created_at  timestamp default current_timestamp(),
                         updated_at  timestamp default current_timestamp(),
                         constraint ARTICLE_MEMBER_ID_FK
                             foreign key (user_id) references member (id)
);

create table answer (
                         id  long auto_increment primary key,
                         contents    text not null,
                         user_id long not null,
                         article_id long not null,
                         created_at  timestamp,
                         updated_at  timestamp,
                         constraint ANSWER_MEMBER_ID_FK
                             foreign key (user_id) references member (id),
                         constraint ANSWER_ARTICLE_ID_FK
                            foreign key (article_id) references article (id)
);
