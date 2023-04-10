# 목차

## 1. 프로젝트 소개

회원가입 및 로그인, 게시글 작성, 댓글 작성 기능을 갖는 간단한 게시판 프로젝트입니다.
개인 프로젝트로 Lombok 과 JPA는 사용하지 않고, Spring JDBC 를 사용했습니다.

### 1.1 기능
- 게시판 : CRUD 기능
- 사용자 : 회원가입 및 로그인, 회원정보 수정, 프로필 조회
- 댓글 : CRUD 기능, 싱글 페이지로 댓글 추가 및 삭제 가능

### 1.2 사용 기술

**주요 프레임 워크**

- Java
- SpringBoot
- Spring JDBC
- JUnit5, AssertJ

**DB**
- MySQL, H2DB

**Template Engine**
- Thymeleaf

**AJAX 처리**
- Jquery

**배포**
- AWS EC2

## 2. 서비스 미리보기
### 2.1 게시글
1. 게시글 전체 목록

![img_1.png](img_1.png)

> 게시글의 목록과 해당 게시글의 댓글 수를 보여줍니다.

2. 게시글 작성

![img_2.png](img_2.png)

> 로그인한 사용자만 게시글을 작성할 수 있습니다.

![img_3.png](img_3.png)

![img_4.png](img_4.png)

> 게시글을 작성하면 게시글 목록으로 리디렉션 됩니다.

![img_11.png](img_11.png)

![img_12.png](img_12.png)
> 게시글의 내용이 없거나, 게시글의 최대 글자수를 넘으면 작성할 수 없습니다.

3. 게시글 상세 보기

![img_5.png](img_5.png)
> 자세한 게시글 내용을 조회할 수 있습니다.

![img_6.png](img_6.png)
> - 댓글 조회, 추가, 삭제가 가능합니다.
> - 댓글 삭제 방식은 soft-delete 입니다.
> - 싱글페이지로 동작합니다.

![img_14.png](img_14.png)
> - 댓글은 자신이 작성한 댓글만 삭제 가능합니다.

4. 게시글 수정 하기

![img_7.png](img_7.png)
![img_8.png](img_8.png)

> - 게시글 작성자는 게시글을 수정할 수 있습니다.
> - 수정이 완료되면 게시글 목록으로 리디렉션 됩니다.

![img_13.png](img_13.png)

> - 수정에서도 위와 같은 검증이 수행됩니다.

![img_16.png](img_16.png)
> 자신이 작성하지 않은 게시글은 수정할 수 없습니다.

5. 게시글 삭제 하기

![img_9.png](img_9.png)
![img_10.png](img_10.png)
> - 게시글 작성자는 게시글을 삭제할 수 있습니다.
> - soft-delete 로 처리됩니다.
> - 삭제가 완료되면 게시글 목록으로 리디렉션 됩니다.

![img_15.png](img_15.png)
> 자신이 작성하지 않은 게시글은 삭제할 수 없습니다.

### 2.2 회원
1. 회원 가입 하기

![img_17.png](img_17.png)
> 로그인하지 않은 사용자의 경우 `로그인`, `회원가입` 만 보입니다.

![img_18.png](img_18.png)
> - 회원가입 폼을 작성하여 회원가입 할 수 있습니다.
> - 회원가입은 정해진 양식을 준수해야 합니다.

![img_19.png](img_19.png)
> 정해진 양식을 준수하였더라도, 중복 ID 인 경우 제출할 수 없습니다.

![img_20.png](img_20.png)
> 성공적으로 회원가입을 성공하면 회원목록으로 리디렉션 됩니다.

![img_21.png](img_21.png)
> 로그인을 할 수 있습니다.

![img_22.png](img_22.png)
> 존재하는 아이디지만, 비밀번호가 틀린경우

![img_23.png](img_23.png)
> 아이디가 존재하지 않는 경우

![img_24.png](img_24.png)
> 로그인을 성공적으로 수행하면 `로그아웃`, `개인정보수정` 이 보입니다.

![img_25.png](img_25.png)
![img_26.png](img_26.png)
> 개인정보 수정 시, 비밀번호를 입력해야 합니다.
> 현재 비밀번호가 맞는 경우, 아이디를 제외한 모든 정보를 수정할 수 있습니다.

