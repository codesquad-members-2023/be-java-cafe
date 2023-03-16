package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/form").setViewName("user/form");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/login-failed").setViewName("user/login-failed");
        registry.addViewController("/show").setViewName("qna/show");
        registry.addViewController("/qna/form").setViewName("qna/form");
        registry.addViewController("/qna/show").setViewName("qna/show");
    }
}
