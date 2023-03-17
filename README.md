# be-java-cafe
마스터즈 2023 스프링 카페 

### 학습 계획
- 인프런 김영한 스프링 입문편
- 추가 학습 거리
  - template engine
  - mustache 문법, 설정
  - html 중복 제거
  - URL과 html 연결

- 월요일
  - [O] 김영한 스프링 입문편 수강하기
  - [ ] 향로 스프링 학습 블로그
- 화요일
  - [O] 회원가입 기능 구현
- 수요일
  - [O] 회원 목록 조회 기능 구현
- 목요일
  - [O] 회원 프로필 조회 기능 구현
- 금요일
  - [ ] 기타 미구현 요구사항 구현
  - [ ] 추가학습거리 학습

### 미션 - 1 : 기능 요구사항
1. 웹페이지 디자인
- static 폴더에 있는 기존 자료(QA 게시판) 를 수정하거나 아래 디자인 기획서를 참고해서 구현한다.
- 디자인은 자유롭게 구현해도 무방하다.
- 디자인 기획서
- 참고 마크업 파일
- 별도의 데이터베이스는 사용하지 않는다.

2. 회원가입 기능 구현
- 가입하기 페이지에서 회원 가입 폼을 표시한다.
- 개인정보를 입력하고 확인을 누르면 회원 목록 조회 페이지로 이동한다.

3. 회원목록 조회 기능 구현
- 목록 조회 페이지에서는 가입한 회원들의 목록을 출력한다.

4. 회원 프로필 조회 기능 구현
- 회원 프로필 페이지에서는 개별 회원의 프로필 정보를 출력한다.


## 학습 내용 정리

### 스프링
- 스프링의 개념
  - 자바 기반 웹 어플리케이션을 만들 수 있는 프레임워크
- 스프링 특징
  - Tomcat과 같은 WAS가 내장되어있어 웹 어플리케이션을 구동할 수 있다.
  - 경량 컨테이너로 자바 객체를 Spring 안에서 관리한다. 객체 생성, 소멸 등 생명 주기(life cycle)을 관리하며, 컨테이너에서 필요한 객체를 가져와 사용
  - IOC(제어 역전)
    - 기존 : 각 객체들이 프로그램 흐름을 결정하며 각 객체에서 다른 객체의 생성 및 조작을 한다.
    - IOC : 객체 생성을 특별한 관리 위임 주체에게 맡겨 다른 주체가 객체 생명 주기를 컨트롤한다.
      - 스프링에게 제어를 위임하여 스프링이 만든 객체를 주입 -> 의존성 객체의 메소드 호출 구조
    - 위 방식으로 객체간의 결합도를 낮출 수 있다.

### 스프링 부트
- 스프링 개발 시 프로젝트 관련된 설정(실행환경, 의존성 관리 등)을 쉽게할 수 있도록 도와준다.

### 스프링 빈
- Spring IoC 컨테이너가 관리하는 자바 객체를 Bean이라고 한다.
- Spring에 의하여 생성되고 관리되는 자바 객체로, ApplicationContext.getBean()과 같은 메소드로 객체를 얻어 사용

### 스프링 빈을 IoC 컨테이너에 등록하는 방법
1. 자바 어노테이션 사용
- @Component Annotation
  - @Component Annotation이 등록되어있는 경우 Spring이 Annotation을 확인하고 자체적으로 Bean을 등록한다.
  - ex) @Controller Annotation은 @Component Annotation이 등록되어 어노테이션 사용 시 스프링이 Controller를 Bean으로 등록한다.
2. Bean Configuration File에 직접 등록
- @Configuration @Bean Annotation
- @Configuration을 이용하면 Spring project에서 Configuration 역할을 하는 Class 지정 가능
- 해당 File 하위에 Bean으로 등록하고자 하는 Class에 @Bean Annotation을 사용하면 Bean을 등록할 수 있다.
```
// Hello.java
@Configuration
public class HelloConfiguration {
    @Bean
    public HelloController sampleController() {
        return new SampleController;
    }
}
```

### Annotation?
- Annotation의 역할
  - 자바 소스 코드에 추가하여 사용할 수 있는 메타데이터의 일종
  - Spring에서는 클래스의 역할, Bean 주입, getter&setter 자동 생성 등 다양한 역할 수행 가능하다.
  - 코드량 감소하고 유지보수가 쉬우며 생산성이 증대된다.

### 대표적인 Annotation과 역할
1. @Component
- 생성한 클래스를 Spring의 Bean으로 등록
2. @ComponentScan
- @Component, @Service, @Repository, @Controller, @Configuration 중 1개라도 등록된 클래스를 찾으면 Context에 bean으로 등록한다.
- Annotation이 있는 클래스의 하위 Bean을 등록한다.
3. @Bean
- 제어 불가능한 외부 라이브러리와 같은 것을 Bean으로 만들 때 사용
4. @Controller
- 해당 클래스가 컨트롤러 역할을 한다고 명시하는 Annotation
5. @RequestHeader
- Request의 header 값을 가져올 수 있다.
6. @RequestMapping
- RequestMapping(value="") 형태로 작성, URI 요청과 Annotation value 값이 일치하면 클래스, 메소드가 실행된다.
- Controller 객체 안에 메서드와 클래스에 적용 가능
- Class 단위에 적용하면 하위 메소드에 모두 적용
- 메소드에 적용되면 해당 메소드에서 지정한 방식으로 URI 처리
7. @RequestParam
- URL 전달되는 파라미터를 메소드 인자와 매칭시켜, 파라미터를 받아서 처리할 수 있는 Annotation
- Json형식의 Body를 MessageConverter를 통해 Java 객체로 변환시킨다.
```
@Controller                   // 이 Class는 Controller 역할을 합니다
@RequestMapping("/user")      // 이 Class는 /user로 들어오는 요청을 모두 처리합니다.
public class UserController {
    @RequestMapping(method = RequestMethod.GET)
    public String getUser(@RequestParam String nickname, @RequestParam(name="old") String age {
        // GET method, /user 요청을 처리
        // https://naver.com?nickname=dog&old=10
        String sub = nickname + "_" + age;
        ...
    }
}
```
8. @RequestBody
- Body에 전달되는 데이터를 메소드의 인자와 매칭시켜, 데이터를 받아서 처리할 수 있는 Annotation
- 클라이언트가 보내는 HTTP 요청 본문(JSON 및 XML 등)을 Java 오브젝트로 변환한다.

