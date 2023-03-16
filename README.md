## 기능 추가 구현

- 글쓰기 기능 구현
- 글 목록 조회하기 구현
- 게시글 상세보기 구현
- 회원 정보 


## 각 기능에 따른 url 과 메소드 convention

| URL                 | 기능             |
|:--------------------|:---------------|
| GET /user           | 회원 목록 조회       |
| GET /user/{userId}  | 회원 profile 보기  |
| POST /user/save     | 회원 가입          |
| GET /qan/list       | 글 목록 보기        |
| GET /qna/show       | 글 상세 보기        |
| GET /qna/form       | 글 작성           |
| PUT user/update/:id | 유저 정보 업데이트     |

## 학습 예정 키워드

- 스프링의 Annotation
- classpath 설정
- context path
