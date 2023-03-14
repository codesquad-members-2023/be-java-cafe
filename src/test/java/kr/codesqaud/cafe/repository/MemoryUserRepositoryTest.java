package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryUserRepositoryTest {

    MemoryUserRepository repository = new MemoryUserRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
//given
        User user = new User();
        user.setUserId("1");
        user.setUserName("spring");
//when
        repository.save(user);
//then
        User result = repository.findById(user.getUserId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void findByName() {
        User user1 = new User();
        user1.setUserName("aa");
        repository.save(user1);

        User user2 = new User();
        user2.setUserName("spring4");
        repository.save(user2);

        User result = repository.findByName("aa").get();

        assertThat(result.getUserName()).isEqualTo(user1.getUserName());
    }

    @Test
    void findAll() {
        User user1 = new User();
        user1.setUserName("spring3");
        repository.save(user1);

        User user2 = new User();
        user2.setUserName("spring4");
        repository.save(user2);

        List<User> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
