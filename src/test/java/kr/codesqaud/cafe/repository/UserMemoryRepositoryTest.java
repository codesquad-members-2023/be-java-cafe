package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.basic.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class UserMemoryRepositoryTest {

    MemoryUserRepository repository;

    @BeforeEach
    public void init() {
        repository = new MemoryUserRepository();
    }

    @Test
    public void findTest () {
        //given
        User userA = new User("cire", "1234", "lee","dltpwns6@naver.com");
        User userB = new User("hello", "123", "kim","lee6820@naver.com");

        repository.join(userA);
        repository.join(userB);

        //when
        User resultUserA = repository.findUser("cire");
        User resultUserB = repository.findUser("hello");

        //then
        Assertions.assertEquals(userA, resultUserA);
        Assertions.assertEquals(userB, resultUserB);

    }

    @Test
    public void findAllTest () {
        //given
        User userA = new User("cire", "1234", "lee","dltpwns6@naver.com");
        User userB = new User("hello", "123", "kim","lee6820@naver.com");

        repository.join(userA);
        repository.join(userB);

        //when

        List<User> users = repository.findAll();

        //then

        Assertions.assertEquals(users.get(0) , userA);
        Assertions.assertEquals(users.get(1) , userB);
     }


}
