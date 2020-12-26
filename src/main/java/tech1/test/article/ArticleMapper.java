package tech1.test.article;

import tech1.test.article.Color;
import tech1.test.article.dto.ArticleCreateDto;
import tech1.test.article.dto.ArticleResponseDto;
import tech1.test.article.exceptions.ColorNotExistsException;
import tech1.test.article.model.Article;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
public class ArticleMapper {
    public static ArticleResponseDto entityToResponseDto(Article article) {
        if (article == null) return null;

        ArticleResponseDto dto = new ArticleResponseDto();
        dto.setId(article.getId());
        dto.setColor(article.getColor().name().toLowerCase());
        dto.setText(article.getText());
        UUID authorId = article.getAuthor() != null ? article.getAuthor().getId() : null;
        dto.setAuthorId(authorId);
        return dto;
    }

    public static Article createDtoToEntity(ArticleCreateDto dto) throws ColorNotExistsException {
        Article result = new Article();
        Color color;
        try {
            color = Color.valueOf(dto.getColor().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ColorNotExistsException(String.format("Such color does not exists: %s. Available color set: %s",
                    dto.getColor(), Color.getValuesAsString()));
        }

        result.setColor(color);
        result.setText(dto.getText());
        return result;
    }
}
