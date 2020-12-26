package tech1.test.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tech1.test.article.Color;
import tech1.test.user.dto.UserCreateDto;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import org.junit.Before;
//import org.junit.runner.RunWith;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 13.07.2020
 */
@WithMockUser
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerWithServiceAndRepoIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Test
    void whenGetByAgeGreaterThen_thenReturnValidResponse() throws Exception {
        int ageLimit = 30;

        this.mockMvc
                .perform(get("/user/older-then?age=" + ageLimit))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Anna"))
                .andExpect(jsonPath("$[0].id").value("0a36f5af-c63e-4cfd-930a-6d56754d7b79"))
                .andExpect(jsonPath("$[0].age").value(50))
                .andExpect(jsonPath("$[1].name").value("Pavel"));
    }


    @PreAuthorize("authenticated")
    @Test
    void whenGetByMoreThen3Articles_thenReturnValidResponse() throws Exception {

        this.mockMvc
                .perform(get("/user/has-three-plus-articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value("Alex"));
    }

    @Test
    void whenGetByArticleColor_thenReturnValidResponse() throws Exception {


        this.mockMvc
                .perform(get("/user/has-article-color?color=red"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Pavel"))
                .andExpect(jsonPath("$[1].name").value("Pavel"))
                .andExpect(jsonPath("$[0].articles[0].color").value("red"))
                .andExpect(jsonPath("$[1].articles[0].color").value("red"));
    }

    @Test
    void whenAttemptToGetByWrongColor_thenThrowColorNotExistsException() throws Exception {
        String wrongColorName = "sero-buro-malinoviy";
        String msg = String.format("Such color does not exists: %s. Available color set: %s",
                wrongColorName, Color.getValuesAsString());

        this.mockMvc
                .perform(get("/user/has-article-color?color=" + wrongColorName))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(msg));
    }

    @Sql(scripts = { "/clean.sql", "/data.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void whenCreateNewUser_thenReturnValidResponse() throws Exception {
        String name = "Vasya";
        int userAge = 32;
        UserCreateDto dto = new UserCreateDto(name, userAge);


        this.mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void whenCreateWithoutName_thenReturnBadRequest() throws Exception {

        this.mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"age\": 22}"))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
