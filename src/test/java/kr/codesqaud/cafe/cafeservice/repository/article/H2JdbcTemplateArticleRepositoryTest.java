package kr.codesqaud.cafe.cafeservice.repository.article;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.repository.member.H2JdbcTemplateMemberRepository;
import kr.codesqaud.cafe.cafeservice.repository.member.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql("classpath:testdb/schema.sql")
@Sql("classpath:testdb/data.sql")
class H2JdbcTemplateArticleRepositoryTest {


    @Autowired
    JdbcTemplate jdbcTemplate;

    ArticleRepository articleRepository;
    MemberRepository memberRepository;
    Article article;
    Member member;

    @BeforeEach
    void init() {
        articleRepository = new H2JdbcTemplateArticleRepository(jdbcTemplate.getDataSource());
        memberRepository = new H2JdbcTemplateMemberRepository(jdbcTemplate.getDataSource());
    }

    @DisplayName("글을 저장한다.")
    @Test
    void save() {

    }

    @DisplayName("모든 글을 찾는다..")
    @Test
    void findAll() {
        List<Article> all = articleRepository.findAll();
    }

    @DisplayName("글을 하나 찾는다.")
    @Test
    void findById() {
        Optional<Article> repositoryById = articleRepository.findById(1L);
        assertEquals(repositoryById.orElseThrow().getId(), 1L);
    }

    @DisplayName("글을 삭제한다.")
    @Test
    void delete() {
        articleRepository.delete(1L);
        try {
            articleRepository.findById(1L);
        } catch (RuntimeException e) {
            Assertions.assertEquals("some exception message...", e.getMessage());
        }
    }

    @DisplayName("글을 업데이트한다.")
    @Test
    public void update() {
        Optional<Article> findArticle = articleRepository.findById(1L);
    }

    @DisplayName("로그인할  아이디를 찾는다.")
    @Test
    public void findByLoginId() {

    }
}
