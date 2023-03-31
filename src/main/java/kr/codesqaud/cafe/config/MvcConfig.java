package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.filter.PermissionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/templates/", "classpath:/static/");

    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/user/form").setViewName("user/form");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/article/form").setViewName("qna/form");
    }
    @Bean
    public FilterRegistrationBean<Filter> permissionFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();

        filterFilterRegistrationBean.setFilter(new PermissionFilter());
        filterFilterRegistrationBean.addUrlPatterns("/users/*", "/articles/*", "/article/*");
        return filterFilterRegistrationBean;
    }
}

