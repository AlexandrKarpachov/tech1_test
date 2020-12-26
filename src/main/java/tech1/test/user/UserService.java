package tech1.test.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech1.test.article.Color;
import tech1.test.article.exceptions.ColorNotExistsException;
import tech1.test.user.dto.UserCreateDto;
import tech1.test.user.dto.UserResponseDto;
import tech1.test.user.dto.UserShortDto;
import tech1.test.user.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 12.07.2020
 */
@Slf4j
@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserShortDto createUser(UserCreateDto createDto) {
        User user = this.repository.save(User.createFromDto(createDto));
        log.info("User= [{}] has been created ", user.toString());
        return UserShortDto.fromModel(user);
    }

    public List<UserResponseDto> getByAgeGreaterThen(int age) {
        log.info("try to get users older then = {}", age);
        return repository.findByAgeGreaterThanOrderByName(age).stream()
                .map(UserMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getByArticleColor(String color) throws ColorNotExistsException {
        log.info("try to get users with article color = {}", color);
        return repository.findByArticleColor(this.getColor(color))
                .stream()
                .map(UserMapper::entityToResponseDto)
                .collect(Collectors.toList());
    }

    private Color getColor(String color) throws ColorNotExistsException {
        try {
            return Color.valueOf(color.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.info("wrong color name =  {}", color);
            throw new ColorNotExistsException(String.format("Such color does not exists: %s. Available color set: %s",
                    color, Color.getValuesAsString()));
        }
    }

    public Set<String> getByArticleCountGreaterThen(long count) {
        log.info("try to get User with Article count > {}", count);
        return repository.getUserNamesWithArticleCount(count);
    }

}
