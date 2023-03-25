package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class CafeConfig implements WebMvcConfigurer {

    private DataSource dataSource;

    @Autowired
    public CafeConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcTemplateMemberRepository(dataSource);
    }

    @Bean
    public ArticleRepository articleRepository() {
        return new JdbcTemplateArticleRepository(dataSource);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/form").setViewName("user/form");
        registry.addViewController("/qna/show").setViewName("qna/show");
        registry.addViewController("/qna/article").setViewName("qna/form");
    }
}
