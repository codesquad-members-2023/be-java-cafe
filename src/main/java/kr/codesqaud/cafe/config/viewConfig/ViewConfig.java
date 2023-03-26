package kr.codesqaud.cafe.config.viewConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/createForm").setViewName("user/createForm");
        registry.addViewController("/user/login-failed").setViewName("user/login-failed");
        registry.addViewController("/qna/show").setViewName("qna/show");
    }
}
