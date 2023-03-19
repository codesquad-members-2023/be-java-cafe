package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class UserRepositoryTest {

    UserRepository repository = new UserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
//given
        User user = new User("aa","1234","aaName","aa@aa");
//when
        repository.save(user);
//then
        User result = repository.findById(user.getUserId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void findByName() {
        User user1 = new User("aa","1234","aaName","aa@aa");
        repository.save(user1);

        User user2 = new User("bb","1234","bbName","bb@bb");
        repository.save(user2);

        Optional<User> result = repository.findByName("aaName");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getUserName()).isEqualTo(user1.getUserName());

    }

    @Test
    void findAll() {
        User user1 = new User("aa","1234","aaName","aa@aa");
        repository.save(user1);

        User user2 = new User("bb","1234","bbName","bb@bb");
        repository.save(user2);

        List<User> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