![img_27.png](img_27.png)
> 현재 비밀번호가 틀린 경우, 에러페이지로 이동합니다.

## 3. 설계
### 3.1 DB 설계

#### Users 테이블

| 필드       | 타입                       | NULL 허용 | 키 | 기본값 |
|----------|--------------------------|----------|----|--------|
| ID       | INTEGER (AUTO_INCREMENT) | NO | PRI | NULL   |
| USER_ID  | CHARACTER VARYING(30)    | NO |     | NULL   |
| PASSWORD | CHARACTER VARYING(30)    | NO |   | NULL   |
| NAME     | CHARACTER VARYING(30)    | NO |       | NULL   |
| EMAIL    | CHARACTER VARYING(30)    | NO |       | NULL   |

#### Article 테이블

| 필드         | 타입                       | NULL 허용 | 키 | 기본값  |
|------------|--------------------------|---------|----|------|
| ID         | INTEGER (AUTO_INCREMENT) | NO      | PRI | NULL |
| USER_ID    | INTEGER                  | NO      | FK  | NULL |
| TITLE      | CHARACTER VARYING(70)    | NO      |     | NULL |
| CONTENTS   | CHARACTER VARYING(1000)  | NO      |  | NULL |
| CREATEDATE | TIMESTAMP WITH TIME ZONE | YES     | | NULL |
| DELETED    | BOOLEAN                  | YES     | | NULL |

#### Reply 테이블

| 필드           | 타입                       | NULL 허용 | 키 | 기본값  |
|--------------|--------------------------|---------|----|------|
| ID           | INTEGER (AUTO_INCREMENT) | NO      | PRI | NULL |
| USER_ID      | INTEGER                  | NO      | FK  | NULL |
| ARTICLE_ID   | INTEGER                  | NO      | FK  | NULL |
| CONTENTS     | CHARACTER VARYING(1000)  | NO      | | NULL |
| CREATIONTIME | TIMESTAMP WITH TIME ZONE | YES     | | NULL |
| DELETED      | BOOLEAN                  | YES     | | NULL |

### 3.2 URL 설계

#### 게시글 관련 URL

| 기능           | 메소드    | URL                   | Return Page       |
|--------------|--------|-----------------------|-------------------|
| 게시글 전체 목록 조회 | GET    | /                     | 게시글 전체 목록         |
| 게시글 상세 보기    | GET    | /articles/{index}     | 게시글 상세보기 페이지 이동   |
| 게시글 등록 페이지   | GET    | /questions/form            | 게시글 등록 페이지 이동     |
| 게시글 수정 페이지   | GET    | /articles/{index}/form | 게시글 수정 페이지 이동     |
| 게시글 등록       | POST   | /questions          | 게시글 등록 후 전체 목록 이동 | 
| 게시글 수정       | PUT    | /articles/{index} | 게시글 수정 후 전체 목록 이동 |
| 게시글 삭제       | DELETE | /articles/{index}      | 게시글 삭제 후 전체 목록 이동 |

#### 회원 관련 URL


| 기능      | 메소드  | URL                  | Return Page           |
|---------|------|----------------------|-----------------------|
| 회원 가입 페이지 | GET  | /users/form        | 회원 가입 페이지로 이동         |
| 회원 프로필 조회 | GET  | /users/{id}      | 회원 프로필 페이지로 이동        |
| 회원 가입   | POST | /users      | 회원 가입 후 회원 목록으로 이동    |
| 회원 목록 페이지 | GET  | /users         | 회원 목록 페이지로 이동         |
| 회원 수정 페이지 | GET  | /users/{id}/form | 회원 정보 수정 페이지로 이동      |
| 회원 수정   | PUT  | /users/{id} | 회원 정보 수정 후 회원 목록으로 이동 |
| 로그인 페이지 | GET  | /login       | 로그인 페이지로 이동           | 
| 로그인     | POST | /login        | 로그인 후 회원 목록으로 이동      |
| 로그아웃    | GET  | /logout     | 로그아웃 후 회원 목록으로 이동     |

#### 댓글 관련 URL

| 기능         | 메소드    | URL                  | Return Page |
|------------|--------|----------------------|---------|
| 댓글 작성      | POST   | /articles/{index}/replies         | 댓글 등록   |
| 댓글 삭제      | DELETE | /articles/{articleId}/replies/{replyId}      | 댓글 삭제   |
