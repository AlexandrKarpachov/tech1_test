package tech1.test.article.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@Data
public class ArticleResponseDto {
    private UUID id;
    private String color;
    private String text;
    private UUID authorId;
}
