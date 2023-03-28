package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Sql("classpath:db/init.sql")
@Sql("classpath:db/initData.sql")
class H2ArticleRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    ArticleRepository repository;
    MemberRepository memberRepository;
    Member member;
    Article article;


    @BeforeEach
    void init() throws FileNotFoundException {
        repository = new H2ArticleRepository(jdbcTemplate.getDataSource());
        memberRepository = new H2MemberRepository(jdbcTemplate.getDataSource());

        member = memberRepository.findById(1L);

        article = new Article();
        article.setWriter(member);
        article.setTitle("테스트를 해보자");
        article.setContents("통과를 해보자🔥");
    }

    @Test
    @DisplayName("레포지토리에 접속하여 모든 요소를 찾아올 수 있어야 한다.")
    void findAllData() {
        assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("article이 1개 저장되면 목록의 사이즈도 1증가해야 한다.")
    void save() {
        List<Article> exList = repository.findAll();
        repository.save(article);
        assertThat(repository.findAll()).hasSize(exList.size() + 1);
    }

    @Test
    @DisplayName("article ID에 따라 맞는 데이터를 반환해주어야 한다.")
    void findById() {
        Article byId = repository.findById(1L);
        assertThat(byId.getTitle()).isEqualTo("test1");
    }

    @Test
    @DisplayName("article ID로 글쓴이 ID를 알 수 있어야 한다.")
    public void findWriterIdById() throws Exception{
        Article byId = repository.findById(1L);
        assertThat(byId.getWriterId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("article ID로 글쓴이 닉네임을 알 수 있어야 한다.")
    public void findWriterNickNameById() throws Exception{
        Article byId = repository.findById(1L);
        assertThat(byId.getWriterNickName()).isEqualTo("산지기");
        System.out.printf(byId.toString());
    }

    @Test
    @DisplayName("article을 업데이트하여 제목을 바꿀 수 있다.")
    public void updateTest() throws Exception{
        Article exArticle = repository.findById(1L);

        Article newArticle = new Article();
        newArticle.setTitle("테스트 article");
        newArticle.setContents("테스트 contents");
        repository.update(exArticle, newArticle);

        assertThat(repository.findById(exArticle.getId()).getTitle()).isEqualTo("테스트 article");
    }

    @Test
    @DisplayName("article을 업데이트하여 내용을 바꿀 수 있다.")
    public void updateContentsTest() throws Exception{
        Article exArticle = repository.findById(1L);

        Article newArticle = new Article();
        newArticle.setTitle("테스트 article");
        newArticle.setContents("테스트 contents");
        repository.update(exArticle, newArticle);

        assertThat(repository.findById(exArticle.getId()).getContents()).isEqualTo("테스트 contents");
    }

    @Test
    @DisplayName("article을 업데이트하여 업데이트 일시가 변경되어야 한다.")
    public void updateUpdateDatetimeTest() throws Exception{
        LocalDateTime before = LocalDateTime.now();
        Article exArticle = repository.findById(1L);

        Article newArticle = new Article();
        newArticle.setTitle("테스트 article");
        newArticle.setContents("테스트 contents");
        repository.update(exArticle, newArticle);

        assertThat(repository.findById(exArticle.getId()).getUpdatedDate()).isAfter(before);
    }

    @Test
    @DisplayName("article을 업데이트하여도 생성 일시가 변경되면 안된다.")
    public void updateCreatedDatetimeTest() throws Exception{
        Article exArticle = repository.findById(1L);
        LocalDateTime createdDate = exArticle.getCreatedDate();

        Article newArticle = new Article();
        newArticle.setTitle("테스트 article");
        newArticle.setContents("테스트 contents");
        repository.update(exArticle, newArticle);

        assertThat(repository.findById(exArticle.getId()).getCreatedDate()).isEqualTo(createdDate);
    }

    @Test
    @DisplayName("article을 id로 삭제하면 전체 리스트 크기도 1줄어든다.")
    public void deleteMember() {
        int exSize = repository.findAll().size();
        repository.delete(1L);
        assertThat(repository.findAll()).hasSize(exSize-1);
    }

    @Test
    @DisplayName("article을 id로 삭제 후 findById를 하면 예외가 발생해야 한다.")
    public void deleteMemberThenFindById() {
       repository.delete(1L);
       assertThatThrownBy(() -> repository.findById(1L)).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
