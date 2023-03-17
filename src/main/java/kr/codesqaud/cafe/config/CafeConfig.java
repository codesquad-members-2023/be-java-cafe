package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.repository.MemberRepository;
import kr.codesqaud.cafe.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CafeConfig implements WebMvcConfigurer {

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/login").setViewName("/user/login");
        registry.addViewController("/form").setViewName("/user/form");
        registry.addViewController("/index").setViewName("/user/index");
    }
}
