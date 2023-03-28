package kr.codesqaud.cafe.config;

import kr.codesqaud.cafe.repository.JdbcTemplateArticleRepository;
import kr.codesqaud.cafe.repository.JdbcTemplateReplyRepository;
import kr.codesqaud.cafe.repository.JdbcTemplateUserRepository;
import kr.codesqaud.cafe.service.ArticleService;
import kr.codesqaud.cafe.service.ReplyService;
import kr.codesqaud.cafe.service.SessionUtil;
import kr.codesqaud.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository());
    }

    @Bean
    public ArticleService articleService() {
        return new ArticleService(articleRepository());
    }

    @Bean
    public ReplyService replyService() {
        return new ReplyService(replyRepository());
    }

    @Bean
    public SessionUtil sessionUtil() {
        return new SessionUtil();
    }

    @Primary
    public JdbcTemplateUserRepository userRepository() {
        return new JdbcTemplateUserRepository(dataSource);
    }

    @Primary
    public JdbcTemplateArticleRepository articleRepository() {
        return new JdbcTemplateArticleRepository(dataSource);
    }

    @Primary
    public JdbcTemplateReplyRepository replyRepository() {
        return new JdbcTemplateReplyRepository(dataSource);
    }
}
