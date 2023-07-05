package com.epam.esm.persistance.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:app.properties")
public class JdbcConfiguration {


    @Bean
    public DataSource dataSource(@Value("${user}") String user,
                                 @Value("${password}") String password,
                                 @Value("${driver}") String className,
                                 @Value("${url}") String connectionUrl,
                                 @Value("${cachePrepStmts}") String cache,
                                 @Value("${prepStmtCacheSize}") String size,
                                 @Value("${prepStmtCacheSqlLimit}") String limit) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(className);
        config.setJdbcUrl(connectionUrl);
        config.setUsername(user);
        config.setPassword(password);
        config.addDataSourceProperty("cachePrepStmts",cache);
        config.addDataSourceProperty("prepStmtCacheSize", size);
        config.addDataSourceProperty("prepStmtCacheSqlLimit",limit);

        return new HikariDataSource(config);
    }


}
