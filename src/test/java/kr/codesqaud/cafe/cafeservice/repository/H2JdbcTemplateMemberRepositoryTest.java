package kr.codesqaud.cafe.cafeservice.repository;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.article.H2JdbcTemplateArticleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class H2JdbcTemplateMemberRepositoryTest {


    @Autowired
    private DataSource dataSource;
    H2JdbcTemplateMemberRepository repository;
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
