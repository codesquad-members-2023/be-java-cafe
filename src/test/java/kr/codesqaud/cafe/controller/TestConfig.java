package kr.codesqaud.cafe.controller;

import javax.sql.DataSource;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@TestConfiguration
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource("jdbc:h2:tcp://localhost/~/be-java-cafe/test", "sa", "");
    }
}