```
@Controller                   // 이 Class는 Controller 역할을 합니다
@RequestMapping("/user")      // 이 Class는 /user로 들어오는 요청을 모두 처리합니다.
public class UserController {
    @RequestMapping(method = RequestMethod.POST)
    public String addUser(@RequestBody User user) {
        //  POST method, /user 요청을 처리
        String sub_name = user.name;
        String sub_old = user.old;
    }
}
```

9. @ModelAttribute
- parameter나 body 내용을 setter 함수로 1:1로 데이터에 연결(바인딩)한다. multipart/form-data 형태를 요구하고, json을 처리할 수 없다.

10. @ResponseBody
- View로 출력되지 않고 Response Body에 직접 쓰여진다. json, xml과 같은 데이터를 return한다.

11. @Autowired
- Bean을 주입받기 위한 방법
  - @Autowired
  - 생성자 (@AllArgsConstructor 사용)
  - setter

### Lombok Annotation
- Lombok은 코드를 크게 줄여주어 가독성을 높이는 라이브러리
1. @Setter, @Getter
- Class 모든 필드의 Setter/Getter를 생성
2. @AllArgsConstructor
- Class 모든 필드 값을 파라미터로 받는 생성자
3. @NoArgsConstructor
- 기본 생성자 추가
4. @ToString

### DAO, DTO, VO
- DAO(Data Access Object)
  - 데이터베이스의 data에 접근하기 위한 객체
  - DataBase에 접근하기 위한 로직 & 비즈니스 로직을 분리하기 위해 사용
- DTO(Data Transfer Object)
  - 계층 간 데이터 교환을 위해 사용하는 객체
  - 로직을 가지지 않는 순수한 데이터 객체(Getter & Setter만)
- VO(Value Object)
  - 값 오브젝트로 값을 위해 쓰인다. read-Only 특징(사용 중에 변경 불가능하며, 오직 읽기만 가능)
  - DTO와 달리 setter가 없어 값의 불변성을 보장

### Mustache를 이용한 HTML 중복 제거
- 중복되는 HTML을 별도 파일로 추출한다.
- {{#name}} {{/name}}으로 section을 반복 출력할 수 있다.
- Model 객체에 addAttribute를 통해서 model을 View로 전달할 수 있다.
- Templates은 WAS에서 처리되는 것, Static 파일은 Web Server에서 처리한다.
- 동적으로 처리하기 위해서는 templates로 옮겨주면 템플릿 엔진이 파일을 인식해서 처리 가능하다.
- 동적 처리를 위해서는 URL에 각각 할당해야한다.
- 로직을 구현하지않는 Templates은 MvcConfigurer를 이용해서 Configuration에서 Mapping이 가능하다.

### Controller AddController를 오버라이딩해서 Logic 없는 Template을 처리하는 경우
- View만을 처리할 Controller를 생성하는 것이기 때문에 같은 URL로 접근하는 경우 Controller간 충돌이 발생할 수 있다.

### Test 객체의 LifeCycle
- PR 참조

### 질문하기 구현

### DDD?
- Domain : 해결하고자하는 영역(관심 영역)
- Domain Driven Design

### 패키지 나누기
- 핵심 비즈니스 로직이 도메인에 존재해야한다.
- Repository, Controller 등 그룹별로 나누는 방법.
- 도메인끼리 묶는 방법. (User, Article 등)

### 빈이 thread safe?
- 지역변수 : 스레드 세이프
- 객체 : 힙을 공유하기 때문에 not safe
- HashTable, HashMap : HashTable은 safe
- Collection : not safe
- 빈은 상태를 저장하면 안된다.
- 스태틱도 안됨

### 그런 레스트 API로 괜찮은가?

### Adapter Pattern이 deprecated된 이유?
- interface의 default 메서드가 생기면서 adapter 패턴이 필요없어짐.
- 인터페이스에 구현을 못넣어서 하나만 구현+나머지는 비워둔 메서드로 extends 후 메서드를 사용 - Adapter 패턴
- JAVA8 이후로 implements로 바뀌면서 디폴트 메서드가 가능해져 deprecated된것

### DB 클래스
- User, Article
- ORM : 객체를 관계형 데이터로 Mapping해서 저장
- ODBC : 데이터베이스에 접근하기 위한 소프트웨어의 표준 규격
- JDBC : 자바에서 DB에 접근하기 위한 JAVA API

- Not Opinionated : 취향에 따라 사용가능 Spring
- Strong Opinionated : 의견이 반영됨(변경해서 사용 가능) SpringBoot

