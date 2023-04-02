package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Reply;
import kr.codesqaud.cafe.domain.User;
import kr.codesqaud.cafe.domain.dto.ArticleWithWriter;
import kr.codesqaud.cafe.domain.dto.ReplyWithUser;
import kr.codesqaud.cafe.domain.dto.SimpleArticleWithWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class MySQLArticleRepositoryTest {

    @Autowired
    private DataSource dataSource;

    private MySQLUserRepository userRepository;
    private MySQLArticleRepository articleRepository;
    private MySQLReplyRepository replyRepository;

    @BeforeEach
    void init() {
        userRepository = new MySQLUserRepository(dataSource);
        articleRepository = new MySQLArticleRepository(dataSource);
        replyRepository = new MySQLReplyRepository(dataSource);

        userRepository.save(new User("Hyun", "1234", "황현", "ghkdgus29@naver.com"));
        userRepository.save(new User("Yoon", "1234", "황윤", "ghkddbs28@naver.com"));
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
    @DisplayName("article 객체를 인자로 받아와 DB에 저장한다.")
    void save() {
        //given
        Article article = new Article(1, "테스트해볼까나", "테스트내용");

        //when
        articleRepository.save(article);

        //then
        List<SimpleArticleWithWriter> articles = articleRepository.findAll();
        assertThat(articles.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 번호로 DB에서 단 건 조회가 가능")
    void findById() {
        //given
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");
        articleRepository.save(article1);
        articleRepository.save(article2);

        //when
        ArticleWithWriter article = articleRepository.findById(1);

        //then
        assertThat(article.getUserId()).isEqualTo(1);
        assertThat(article.getContents()).isEqualTo("미안하다. 이거보여줄려고 어그로끌었다.");
    }

    @Test
    @DisplayName("게시글을 DB 에서 모두 조회 가능")
    void findAll() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        List<SimpleArticleWithWriter> articles = articleRepository.findAll();
        assertThat(articles.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("저장소에 존재하지 않는 Article 검색 시 예외 발생")
    void validateArticleExist() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        assertThatThrownBy(() -> {
            articleRepository.findById(3);
        })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않는 게시글입니다!");
    }

    @Test
    @DisplayName("저장소에서 특정 id의 Article을 제거 가능")
    void delete() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        articleRepository.delete(1);

        List<SimpleArticleWithWriter> articles = articleRepository.findAll();

        assertThat(articles.size()).isEqualTo(1);

        SimpleArticleWithWriter restArticle = articles.get(0);
        assertThat(restArticle.getTitle()).isEqualTo("진짜 실화냐?");
    }

    @Test
    @DisplayName("특정 id의 Article 제거 시, 달려있는 댓글들도 모두 제거")
    void deleteCascade() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        replyRepository.save(new Reply("zzz", 1, 1));
        replyRepository.save(new Reply("zzzzz", 2, 1));

        articleRepository.delete(1);

        List<ReplyWithUser> repliesInArticle1 = replyRepository.findByArticleId(1);

        assertThat(repliesInArticle1).isEmpty();
    }

    @Test
    @DisplayName("저장소에서 특정 id의 Article 수정 가능")
    void update() {
        Article article1 = new Article(1, "실화냐?", "미안하다. 이거보여줄려고 어그로끌었다.");
        Article article2 = new Article(2, "진짜 실화냐?", "미안하다. 이거보여줄려고 또 어그로끌었다.");

        articleRepository.save(article1);
        articleRepository.save(article2);

        Article updateArticle = new Article(2, "진짜 실화 아니었음", "미안");
        articleRepository.update(2, updateArticle);

        ArticleWithWriter article = articleRepository.findById(2);

        assertThat(article.getTitle()).isEqualTo(updateArticle.getTitle());
        assertThat(article.getContents()).isEqualTo(updateArticle.getContents());
    }
}