package tech1.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech1.test.article.ArticleRepository;
import tech1.test.article.exceptions.ColorNotExistsException;
import tech1.test.security.JwtTokenProvider;
import tech1.test.user.dto.UserCreateDto;
import tech1.test.user.dto.UserResponseDto;
import tech1.test.user.dto.UserShortDto;
import tech1.test.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 23.12.2020
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider provider;

    @BeforeEach
    void setUp() {
        this.userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void whenGetByAgeGreaterThen_thenReturnCorrectList() {
        //mock
        List<User> testUsers = new ArrayList<>();
        testUsers.add(new User(UUID.randomUUID(), "user 1", 30));
        testUsers.add(new User(UUID.randomUUID(), "user 2", 35));
        when(userRepository.findByAgeGreaterThanOrderByName(25)).thenReturn(testUsers);

        //call
        List<UserResponseDto> users = userService.getByAgeGreaterThen(25);

        //validate
        assertEquals(users.size(), testUsers.size());
        for (int i = 0; i < users.size(); i++) {
            assertThat(users.get(i), samePropertyValuesAs(
                    UserMapper.entityToResponseDto(testUsers.get(i))
            ));
        }
    }


    @Test
    void whenGetByWrongColor_thenThrowColorNotExistsException() {
        assertThrows(ColorNotExistsException.class, () -> userService.getByArticleColor("wrong color name"));
    }

    @Test
    void whenCreateUser_thenReturnCorrectUserShortDto() {
        //mock
        UUID id = UUID.fromString("d95da7b7-c5c9-479f-a74e-a6523e6cf1b9");
        String name = "name";
        User newUser = new User(id, name, 33);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(newUser);

        //call
        UserCreateDto createDto = new UserCreateDto();
        createDto.setName(name);
        UserShortDto result = userService.createUser(createDto);

        //validate
        assertEquals(result.getName(), name);
        assertNotNull(result.getId());
    }
}
