package com.epam.esm.persistance.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.persistence.EntityManager;
import java.util.Properties;

@Configuration
@Profile("integration-test")
public class EmbeddedJdbcConfig {
  @Bean
  public EmbeddedDatabase embeddedDatabase() {
      return new EmbeddedDatabaseBuilder().generateUniqueName(true)
        .setType(EmbeddedDatabaseType.H2)
        .setScriptEncoding("UTF-8")
        .addDefaultScripts().build();
  }

    @Bean
    public EntityManager entityManager(LocalSessionFactoryBean sessionFactory) {
        SessionFactory localSessionFactoryBean = sessionFactory.getObject();
        assert localSessionFactoryBean != null;
        return localSessionFactoryBean.createEntityManager();
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory(@Autowired EmbeddedDatabase embeddedDatabase) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(embeddedDatabase);
        sessionFactory.setPackagesToScan("com.epam.esm.persistance.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }
    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return hibernateProperties;
    }

}