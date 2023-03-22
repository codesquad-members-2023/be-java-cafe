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

## 학습 계획 (3주차)
- AWS를 이용한 배포 (with. H2 설정)
- 로그인 세션, 쿠키 학습
- 데이터베이스 쿼리 연습 및 성능 비교
  - 조인 순서 또는 인덱스 고려
- 게시글 권한 부여할 때 어떻게 테이블을 만드는 것이 효율적인지 생각 (테이블에 허용 범위 컬럼을 추가할 것인지 다른 방법이 있는지 등)
- 대댓글이 있지만 삭제된 댓글 / 대댓글이 없는 삭제된 댓글 등 UI 고려한 로직 고민 


## url
### 유저(/users)
- 회원 가입 (POST /users)
  - return redirect:/user
- 회원 목록 조회 (GET /users)
  - return user/list
- 회원 프로필 조회 (GET /users/{userId})
  - return user/profile