package tech1.test.article;

import org.springframework.data.jpa.repository.JpaRepository;
import tech1.test.article.model.Article;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

}
