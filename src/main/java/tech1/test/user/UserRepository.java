package tech1.test.user;

import org.springframework.data.jpa.repository.Query;
import tech1.test.article.Color;
import tech1.test.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByAgeGreaterThanOrderByName(Integer age);

    @Query("SELECT DISTINCT u FROM User u INNER JOIN u.articles a WHERE a.color = :color ORDER BY u.name")
    List<User> findByArticleColor(Color color);

    @Query("SELECT DISTINCT u.name FROM User u INNER JOIN u.articles ar " +
            "GROUP BY u.id HAVING COUNT(ar) > :artCount ORDER BY u.name")
    Set<String> getUserNamesWithArticleCount(Long artCount);
}
