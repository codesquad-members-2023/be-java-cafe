package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import kr.codesqaud.cafe.repository.Member.JdbcMemberRepository;
import kr.codesqaud.cafe.repository.Member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@JdbcTest
class UserRepositoryTest {

//    JdbcMemberRepository repository = new JdbcMemberRepository() {
//
//    @Test
//    public void save() {
////given
//        Member user = new Member("aa","1234","aaName","aa@aa");
////when
//        repository.save(user);
////then
//        Member result = repository.findById(user.getUserId()).get();
//        assertThat(result).isEqualTo(user);
//    }
//
//    @Test
//    void findByName() {
//        Member user1 = new Member("aa","1234","aaName","aa@aa");
//        repository.save(user1);
//
//        Member user2 = new Member("bb","1234","bbName","bb@bb");
//        repository.save(user2);
//
//        Optional<Member> result = repository.findByName("aaName");
//
//        assertThat(result.isPresent()).isTrue();
//        assertThat(result.get().getUserName()).isEqualTo(user1.getUserName());
//
//    }

//    @Test
//    void findAll() {
//        Member user1 = new Member("aa","1234","aaName","aa@aa");
//        repository.save(user1);
//
//        Member user2 = new Member("bb","1234","bbName","bb@bb");
//        repository.save(user2);
//
//        List<Member> result = repository.findAll();
//
//        assertThat(result.size()).isEqualTo(2);
//    }
}
