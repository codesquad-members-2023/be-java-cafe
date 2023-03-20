# 스프링 카페1
> ✏️스프링부트 프로젝트 셋업     
> ✏️템플릿 기반 MVC 페이지를 구성하고 기능 구현   
> ✏️스프링부트로 GET과 POST 메소드의 동작방식을 이해하고 처리   
> ✏️스프링부트를 이용해서 게시글 쓰기
### ☑️ TODO   
**0313**
- [x] 스프링 프로젝트 환경설정 
- [x] 스프링 입문 강의 ~ 섹션 4까지 

**0314**
- [x] 스프링 입문 강의 ~ 섹션 5까지  
- [x] 스프링 입문 강의 정리 
- [ ] 마스터 클래스 복습 및 정리 -> 0315
- [x] mustache 추가 및 설정
- [x] 미션 1 설계 시도
- [x] 홈 화면 띄우기 & 동작 확인
- [x] 회원 가입 기능 구현
- [x] 회원 목록 보기 구현
- [ ] 회원 프로필 보기 구현 -> 0315

**0315**
- [x] 오브젝트 읽기
- [x] 회원 가입 & 목록 보기 수정
- [x] html 파일 수정
- [x] 회원 프로필 보기 구현
- [ ] 마스터 클래스 복습 -> 0316

**0316**
- [x] 오브젝트 읽기
- [x] 회원 프로필 구현 완료
- [ ] 스레드 세이프한지 확인 
- [x] cotronller에서 URL 매핑하지 않고 html을 매핑 -> config 패키지 생성? 
- [x] 카카오 화면을 기본 화면으로 변경 
- [ ] 마스터 클래스 복습 -> 0317 
- [x] 글쓰기 기능 구현
- [ ] 글 목록 조회 기능 구현 -> 0317

**0317**
- [x] 글 목록 조회 기능 구현
- [x] 게시글 상세보기 구현
- [ ] 마스터 클래스 1 복습
- [ ] 마스터 클래스 2 복습 
- [x] two pointers 알고리즘 공부 

**0320**
- [ ] 회원 정보 수정
- [ ] 오브젝트 읽기
- [ ] 스프링 입문편 섹션 6 듣기 (스프링 DB 접근 기술)
- [ ] H2 데이터베이스 연동
- [ ] 게시글 데이터 저장 

### 📝 추가 정리 필요
- [ ] url convention 
- [ ] MVC란?
- [ ] HTTP 동작 방식
- [ ] GET, POST 차이 
- [ ] 학습 자료 공부
- [ ] log - logging 라이브러리
- [ ] 스레드 세이프 의미? 

***
## 미션 구현
### 📌 웹 페이지 디자인
* static 폴더에 있는 기존 자료를 수정하여 진행
### 📌1단계
* 화면 URL : /user/form -> /user/form.html
* 저장 후 : /users -> /user/list.html
* `UserController` 사용자 관리 기능 구현 담당
  * @Controller 애노테이션 매핑
  * @GetMapping 
    * 회원가입 URL과 매핑 -> /user/form
    * 회원 프로필 URL과 매핑 /users/{name}
  * @PosstMapping 회원가입 요청 
* `User` 사용자 
  * name
  * id
  * email
  * password
* `UserRepository`
  * List<User> users 
  * findByName(name) / findByEmail(email) : 이름/이메일로 사용자 찾기

### 📌2단계
* 질문하기 URL : /qna/form -> /qna/form.html 
  * `addViewController`에 추가하기
* `ArticleController`: 게시글 기능 구현 담당
  * @PostMapping 게시글 작성 요청 -> /questions
    1. `ArticleForm` 사용자가 전달한 값 받기 위함 
    2. return "redirect:/"
  * @GetMapping("/") : 글 목록 -> index.html
  * @GetMapping("/articles/{index}") 
* `Article` 
  * 글쓴이
  * 제목
  * 내용
* `ArticleRepository` 
  * List<Article> articles