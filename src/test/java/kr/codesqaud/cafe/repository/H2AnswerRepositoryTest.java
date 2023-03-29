package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Answer;
import kr.codesqaud.cafe.domain.Article;
import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.dto.AnswerViewDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Sql("classpath:db/init.sql")
@Sql("classpath:db/initData.sql")
class H2AnswerRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    AnswerRepository repository;
    MemberRepository memberRepository;
    Article article;
    Answer answer;

    static final String TEST_CONTENTS = "test 댓글";

    @BeforeEach
    void init() {
        repository = new H2AnswerRepository(jdbcTemplate.getDataSource());
        memberRepository = new H2MemberRepository(jdbcTemplate.getDataSource());

        answer = new Answer();
        answer.setArticleId(1L);
        answer.setContents(TEST_CONTENTS);
        answer.setWriter(memberRepository.findById(1L));
    }

    @Test
    @DisplayName("모든 댓글을 조회할 수 있다.")
    void findAll() {
        assertThat(repository.findAll(0L)).isEmpty();
    }

    @Test
    @DisplayName("댓글을 1개 저장하면 리스트 크기가 1 늘어나야한다.")
    void save() {
        int before = repository.findAll(1L).size();
        repository.save(answer);
        assertThat(repository.findAll(1L)).hasSize(before + 1);
    }

    @Test
    @DisplayName("댓글 ID로 내용을 읽어올 수 있다.")
    void findById() {
        repository.save(answer);
        assertThat(repository.findById(1L).getContents()).isEqualTo(TEST_CONTENTS);
    }

    @Test
    @DisplayName("댓글 저장 후 작성자의 이름을 확인할 수 있다.")
    void saveCheckDataWriter() {
        int before = repository.findAll(1L).size();
        repository.save(answer);
        assertThat(repository.findById(1L).getWriterNickname()).isEqualTo("자바지기");
    }
