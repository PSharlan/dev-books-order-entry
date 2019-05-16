package com.devbooks.sharlan;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@SqlConfig(dataSource = "pgTestDataSource")
@Sql(value = {"/create.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/drop.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CatalogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //OFFERS
    @Test
    public void getAllOffers() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer1")))
                .andExpect(content().string(containsString("TestOffer2")))
                .andExpect(content().string(containsString("TestOffer3")))
                .andExpect(content().string(containsString("333.33")))
                .andExpect(content().string(containsString("cat1")));

    }

    @Test
    public void getOffersByIds() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers/list?ids=1&ids=2&ids=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer1")))
                .andExpect(content().string(containsString("TestOffer2")))
                .andExpect(content().string(containsString("TestOffer3")));
    }

    @Test
    public void getOfferById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer2")))
                .andExpect(content().string(containsString("222.22")))
                .andExpect(content().string(containsString("cat2")))
                .andExpect(content().string(containsString("tag2")))
                .andExpect(content().string(containsString("tag3")));
    }

    @Test
    public void notFoundOfferById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/offers/00"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createNewOfferAndDelete() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"TestOffer4\",\"description\":\"TestDescription4\",\"category\":{\"id\":4,\"name\":\"cat4\"},\"price\":444.44,\"tags\":[{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(foundStr, Map.class);
        int id = (Integer)map.get("id");

        this.mockMvc.perform(get("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("TestOffer4")))
                .andExpect(content().string(containsString("TestDescription4")))
                .andExpect(content().string(containsString("tag5")));

        this.mockMvc.perform(delete("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void updateOffer() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"TestOffer4\",\"description\":\"TestDescription4\",\"category\":{\"id\":4,\"name\":\"cat4\"},\"price\":444.44,\"tags\":[{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(foundStr, Map.class);
        int id = (Integer)map.get("id");

        this.mockMvc.perform(put("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + id + "\",\"name\":\"UpdatedTestOffer4\",\"description\":\"UpdatedTestDescription4\",\"category\":{\"id\":1,\"name\":\"cat1\"},\"price\":444.44,\"tags\":[{\"id\":1,\"name\":\"tag1\"},{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("UpdatedTestOffer4")))
                .andExpect(content().string(containsString("UpdatedTestDescription4")))
                .andExpect(content().string(containsString("tag1")))
                .andExpect(content().string(containsString("cat1")));

        this.mockMvc.perform(delete("/api/v1/catalog/offers/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateNotExistingOffer() throws Exception {
        this.mockMvc.perform(put("/api/v1/catalog/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"TestOffer4\",\"description\":\"TestDescription4\",\"category\":{\"id\":4,\"name\":\"cat4\"},\"price\":444.44,\"tags\":[{\"id\":4,\"name\":\"tag4\"},{\"id\":5,\"name\":\"tag5\"}]}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    //CATEGORIES
    @Test
    public void getAllCategories() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("cat1")))
                .andExpect(content().string(containsString("cat2")))
                .andExpect(content().string(containsString("cat3")))
                .andExpect(content().string(containsString("cat4")))
                .andExpect(content().string(containsString("cat5")));
    }

    @Test
    public void getCategoryById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/categories/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("cat2")));
    }

    @Test
    public void notFoundCategoryById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/categories/00"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createNewCategoryAndDelete() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"cat9\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(foundStr, Map.class);
        int id = (Integer)map.get("id");

        this.mockMvc.perform(get("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("cat9")));

        this.mockMvc.perform(delete("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void updateCategory() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"cat11\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(foundStr, Map.class);
        int id = (Integer)map.get("id");

        this.mockMvc.perform(put("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + id + "\",\"name\":\"updated_cat11\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("updated_cat11")));

        this.mockMvc.perform(delete("/api/v1/catalog/categories/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateNotExistingCategory() throws Exception {
        this.mockMvc.perform(put("/api/v1/catalog/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"cat44\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    //TAGS
    @Test
    public void getAllTags() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/tags"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tag1")))
                .andExpect(content().string(containsString("tag2")))
                .andExpect(content().string(containsString("tag3")))
                .andExpect(content().string(containsString("tag4")))
                .andExpect(content().string(containsString("tag5")));
    }

    @Test
    public void getTagById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/tags/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tag2")));
    }

    @Test
    public void notFoundTagById() throws Exception {
        this.mockMvc.perform(get("/api/v1/catalog/tags/00"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createNewTagAndDelete() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tag9\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(foundStr, Map.class);
        int id = (Integer)map.get("id");

        this.mockMvc.perform(get("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("tag9")));

        this.mockMvc.perform(delete("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void updateTag() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tag11\"}"))
                .andReturn();

        String foundStr = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = mapper.readValue(foundStr, Map.class);
        int id = (Integer)map.get("id");

        this.mockMvc.perform(put("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"" + id + "\",\"name\":\"updated_tag11\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("updated_tag11")));

        this.mockMvc.perform(delete("/api/v1/catalog/tags/" + id))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateNotExistingTag() throws Exception {
        this.mockMvc.perform(put("/api/v1/catalog/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tag44\"}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}