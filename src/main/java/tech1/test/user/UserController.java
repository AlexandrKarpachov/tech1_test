package tech1.test.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech1.test.article.exceptions.ColorNotExistsException;
import tech1.test.user.dto.UserCreateDto;
import tech1.test.user.dto.UserResponseDto;
import tech1.test.user.dto.UserShortDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 12.07.2020
 */
@RequestMapping("/user")
@RestController
public class UserController {

    private static final int CONST_THRESHOLD_VALUE = 3;

    @Autowired
    private UserService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserShortDto createUser(@Valid @RequestBody UserCreateDto dto) {
        return service.createUser(dto);
    }

    @GetMapping("/older-then")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> ageGreaterThen(@RequestParam Integer age) {
        return service.getByAgeGreaterThen(age);
    }

    @GetMapping("/has-three-plus-articles")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getByArticleCount() {
        return service.getByArticleCountGreaterThen(CONST_THRESHOLD_VALUE);
    }

    @GetMapping("/has-article-color")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getByArticleColor(@RequestParam String color) throws ColorNotExistsException {
        return service.getByArticleColor(color);
    }
}
