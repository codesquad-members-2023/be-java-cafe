package kr.codesqaud.cafe.repository;

import kr.codesqaud.cafe.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemoryUserRepositoryTest {

    DataSource dataSource;
    MemoryUserRepository memoryUserRepository;

    @BeforeEach
    void init() {
        dataSource = new DriverManagerDataSource("jdbc:h2:~/db/CAFEDB", "sa", "");
        memoryUserRepository = new MemoryUserRepository(dataSource);
    }
    
    @Test
    @Transactional
    public void findAllTestWithDB() throws Exception{
        List<Member> all = memoryUserRepository.findAll();
        assertThat(all).hasSize(1);
    }

    @Test
    @Transactional
    @DisplayName("이름이 같은 유저가 없을 경우 저장되어야 한다.")
    public void saveTestWithDB() throws Exception{
        Member member = new Member("user","user@asdf","1234","asdf@asdf");
        memoryUserRepository.save(member);
        assertThat(memoryUserRepository.findAll()).hasSize(2);
    }

    @Test
    @Transactional
    @DisplayName("이름이 같은 유저가 있을 경우 저장되면 안된다.")
    public void memberNameTestWithDB() throws Exception{
        Member member1 = new Member("user","user@asdf","1234","asdf@asdf");
        Member member2 = new Member("user","user@asdf","1234","asdf@asdf");
        memoryUserRepository.save(member1);
        memoryUserRepository.save(member2);
        assertThat(memoryUserRepository.findAll()).hasSize(1);
    }

}
