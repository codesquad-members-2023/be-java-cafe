package kr.codesqaud.cafe.cafeservice.domain.login;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class LoginServiceTest {

    MemberRepository repository;
    LoginService service;

    @Autowired
    public LoginServiceTest(MemberRepository repository, LoginService service) {
        this.repository = repository;
        this.service = service;
    }

    @Test
    void login() {
//        repository.save(new Member("감자", "123", "aaa@aaa"));
//        repository.save(new Member("고구마", "123", "bbb@bbb"));
//        Optional<Member> member1 = repository.findByLoginId("감자");
//        Optional<Member> member2 = repository.findByLoginId("고구마");
        Member member = service.login("감자", "123");
    }
}
