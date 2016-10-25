package com.github.henriquesmoco.localflix.config;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { DataAccessTestConfig.class })
public class RepositoryIT {

    @Autowired
    @Qualifier("demoDatasource")
    private DataSource demoDts;

    protected JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void init() {
        jdbcTemplate = new JdbcTemplate(demoDts);
    }
}
