package com.github.henriquesmoco.localflix.config;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.dialect.HSQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;


@Configuration
@Import(DataAccessConfig.class)
public class DataAccessTestConfig {

    @Autowired
    private ApplicationContext appContext;


    @PostConstruct
    public void bindJndiDatasources() throws NamingException {
        JndiTemplate jndiTemplate = new JndiTemplate();
        jndiTemplate.bind("java:/localflix-demo", appContext.getBean("demoDatasource"));
        jndiTemplate.bind("java:/tenant1", appContext.getBean("tenant1Datasource"));
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(HSQLDialect.class.getName());
        return vendorAdapter;
    }

    @Bean
    public DataSource demoDatasource() {
        return createHsqlDatabaseBuilder().build();
    }

    @Bean
    public DataSource tenant1Datasource() {
        return createHsqlDatabaseBuilder().build();
    }

    private EmbeddedDatabaseBuilder createHsqlDatabaseBuilder() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(HSQL)
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true);
    }

    @Bean
    public SpringLiquibase liquibaseDemo(DataSource demoDatasource) {
        return createSpringLiquibaseConfig(demoDatasource, "testdata-demo");
    }

    @Bean
    public SpringLiquibase liquibaseTenant1(DataSource tenant1Datasource) {
        return createSpringLiquibaseConfig(tenant1Datasource, "testdata-tenant1");
    }

    private SpringLiquibase createSpringLiquibaseConfig(DataSource dts, String contexts) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db-changelog.xml");
        liquibase.setDataSource(dts);
        liquibase.setContexts(contexts);
        return liquibase;
    }


}
