package kr.codesqaud.cafe.repository.h2Repository;

import kr.codesqaud.cafe.basic.Article;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


import javax.sql.DataSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@SpringBootTest
class H2ArticleRepositoryTest {

    @Autowired
    private H2ArticleRepository articleRepository;

    @Autowired
    private H2UserRepository userRepository;

    @TestConfiguration
    static class TestConfig {

        @Bean
        H2ArticleRepository articleRepository(DataSource dataSource) {
            return new H2ArticleRepository(dataSource);
        }

        @Bean
        H2UserRepository userRepository(DataSource dataSource) {
            return new H2UserRepository(dataSource);
        }
    }


    @AfterEach
    public void afterEach() {
        articleRepository.delete(0);
    }

    @Test
    public void saveTest () {
        //given
        Article articleA = new Article("writerA", "titleA", "contentsA", Timestamp.valueOf(LocalDateTime.now()));
        articleRepository.save(articleA);

        //when

        List<Article> articles = articleRepository.findAll();

        //then
        assertThat(articleA).usingRecursiveComparison()
                .isEqualTo(articles.get(0));

        for (int i = 0; i < 4; i++) {
            System.out.println("articles.get(0).getArticleId(); = " + articles.get(0).getArticleId());
            System.out.println("articles.get(0).getWriter(); = " + articles.get(0).getWriter());
            System.out.println("articles.get(0).getTitle(); = " + articles.get(0).getTitle());
            System.out.println("articles.get(0).getContents(); = " + articles.get(0).getContents());
            System.out.println("articles.get(0).getTimestamp(); = " + articles.get(0).getTimeStamp());
        }


     }

}