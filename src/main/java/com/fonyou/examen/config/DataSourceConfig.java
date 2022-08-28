package com.fonyou.examen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "dsExamen")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSourceToken() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "jdbcExamen")
    public NamedParameterJdbcTemplate jdbcUtilsTreasury(@Qualifier("dsExamen") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    @Primary
    @Bean(name = "txmExamen")
    @Autowired
    DataSourceTransactionManager tmsExamen(@Qualifier("dsExamen") DataSource ds) {
        DataSourceTransactionManager txm = new DataSourceTransactionManager(ds);
        return txm;
    }
    
}