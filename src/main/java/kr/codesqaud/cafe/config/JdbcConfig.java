package kr.codesqaud.cafe.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JdbcConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://r4wkv4apxn9btls2.cbetxkdyhwsb.us-east-1.rds.amazonaws.com/a4kqb57yejyiu1lw");
        dataSource.setUsername("b3em6ctea2ecqnie");
        dataSource.setPassword("j9w7u24ezt5lbwqz");

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
