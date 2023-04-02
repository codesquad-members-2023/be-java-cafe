package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Sql("classpath:db/schema.sql")
@Sql("classpath:db/data.sql")
class H2MemberRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    MemberRepository repository;
    Member member;

    @BeforeEach
    void init() {
        repository = new H2MemberRepository(jdbcTemplate.getDataSource());
        member = repository.findById(1L);
    }

    @Test
    @DisplayName("레포지토리에 접속하여 모든 회원을 조회할 수 있어야 한다.")
    void findAllData() {
        assertThat(repository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("멤버가 1명 저장되면 목록의 크기도 1증가해야 한다.")
    void save() {
        List<Member> exList = repository.findAll();

        Member newMember = new Member();
        newMember.setUserId("saveTest");
        newMember.setNickname("newpow");
        newMember.setPassword("12345");
        newMember.setEmail("test@nnoo.com");

        repository.save(newMember);
        assertThat(repository.findAll()).hasSize(exList.size() + 1);
    }

    @Test
    @DisplayName("ID(PK)로 회원 정보를 조회할 수 있다.")
    void findById() {
        Member byId = repository.findById(1L);
        assertThat(byId.getEmail()).isEqualTo("javajigi@slipp.net");
    }

    @Test
    @DisplayName("memberID으로 회원 정보를 조회할 수 있다.")
    void findBymemberId() throws Exception{
        Member byId = repository.findByMemberId("sanjigi").orElseThrow();
        assertThat(byId.getNickname()).isEqualTo("산지기");
    }

    @Test
    @DisplayName("같은 memberId가 없다면 true를 반환해야 한다.")
    void findWriterNickNameById() throws Exception{
        assertThat(repository.validMemberId("newpow")).isTrue();
    }
    
    @Test
    @DisplayName("회원 정보를 수정할 수 있다.")
    void updateTest() throws Exception{
        Member newMember = new Member();
        newMember.setNickname("newpow");
        repository.update(member, newMember);
        assertThat(repository.findById(member.getId()).getNickname()).isEqualTo("newpow");
    }

    @Test
    @DisplayName("회원 정보 수정 후, update at 이 새로운 시간으로 변경되어야 한다.")
    void updateTimeTest() {
        LocalDateTime before = LocalDateTime.now();
        Member newMember = new Member();
        newMember.setNickname("newpow");
        repository.update(member, newMember);
        assertThat(repository.findById(member.getId()).getUpdatedDate()).isAfter(before);
    }

    @Test
    @DisplayName("회원 정보 수정 후에도 create at은 변함이 없어야한다.")
    void createTimeTest() {
        LocalDateTime createdDate = member.getCreatedDate();
        Member newMember = new Member();
        newMember.setNickname("newpow");
        repository.update(member, newMember);
        assertThat(repository.findById(member.getId()).getCreatedDate()).isEqualTo(createdDate);
    }

    @Test
    @DisplayName("이미 있는 userId로 회원가입을 시도할 경우 예외가 발생해야한다.")
    void duplicateUserId() {
        Member newMember = new Member();
        newMember.setUserId("javajigi");
        newMember.setNickname("newpow");
        newMember.setPassword("12345");
        newMember.setEmail("test@nnoo.com");

        assertThatThrownBy(() -> repository.save(member))
                .isInstanceOf(DuplicateKeyException.class);
    }
}
