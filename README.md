# be-java-cafe
마스터즈 2023 스프링 카페 

## 학습 계획
- 스프링 기본
- 스프링 애노테이션
- MVC 패턴
- 스프링 부트 템플릿 기반 MVC
- HTTP 메서드
- url 및 메서드 컨벤션
- Path Variable vs Query Parameter


## url
### 유저(/users)
- 회원 가입 (POST /users)
  - return redirect:/user
- 회원 목록 조회 (GET /users)
  - return user/list
- 회원 프로필 조회 (GET /users/{userId})
  - return user/profile