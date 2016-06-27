package com.github.henriquesmoco.localflix.config;

import com.github.henriquesmoco.localflix.web.CurrentTenantIdentifierResolverImpl;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.engine.jdbc.connections.spi.DataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories
public class DataAccessConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabasePlatform(MySQL5Dialect.class.getName());

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.github.henriquesmoco.localflix");
        setJpaProperties(factory.getJpaPropertyMap());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    private void setJpaProperties(Map<String, Object> jpaProperties) {
        jpaProperties.put(AvailableSettings.MULTI_TENANT, "SCHEMA");
        jpaProperties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, CurrentTenantIdentifierResolverImpl.class.getName());
        jpaProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, DataSourceBasedMultiTenantConnectionProviderImpl.class.getName());

        //DATASOURCE FOR selectAnyDatasource
        jpaProperties.put(AvailableSettings.DATASOURCE, CurrentTenantIdentifierResolverImpl.DEFAULT_TENANT_ID);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

}
