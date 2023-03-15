package kr.codesqaud.cafe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import kr.codesqaud.cafe.domain.JoinService;
import kr.codesqaud.cafe.domain.JoinServiceImpl;
import kr.codesqaud.cafe.domain.MemoryUserRepository;
import kr.codesqaud.cafe.domain.UserRepository;

@Configuration
public class AutoAppConfig extends WebMvcConfigurerAdapter {
    @Bean
    public JoinService joinService() {
        return new JoinServiceImpl(userRepository());
    }

    @Bean
    public UserRepository userRepository() {
        return new MemoryUserRepository();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/users/form").setViewName("user/form");
        registry.addViewController("/users/login").setViewName("user/login");
        registry.addViewController("/users/profile").setViewName("user/profile");
    }
}
