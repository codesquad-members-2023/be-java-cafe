CREATE TABLE user
(
    userNum  INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userId   VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    name     VARCHAR(20) NOT NULL,
    email    VARCHAR(50) NOT NULL
);
