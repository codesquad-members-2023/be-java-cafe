package kr.codesqaud.cafe.cafeservice.repository;

import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.article.ArticleRepository;
import kr.codesqaud.cafe.cafeservice.repository.member.H2JdbcTemplateMemberRepository;
import kr.codesqaud.cafe.cafeservice.repository.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

@JdbcTest
class H2JdbcTemplateMemberRepositoryTest {

    @Autowired
    private DataSource dataSource;
    MemberRepository repository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        repository = new H2JdbcTemplateMemberRepository(dataSource);
    }

    @AfterEach
    void clear() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        String sql = "delete from member;" +
                "alter table member alter column id restart with 1";

        template.update(sql);
    }

    @Test
    void memberConnectionTest() {
        // given
        Member member = new Member();
        member.setUserName("user1");
        member.setPassword("password1");
        member.setEmail("email1");

        // when
        repository.save(member);
        List<Member> all = repository.findAll();
        // then

    }

    @Test
    void findAll() {
        List<Member> all = repository.findAll();
        for (Member member : all) {
            System.out.println(member);
        }
        System.out.println();
    }

    @Test
    void findById() {
        Member member = new Member();
        member.setUserName("감자");
        member.setPassword("password1");
        member.setEmail("email1");

        System.out.println( member.getId());
        Optional<Member> byId = repository.findById(member.getId());
        System.out.println(byId.get());
    }

    @Test
    void update() {
    }

    @Test
    void findByLoginId() {
    }
}
