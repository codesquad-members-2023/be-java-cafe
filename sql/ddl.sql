drop table if exists member cascade;

create table member (
    member_number bigint not null auto_increment,
    member_id varchar(64) not null,
    member_password varchar(64) not null,
    member_name varchar(64) not null,
    member_email varchar(64) not null,
    primary key (member_id)
);
