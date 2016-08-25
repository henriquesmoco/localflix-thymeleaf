package com.github.henriquesmoco.localflix.catalog;


import com.github.henriquesmoco.localflix.config.WebAppMvcIT;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CatalogControllerIT extends WebAppMvcIT {

    @Autowired
    public CatalogRepository repo;

    @Test
    public void name() throws Exception {
        mockMvc.perform(get("/tenant1/catalog/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalog/list"))
                .andExpect(model().attribute("mediaList", is(empty())))
                ;
    }

    @Test
    public void name2() throws Exception {
        mockMvc.perform(get("/localflix-demo/catalog/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalog/list"))
                .andExpect(model().attribute("mediaList", is(not(empty()))))
        ;
    }

    @Test
    public void name3() throws Exception {
        List<Media> medias = repo.findAll();
        assertThat(medias, is(notNullValue()));
        assertThat(medias.get(0).getTitle(), not(emptyOrNullString()));
    }

}
