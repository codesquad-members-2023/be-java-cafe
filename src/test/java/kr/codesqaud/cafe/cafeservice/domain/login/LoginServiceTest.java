package kr.codesqaud.cafe.cafeservice.domain.login;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
        System.out.println(repository);
        repository.save(new Member("감자", "123", "123@123"));
        repository.save(new Member("고구마", "123", "123@123"));
//        Optional<Member> member1 = repository.findByLoginId("감자");
//        Optional<Member> member2 = repository.findByLoginId("고구마");
        Member member = service.login("감자", "123");
    }
}
