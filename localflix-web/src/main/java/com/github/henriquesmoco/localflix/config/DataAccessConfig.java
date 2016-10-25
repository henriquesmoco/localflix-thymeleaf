package com.github.henriquesmoco.localflix.config;

import com.github.henriquesmoco.localflix.web.CurrentTenantIdentifierResolverImpl;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.engine.jdbc.connections.spi.DataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@EnableSpringDataWebSupport
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.github.henriquesmoco.localflix")
public class DataAccessConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory(JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPackagesToScan("com.github.henriquesmoco.localflix");
        factory.setJpaVendorAdapter(jpaVendorAdapter);
        setJpaProperties(factory);
        return factory.getObject();
    }

    private void setJpaProperties(AbstractEntityManagerFactoryBean factory) {
        Map<String, Object> jpaProperties = factory.getJpaPropertyMap();
        jpaProperties.put(AvailableSettings.MULTI_TENANT, "SCHEMA");
        jpaProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, CurrentTenantIdentifierResolverImpl.class.getName());
        jpaProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, DataSourceBasedMultiTenantConnectionProviderImpl.class.getName());
        //DATASOURCE FOR selectAnyDatasource
        jpaProperties.put(AvailableSettings.DATASOURCE, "java:/" + CurrentTenantIdentifierResolverImpl.DEFAULT_TENANT_ID);

        factory.afterPropertiesSet();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(MySQL5Dialect.class.getName());
        return vendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

}
