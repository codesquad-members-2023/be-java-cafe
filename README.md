# 스프링 카페1
## 1단계
> ✏️스프링부트 프로젝트 셋업     
> ✏️템플릿 기반 MVC 페이지를 구성하고 기능 구현   
> ✏️스프링부트로 GET과 POST 메소드의 동작방식을 이해하고 처리   
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
- [ ] 오브젝트 읽기
- [ ] 회원 프로필 구현 완료
- [ ] 스레드 세이프한지 확인 
- [ ] static html을 URL 매핑하지 않고 해결하기 -> config 패키지 생성? 
- [ ] 글쓰기 기능 구현
- [ ] 글 목록 조회 기능 구현

### 📝 추가 정리 필요
- [ ] url convention 
- [ ] MVC란?
- [ ] HTTP 동작 방식
- [ ] GET, POST 차이 
- [ ] 학습 자료 공부
- [ ] log - logging 라이브러리

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
  * email
  * password
* `UserRepository`
  * List<User> users 
  * findByName(name) / findByEmail(email) : 이름/이메일로 사용자 찾기

