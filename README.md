## 기능 요구 사항

- [ ] 웹페이지 디자인
- [ ] 회원 가입 기능 구현
- [ ] 회원 목록 조회 기능 구현
- [ ] 회원 프로필 조회 기능 구현

## 프로그래밍 요구사항

### 각 기능에 따른 url 과 메소드 convention

|         URL         |      기능       |
|:-------------------:|:-------------:|
|     GET /users      |   회원 목록 조회    |
| GET /users/{userId} | 회원 profile 보기 |
|  POST /users/save   |     회원 가입     |



## 학습 키워드

- JAR vs WAR
- mustache

### FAR vs WAR

기본적으로 JAR, WAR 모두 Java 의 jar 옵션을 이용해 생성된 압축(아카이브)파일로,
애플리케이션을 쉽게 배포하고 동작시킬 수 있도록 관련 파일(리소스, 속성 파일 등)을 패키징 한 것입니다.

#### JAR (Java Archive)

- Java 어플리케이션이 동작할 수 있도록 자바 프로젝트를 압축한 파일
- Class (Java 리소스, 속성 파일), 라이브러리 파일을 포함함
- JRE (Java Runtime Environment)만 있어도 실행 가능함

#### WAR (Web Application Archive)

- Servlet / Jsp 컨테이너에 배치할 수 있는 웹 애플리케이션 압축파일 포멧
- 웹 관련 자원을 포함함 (JSP, Servlet, JAR, Class, XML, HTML, Javascript)
- 사전 정의된 구조를 사용함 (WEB-INF, META-INF)
- 별도의 웹서버(WEB) or 웹 컨테이너(WAS) 필요
- 즉, JAR 파일의 일종으로 웹 애플리케이션 전체를 패키징 하기 위한 JAR 파일이다.

### build.gradle 의 dependencies

dependencies 에 프로젝트가 작업을 수행하는데 필요한 의존성들을 선언헌다.  
또한 선언된 모든 의존성은 사용되는 `특정 범위`를 가진다. 예를 들어 어떤 의존성은 컴파일 할 때에만 사용될 수 있고,  
다른 의존성은 런타임할 때에 사용될 수 있다. 이렇게 의존성의 범위를 표현한 것을 dependency configuration이라고 한다.

- implementation : 구현할 때에만 사용된다.
- compileOnly : 컴파일 시점에만 사용하고 런타임에는 사용되지 않는다.
- runtimeOnly : 런타임 때에만 사용된다.
- testImplementation : 테스트할 때에만 사용된다.

#### annotationProcessor

#### test***

테스트 코드를 수행할 때만 적용하는 *** 들 이다.

---

### mustache



## 출처

https://old-developer.tistory.com/m/171
