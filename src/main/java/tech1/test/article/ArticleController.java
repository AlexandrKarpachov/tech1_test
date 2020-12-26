package tech1.test.article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech1.test.article.dto.ArticleCreateDto;
import tech1.test.article.dto.ArticleResponseDto;
import tech1.test.article.exceptions.ColorNotExistsException;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@RequestMapping("/article")
@RestController
public class ArticleController {

    private final ArticleService service;

    @Autowired
    public ArticleController(ArticleService service) {
        this.service = service;
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleResponseDto create(@RequestBody ArticleCreateDto dto) throws ColorNotExistsException {
        return service.create(dto);
    }
}
