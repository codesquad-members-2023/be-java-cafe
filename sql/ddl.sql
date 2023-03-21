CREATE TABLE cafe_user
(
    userNum  BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId   VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(20) NOT NULL,
    name     VARCHAR(20) NOT NULL,
    email    VARCHAR(50) NOT NULL
);

CREATE TABLE cafe_article
(
    index    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    writer   VARCHAR(20)  NOT NULL,
    title    VARCHAR(50)  NOT NULL,
    contents VARCHAR(500) NOT NULL
);
