create table member
(
    member_id varchar(255) primary key,
    password varchar(255),
    email varchar(320),
    name varchar(255)
);
create table article
(
    article_id bigint primary key auto_increment,
    member_id varchar(255) references member(member_id),
    title varchar(255) not null,
    content varchar(1000) not null,
    createdAt timestamp not null default current_timestamp
);
