package com.epam.esm.persistance.config;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EntityScan("com.epam.esm.persistance.entity")
@EnableTransactionManagement
public class HibernateConfig {

    private final DataSource dataSource;


    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;
    @Value("${hibernate.dialect}")
    private String dialect;
    @Autowired
    public HibernateConfig(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public EntityManager entityManager(LocalSessionFactoryBean sessionFactory) {
        SessionFactory localSessionFactoryBean = sessionFactory.getObject();
        assert localSessionFactoryBean != null;
        return localSessionFactoryBean.createEntityManager();
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("com.epam.esm.persistance");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto",hbm2ddl);
        hibernateProperties.setProperty("hibernate.dialect",dialect);

        return hibernateProperties;
    }
}
