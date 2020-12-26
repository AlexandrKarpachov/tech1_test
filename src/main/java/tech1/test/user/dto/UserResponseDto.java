package tech1.test.user.dto;

import tech1.test.article.dto.ArticleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseDto {
    private UUID id;
    private String name;
    private int age;
    private List<ArticleResponseDto> articles;
}
