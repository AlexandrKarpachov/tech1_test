package tech1.test.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tech1.test.article.Color;
import tech1.test.article.model.Article;
import tech1.test.user.dto.UserCreateDto;
import tech1.test.user.model.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.runner.RunWith;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
@AutoConfigureMockMvc
public class UserControllerWithServiceIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    void whenGetByAgeGreaterThen_thenReturnValidResponse() throws Exception {

        String name = "name";
        UUID id = UUID.fromString("d95da7b7-c5c9-479f-a74e-a6523e6cf1b9");
        int userAge = 25;
        int ageLimit = 20;
        when(userRepository.findByAgeGreaterThanOrderByName(ageLimit)).thenReturn(
                Collections.singletonList(new User(id, name, userAge))
        );

        this.mockMvc
                .perform(get("/user/older-then?age=" + ageLimit))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].id").value(id.toString()))
                .andExpect(jsonPath("$[0].age").value(userAge));
    }

    @Test
    void whenGetByMoreThen3Articles_thenReturnValidResponse() throws Exception {
        String name = "Vasya";

        when(userRepository.getUserNamesWithArticleCount(3L))
                .thenReturn(Collections.singleton(name));

        this.mockMvc
                .perform(get("/user/has-three-plus-articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").value(name));
    }

    @Test
    void whenGetByArticleColor_thenReturnValidResponse() throws Exception {
        String name = "Vasya";
        int userAge = 32;
        UUID userId = UUID.fromString("d95da7b7-c5c9-479f-a74e-a6523e6cf1b9");
        User user = new User(userId, name, userAge);

        String firstArticleText = "fist art";
        String secondArticleText = "second article";
        String thirdArticleText = "third text";
        String fourArticleText = "four art text";
        UUID firstArtId = UUID.fromString("c95da7b7-c5c9-479f-a74e-a6523e6cf1b9");
        List<Article> articles = Arrays.asList(
                new Article(firstArtId, Color.BLUE, firstArticleText, user),
                new Article(UUID.randomUUID(), Color.GREEN, secondArticleText, user),
                new Article(UUID.randomUUID(), Color.RED, thirdArticleText, user),
                new Article(UUID.randomUUID(), Color.RED, fourArticleText, user));
        user.setArticles(articles);
        Color requiredColor = Color.GREEN;
        when(userRepository.findByArticleColor(requiredColor))
                .thenReturn(Collections.singletonList(user));

        this.mockMvc
                .perform(get("/user/has-article-color?color=green"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(userAge))
                .andExpect(jsonPath("$[0].id").value(userId.toString()))
                .andExpect(jsonPath("$[0].articles").isArray())
                .andExpect(jsonPath("$[0].articles", hasSize(4)))
                .andExpect(jsonPath("$[0].articles[0].id").value(firstArtId.toString()))
                .andExpect(jsonPath("$[0].articles[0].text").value(firstArticleText))
                .andExpect(jsonPath("$[0].articles[0].color").value("blue"))
                .andExpect(jsonPath("$[0].articles[3].text").value(fourArticleText));

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

    @Test
    void whenCreateNewUser_thenReturnValidResponse() throws Exception {
        String name = "Vasya";
        int userAge = 32;
        UUID userId = UUID.fromString("d95da7b7-c5c9-479f-a74e-a6523e6cf1b9");
        User user = new User(userId, name, userAge);
        UserCreateDto dto = new UserCreateDto(name, userAge);

        when(userRepository.save(ArgumentMatchers.any(User.class)))
                .thenReturn(user);


        this.mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(name));
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
