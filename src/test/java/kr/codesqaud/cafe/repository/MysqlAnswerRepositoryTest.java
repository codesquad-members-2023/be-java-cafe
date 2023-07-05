package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.domain.article.Reply;
import kr.codesqaud.cafe.domain.article.Writer;
import kr.codesqaud.cafe.dto.ReplyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql("classpath:db/mysql/schema.sql")
@Sql("classpath:db/mysql/data.sql")
class MysqlAnswerRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    ArticleRepository repository;
    MemberRepository memberRepository;
    Reply answer;

    static final String TEST_CONTENTS = "test 댓글";

    @BeforeEach
    void init() {
        repository = new H2ArticleRepository(jdbcTemplate.getDataSource());
        memberRepository = new H2MemberRepository(jdbcTemplate.getDataSource());

        answer = new Reply();
        answer.setArticleId(1L);
        answer.setContents(TEST_CONTENTS);

        Member member = memberRepository.findById(1L);
        answer.setWriter(new Writer(member.getId(), member.getNickname()));
    }

    @Test
    @DisplayName("모든 댓글을 조회할 수 있다.")
    void findAll() {
        assertThat(repository.findReplyByArticleId(0L)).isEmpty();
    }

    @Test
    @DisplayName("댓글을 1개 저장하면 리스트 크기가 1 늘어나야한다.")
    void save() {
        int before = repository.findReplyByArticleId(1L).size();
        repository.saveReply(answer);
        assertThat(repository.findReplyByArticleId(1L)).hasSize(before + 1);
    }

    @Test
    @DisplayName("댓글 ID로 내용을 읽어올 수 있다.")
    void findById() {
        repository.saveReply(answer);
        assertThat(repository.findReplyById(1L).getContents()).isEqualTo(TEST_CONTENTS);
    }

    @Test
    @DisplayName("댓글 저장 후 작성자의 이름을 확인할 수 있다.")
    void saveCheckDataWriter() {
        int before = repository.findReplyByArticleId(1L).size();
        repository.saveReply(answer);
        assertThat(repository.findReplyById(1L).getNickname()).isEqualTo("자바지기");
    }

    @Test
    @DisplayName("댓글을 수정하면 댓글 contents가 바뀌어야 한다.")
    void update() {
        repository.saveReply(answer);
        ReplyResponse exAnswer = repository.findReplyById(1L);
        Reply newAnswer = new Reply();
        newAnswer.setContents("수정된 테스트 댓글 내용");
        repository.updateReply(exAnswer.getAnswerIndex(), newAnswer.getContents());
        assertThat(repository.findReplyById(1L).getContents()).isEqualTo("수정된 테스트 댓글 내용");
    }

    @Test
    @DisplayName("댓글을 1 삭제하면 총 댓글 개수가 1 줄어야 한다.")
    void delete() {
        repository.saveReply(answer);
        int size = repository.findReplyByArticleId(1L).size();
        repository.deleteAReply(1L);
        assertThat(repository.findReplyByArticleId(1L)).hasSize(size-1);
    }

}
