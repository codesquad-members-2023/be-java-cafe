package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryUserRepositoryTest {

    private MemoryUserRepository repository = new MemoryUserRepository();

    @Test
    @DisplayName("회원이 메모리 저장소에 잘 저장되는지 확인")
    void save() {
        User user = new User("Hyun", "1234", "황현", "ghkdgus29@naver.com");
        repository.save(user);

        List<User> users = repository.findAll();

        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0)).isEqualTo(user);
    }

    @Test
    @DisplayName("메모리 저장소의 sequence 가 제대로 증가하는지 확인")
    void saveUsers() {
        User user1 = new User("Hyun", "1234", "황현", "ghkdgus29@naver.com");
        User user2 = new User("Yoon", "1234", "황윤", "ghkddbs28@naver.com");

        repository.save(user1);
        repository.save(user2);

        List<User> users = repository.findAll();

        assertThat(users.size()).isEqualTo(2);

        assertThat(users.get(0)).isEqualTo(user1);
        assertThat(users.get(1)).isEqualTo(user2);
    }
}
