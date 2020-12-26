package tech1.test.user;

import tech1.test.article.dto.ArticleResponseDto;
import tech1.test.article.ArticleMapper;
import tech1.test.user.dto.UserResponseDto;
import tech1.test.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 24.12.2020
 */
public class UserMapper {

    public static UserResponseDto entityToResponseDto(User user) {

        List<ArticleResponseDto> articles = user.getArticles().stream()
                .map(ArticleMapper::entityToResponseDto)
                .collect(Collectors.toList());
        return new UserResponseDto(user.getId(), user.getName(), user.getAge(), articles);
    }
}
