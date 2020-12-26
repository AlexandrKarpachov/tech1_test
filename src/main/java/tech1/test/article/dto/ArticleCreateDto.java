package tech1.test.article.dto;

import lombok.Data;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@Data
public class ArticleCreateDto {
    private String color;
    private String text;
}
