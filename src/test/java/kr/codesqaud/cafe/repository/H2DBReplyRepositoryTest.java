package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class H2DBReplyRepositoryTest {

    private DataSource dataSource;
    private H2DBUserRepository userRepository;
    private H2DBArticleRepository articleRepository;
    private H2DBReplyRepository replyRepository;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource("jdbc:h2:mem:test", "sa", "");
        userRepository = new H2DBUserRepository(dataSource);
        articleRepository = new H2DBArticleRepository(dataSource);
        replyRepository = new H2DBReplyRepository(dataSource);

        userRepository.save(new User("Hyun", "1234", "황현", "ghkdgus29@naver.com"));
        userRepository.save(new User("Yoon", "1234", "황윤", "ghkddbs28@naver.com"));

        articleRepository.save(new Article(1, "실화냐?", "미안하다 이거 보여주려고 어그로 끌었다."));
    }

    @AfterEach
    void clear() {
        JdbcTemplate template = new JdbcTemplate(dataSource);

        String sql = "delete from reply;" +
                "alter table reply alter column id restart with 1;" +
                "delete from article;" +
                "alter table article alter column id restart with 1;" +
                "delete from users;" +
                "alter table users alter column id restart with 1;";

        template.update(sql);
    }

    @Test
    @DisplayName("댓글이 제대로 저장된다.")
    void save() {
        Reply reply = new Reply("진짜임?", 2, 1);
        replyRepository.save(reply);
    }
}
