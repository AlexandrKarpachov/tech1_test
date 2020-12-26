package tech1.test.article.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import tech1.test.article.Color;
import tech1.test.user.model.User;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "uuid-char")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;
}
