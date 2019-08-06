package xyz.aiyouv.aiutool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.aiyouv.aiutool.database.AiuGenerator;

/**
 * 个性化配置
 *
 * @author Aiyouv
 * @version 1.0
 **/
@Configuration
public class AiuConfig {
    private final JdbcTemplate jdbcTemplate;

    public AiuConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean()
    public AiuGenerator aiuGenerator (){
        return new AiuGenerator(jdbcTemplate);
    }
}
