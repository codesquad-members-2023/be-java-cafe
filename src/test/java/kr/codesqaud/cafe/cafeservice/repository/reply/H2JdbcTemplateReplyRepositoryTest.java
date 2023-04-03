package kr.codesqaud.cafe.cafeservice.repository.reply;

import kr.codesqaud.cafe.cafeservice.domain.Article;
import kr.codesqaud.cafe.cafeservice.domain.Member;
import kr.codesqaud.cafe.cafeservice.domain.Reply;
import kr.codesqaud.cafe.cafeservice.repository.article.H2JdbcTemplateArticleRepository;
import kr.codesqaud.cafe.cafeservice.repository.member.H2JdbcTemplateMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql("classpath:testdb/schema.sql")
@Sql("classpath:testdb/data.sql")
class H2JdbcTemplateReplyRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    private H2JdbcTemplateMemberRepository memberRepository;
    private H2JdbcTemplateArticleRepository articleRepository;
    private H2JdbcTemplateReplyRepository replyRepository;
    Article article;
    Reply reply;

    @BeforeEach
    void init() {
        memberRepository = new H2JdbcTemplateMemberRepository(jdbcTemplate.getDataSource());
        articleRepository = new H2JdbcTemplateArticleRepository(jdbcTemplate.getDataSource());
        replyRepository = new H2JdbcTemplateReplyRepository(jdbcTemplate);

        reply = new Reply();
        reply.setArticle_id(1L);
        reply.setMember_id(1L);
        reply.setContent("안녕하세요");
        reply.setNickName("감자");
        //insert into member (userName,password,email,nickName) values ('감자', '1234', 'aaa@aaa','tester1');
        // insert into member (userName,password,email,nickName) values ('고구마', '1234', 'bbb@bbb','tester2');
        //insert into article (writer,title,content,member_id) values ('감자', '자바지기입니다', 'test1',1);
        //insert into article (writer,title,content,member_id) values ('고구마', '고구마입니다', 'test2',2);
        //INSERT INTO reply (member_id, article_id, content, reply_date, deleted)
        //VALUES (1, 1, '자바지기입니다!',now(),false);
        //
        //INSERT INTO reply (member_id, article_id, content, reply_date, deleted)
        //VALUES (2, 1, '고구마입니다.',now(),false);
        //
        //INSERT INTO reply (member_id, article_id, content,reply_date,deleted)
        //VALUES (3, 2, '셀러드입니다.',now(),false);
    }

    @Test
    @DisplayName("댓글 저장")
    void save() {
        int size = replyRepository.findAll(1L).size();
        replyRepository.save(reply);
        System.out.println("replyRepository = " + replyRepository.findAll(1L));
        assertThat(replyRepository.findAll(1L)).hasSize(size + 1);
        assertThat(reply.getId()).isEqualTo(1L);
        assertThat(reply.getContent()).isEqualTo("재밌네요");
        assertThat(reply.getNickName()).isEqualTo("감자");
        assertThat(reply.getMember_id()).isEqualTo(1L);
        assertThat(reply.getArticle_id()).isEqualTo(1L);
    }

    @Test
    @DisplayName("댓글 검색.")
    void findById() {
    }

    @Test
    @DisplayName("댓글 삭제")
    void delete() {

        Reply reply1 = new Reply();
        Reply reply2 = new Reply();

        reply1.setMember_id(1L);
        reply1.setArticle_id(1L);
        reply1.setContent("안녕하세요");
        reply1.setNickName("감자");

        reply2.setMember_id(2L);
        reply2.setArticle_id(1L);
        reply2.setContent("안녕하세요");
        reply2.setNickName("고구마");

        replyRepository.save(reply1);
        replyRepository.save(reply2);
        int size = replyRepository.findAll(1L).size();
        assertThat(replyRepository.findAll(1L)).hasSize(size);
        replyRepository.delete(1L);
        assertThat(replyRepository.findAll(1L)).hasSize(size - 1);
    }

    @Test
    @DisplayName("모든 댓글.")
    void findAll() {
    }
}
