package kr.codesqaud.cafe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import kr.codesqaud.cafe.domain.JoinService;
import kr.codesqaud.cafe.domain.JoinServiceImpl;
import kr.codesqaud.cafe.domain.MemoryUserRepository;
import kr.codesqaud.cafe.domain.UserRepository;

@Configuration
public class AutoAppConfig {
    @Bean
    public JoinService joinService() {
        return new JoinServiceImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }
}
