DROP TABLE IF EXISTS cafe_user, cafe_article, cafe_reply CASCADE;
CREATE TABLE cafe_user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId   VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(20)        NOT NULL,
    name     VARCHAR(20)        NOT NULL,
    email    VARCHAR(50)        NOT NULL
);

CREATE TABLE cafe_article
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer     VARCHAR(20)   NOT NULL,
    title      VARCHAR(50)   NOT NULL,
    contents   VARCHAR(500)  NOT NULL,
    time       smalldatetime not null default now(),
    deleted    boolean                default false,
    cafeUserId BIGINT,
    FOREIGN KEY (cafeUserId) REFERENCES cafe_user (id)
);

CREATE TABLE cafe_reply
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    writer    VARCHAR(20)   NOT NULL,
    contents  VARCHAR(300)  NOT NULL,
    time      smalldatetime not null default now(),
    deleted   boolean                default false,
    articleId BIGINT,
    FOREIGN KEY (articleId) REFERENCES cafe_article (id)
);
