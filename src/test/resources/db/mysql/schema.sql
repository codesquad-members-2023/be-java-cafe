DROP TABLE IF EXISTS ANSWER;
DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS MEMBER;

CREATE TABLE MEMBER (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        USERID VARCHAR(255) NOT NULL UNIQUE,
                        NICKNAME VARCHAR(255) NOT NULL,
                        EMAIL VARCHAR(500) NOT NULL UNIQUE,
                        PASSWORD VARCHAR(255) NOT NULL,
                        CREATED_AT TIMESTAMP,
                        UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE ARTICLE (
                         ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                         TITLE VARCHAR(255) NOT NULL,
                         CONTENTS TEXT NOT NULL,
                         USER_ID BIGINT NOT NULL,
                         CREATED_AT TIMESTAMP,
                         UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                         IS_DELETED BOOLEAN DEFAULT FALSE,
                         CONSTRAINT ARTICLE_MEMBER_ID_FK
                             FOREIGN KEY (USER_ID) REFERENCES MEMBER (ID)
);

CREATE TABLE ANSWER (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        CONTENTS TEXT NOT NULL,
                        USER_ID BIGINT NOT NULL,
                        ARTICLE_ID BIGINT NOT NULL,
                        CREATED_AT TIMESTAMP,
                        UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                        IS_DELETED BOOLEAN DEFAULT FALSE,
                        CONSTRAINT ANSWER_MEMBER_ID_FK
                            FOREIGN KEY (USER_ID) REFERENCES MEMBER (ID),
                        CONSTRAINT ANSWER_ARTICLE_ID_FK
                            FOREIGN KEY (ARTICLE_ID) REFERENCES ARTICLE (ID)
);
