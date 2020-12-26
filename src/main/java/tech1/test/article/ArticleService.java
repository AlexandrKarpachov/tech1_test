package tech1.test.article;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech1.test.article.dto.ArticleCreateDto;
import tech1.test.article.dto.ArticleResponseDto;
import tech1.test.article.exceptions.ColorNotExistsException;
import tech1.test.article.model.Article;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@Slf4j
@Service
public class ArticleService {
    private final ArticleRepository repository;

    @Autowired
    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public ArticleResponseDto create(ArticleCreateDto dto) throws ColorNotExistsException {
        Article article = repository.save(ArticleMapper.createDtoToEntity(dto));
        log.info("A new article =[{}] has been created.", article.toString());
        return ArticleMapper.entityToResponseDto(article);
    }

}
