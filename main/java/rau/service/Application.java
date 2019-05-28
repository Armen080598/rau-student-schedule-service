package rau.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public DriverManagerDataSource datasource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(this.getDBConfig("jdbcUrl"));
        ds.setUsername(this.getDBConfig("username"));
        ds.setPassword(this.getDBConfig("password"));
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DriverManagerDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private String getDBConfig(String key) {
        switch (key){
            case "jdbcUrl":
                return  "jdbc:postgresql://localhost:5342/studentschedule";
            case "username":
                return "postgres";
            case "password":
                return "Arm3nJan!@#";
        }
        return null;
    }
}

