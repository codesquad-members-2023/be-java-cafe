CREATE TABLE cafe_user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId   VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    name     VARCHAR(20) NOT NULL,
    email    VARCHAR(50) NOT NULL
);

CREATE TABLE cafe_article
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer   VARCHAR(20)  NOT NULL,
    title    VARCHAR(50)  NOT NULL,
    contents VARCHAR(500) NOT NULL,
    time     TIMESTAMP    not null default now()
);

-- 컬럼명 변경
alter table cafe_user
    rename column usernum to id;

alter table cafe_article
    rename column index to id;

-- 컬럼 타입 변경
alter table cafe_user
    alter column id BIGINT AUTO_INCREMENT;

alter table cafe_article
    alter column id BIGINT AUTO_INCREMENT;

-- 테이블 구조 조회
show columns from cafe_user
