## 기능 요구 사항

### 회원가입 기능 구현

- [x] 가입하기 페이지는 static/user/form.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] 사용자 관리 기능 구현을 담당할 UserController를 추가하고 애노테이션 매핑한다.
- [x] 회원가입하기 요청(POST 요청)을 처리할 메소드를 추가하고 매핑한다.
- [x] 사용자가 전달한 값을 User 클래스를 생성해 저장한다.
- [x] 사용자 목록을 관리하는 ArrayList를 생성한 후 앞에서 생성한 User 인스턴스를 ArrayList에 저장한다.
- [x] 사용자 추가를 완료한 후 사용자 목록 페이지("redirect:/users")로 이동한다.

### 회원목록 기능 구현

- [x] 회원목록 페이지는 static/user/list.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] Controller 클래스는 회원가입하기 과정에서 추가한 UserController를 그대로 사용한다.
- [x] 회원목록 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
- [x] Model을 메소드의 인자로 받은 후 Model에 사용자 목록을 users라는 이름으로 전달한다.
- [x] 사용자 목록을 user/list.html로 전달하기 위해 메소드 반환 값을 "user/list"로 한다.
- [x] user/list.html 에서 사용자 목록을 출력한다.

## 회원 프로필 정보보기

- [x] 회원 프로필 보기 페이지는 static/user/profile.html을 사용한다.
- [x] static에 있는 html을 templates로 이동한다.
- [x] 앞 단계의 사용자 목록 html인 user/list.html 파일에 닉네임을 클릭하면 프로필 페이지로 이동하도록 한다.
- [x] Controller 클래스는 앞 단계에서 사용한 UserController를 그대로 사용한다.
- [x] 회원프로필 요청(GET 요청)을 처리할 메소드를 추가하고 매핑한다.
- [x] URL을 통해 전달한 사용자 아이디 값은 @PathVariable 애노테이션을 활용해 전달 받을 수 있다.
- [x] ArrayList에 저장되어 있는 사용자 중 사용자 아이디와 일치하는 User 데이터를 Model에 저장한다.
- [x] user/profile.html 에서는 Controller에서 전달한 User 데이터를 활용해 사용자 정보를 출력한다.

### 각 기능에 따른 url 과 메소드 convention

|         URL         |      기능       |
|:-------------------:|:-------------:|
|     GET /users      |   회원 목록 조회    |
| GET /users/{userId} | 회원 profile 보기 |
|  POST /users/save   |     회원 가입     |



## 학습 키워드

- JAR vs WAR
- build.gradle 의 dependencies
- mustache

### JAR vs WAR

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

---

### build.gradle 의 dependencies

dependencies 에 프로젝트가 작업을 수행하는데 필요한 의존성들을 선언헌다.  
또한 선언된 모든 의존성은 사용되는 `특정 범위`를 가진다. 예를 들어 어떤 의존성은 컴파일 할 때에만 사용될 수 있고,  
다른 의존성은 런타임할 때에 사용될 수 있다. 이렇게 의존성의 범위를 표현한 것을 dependency configuration 이라고 한다.

- implementation : 구현할 때에만 사용된다.
- compileOnly : 컴파일 시점에만 사용하고 런타임에는 사용되지 않는다.
- runtimeOnly : 런타임 때에만 사용된다.
- testImplementation : 테스트할 때에만 사용된다.

## 출처

https://old-developer.tistory.com/m/171
