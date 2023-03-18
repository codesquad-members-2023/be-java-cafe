# be-java-cafe
마스터즈 2023 스프링 카페

### 학습 계획
* 월 (03.13)
  * 강의 수강
    * [x] HTTP 상태 코드, 일반헤더 수강 및 정리
  * 키워드 학습
    * [x] MVC 패턴 학습 1
  * 미션 구현
    * [x] 회원 가입 기능 구현 1
  * 트러블 슈팅
* 화 (03.14)
  * 강의 수강
    * [ ] HTTP 헤더2, 다음으로 수강 및 정리
  * 키워드 학습
    * [ ] MVC 패턴 학습 2
  * 미션 구현
    * [x] 회원 가입 구현 2
  * 트러블 슈팅
* 수 (03.15)
  * 강의 수강
    * [ ] 스프링 핵심 원리 기본편 섹션 1
  * 키워드 학습
    * [x] 템플릿 엔진
  * 미션 구현
    * [x] 회원 목록 조회 기능 구현 1
  * 트러블 슈팅
* 목 (03.16)
  * 강의 수강
    * [ ] 스프링 핵심 원리 기본편 섹션 2
  * 키워드 학습
  * 미션 구현
    * [x] 회원 목록 조회 기능 구현 2
  * 트러블 슈팅
    * WebMvcConfigurerAdapter가 더이상 권장되지 않는 이유
      * Java 8 이상에서 인터페이스에 디폴트 메소드 지원
        * Java 8부터는 인터페이스에 디폴트 메소드를 선언할 수 있게 되었습니다. 따라서 WebMvcConfigurer 인터페이스에도 디폴트 메소드를 추가할 수 있게 되었습니다. 이로 인해 WebMvcConfigurerAdapter가 불필요해졌습니다. 
      * WebMvcConfigurer 인터페이스가 모든 메소드를 추상 메소드로 가지고 있기 때문에, 필요한 메소드만 오버라이드하여 사용 가능합니다. 
        * 따라서 WebMvcConfigurerAdapter처럼 일부 메소드만 구현하는 클래스가 필요 없어졌습니다. 
      * Spring Framework의 진화
        * Spring Framework는 계속해서 업데이트되고 발전하고 있습니다. 이에 따라 더욱 유연하고 강력한 기능을 제공하는 새로운 인터페이스와 클래스가 추가되고 있습니다. WebMvcConfigurerAdapter는 이러한 새로운 기능에 대한 지원이 부족하기 때문에 더 이상 권장되지 않습니다.
* 금 (03.17)
  * 강의 수강
    * [ ] 스프링 핵심 원리 기본편 섹션 3
  * 키워드 학습
  * 미션 구현
    * [x] 회원 프로필 조회 기능 구현
  * 트러블 슈팅


### SQL 설계

* ARTICLE TABLE
```h2
CREATE TABLE ARTICLE (
ARTICLE_ID INT PRIMARY KEY,
USER_ID VARCHAR2 NOT NULL,
TITLE VARCHAR2 NOT NULL,
CONTENTS VARCHAR2 NOT NULL,
TIME VARCHAR2 NOT NULL
)
```

```mysql
CREATE TABLE ARTICLE (
ARTICLE_ID INT PRIMARY KEY,
USER_ID VARCHAR(255) NOT NUll,
TITLE VARCHAR(255) NOT NUll,
CONTENTS VARCHAR(255) NOT NUll,
TIME VARCHAR(255) NOT NUll
)
```

* USER TABLE
```h2
CREATE TABLE USER (
USER_ID VARCHAR2 PRIMARY KEY,
PASSWORD VARCHAR2 NOT NULL,
NAME VARCHAR2 NOT NULL,
EMAIL VARCHAR2 NOT NULL
)
```

```mysql
CREATE TABLE USER (
USER_ID VARCHAR2 PRIMARY KEY,
PASSWORD VARCHAR(255) NOT NULL,
NAME VARCHAR(255) NOT NULL,
EMAIL VARCHAR(255) NOT NULL
)
```
