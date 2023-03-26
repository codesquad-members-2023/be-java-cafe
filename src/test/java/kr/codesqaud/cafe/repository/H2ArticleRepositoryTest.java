package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class H2ArticleRepositoryTest {

    @Autowired
    ArticleRepository repository;
    @Autowired
    MemberRepository memberRepository;

    Member member;
    Article article;

    @BeforeEach
    void init() {
        member = memberRepository.findById(1L);

        article = new Article();
        article.setWriter(member);
        article.setTitle("테스트를 해보자");
        article.setContents("통과를 해보자🔥");
    }

    @Test
    @Transactional
    @DisplayName("레포지토리에 접속하여 모든 요소를 찾아올 수 있어야 한다.")
    void findAllData() {
        assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    @Transactional
    @DisplayName("article이 1개 저장되면 목록의 사이즈도 1증가해야 한다.")
    void save() {
        List<Article> exList = repository.findAll();
        repository.save(article);
        assertThat(repository.findAll()).hasSize(exList.size() + 1);
    }

    @Test
    @Transactional
    @DisplayName("article ID에 따라 맞는 데이터를 반환해주어야 한다.")
    void findById() {
        Article byId = repository.findById(1L);
        assertThat(byId.getTitle()).isEqualTo("test1");
    }

    @Test
    @Transactional
    @DisplayName("article ID로 글쓴이 ID를 알 수 있어야 한다.")
    public void findWriterIdById() throws Exception{
        Article byId = repository.findById(1L);
        assertThat(byId.getWriterId()).isEqualTo(2L);
    }

    @Test
    @Transactional
    @DisplayName("article ID로 글쓴이 닉네임을 알 수 있어야 한다.")
    public void findWriterNickNameById() throws Exception{
        Article byId = repository.findById(1L);
        assertThat(byId.getWriterNickName()).isEqualTo("산지기");
        System.out.printf(byId.toString());
    }
}
