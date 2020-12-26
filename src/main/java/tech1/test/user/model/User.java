package tech1.test.user.model;

import lombok.*;
import tech1.test.article.model.Article;
import tech1.test.user.dto.UserCreateDto;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    public User(UUID id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();

    public void addArticle(Article article) {
        article.setAuthor(this);
        articles.add(article);
    }

    public static User createFromDto(UserCreateDto createDto) {
        return User.builder().name(createDto.getName()).age(createDto.getAge()).build();
    }
}
