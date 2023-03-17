package kr.codesqaud.cafe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@RequestMapping("/user")
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/login").setViewName("/user/login");
        registry.addViewController("/form").setViewName("/user/form");
        registry.addViewController("/index").setViewName("/user/index");
    }
}
