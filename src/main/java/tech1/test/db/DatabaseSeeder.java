package tech1.test.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import tech1.test.article.ArticleRepository;
import tech1.test.article.Color;
import tech1.test.article.model.Article;
import tech1.test.auth.AppUserRepo;
import tech1.test.auth.model.ApplicationUser;
import tech1.test.user.UserRepository;
import tech1.test.user.model.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DatabaseSeeder {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AppUserRepo appUserRepo;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        List<User> u = jdbcTemplate.query("SELECT * FROM users", (resultSet, rowNum) -> null);
        if(u.size() <= 0) {
            seedAppUserTable();
            seedUsersTable();
            seedArticlesTable();
        }
    }

    private void seedAppUserTable(){
        ApplicationUser user = new ApplicationUser("username", "$2y$10$F6wxi.Mzlumt7OzgQFU4duweWDidr1tdCcgesiNfv79JiX37MBcYu");
        appUserRepo.save(user);
    }

    private void seedUsersTable(){

        User user1 = new User();
        user1.setName("Tolstoy");
        user1.setAge(60);
        User user2 = new User();
        user2.setName("Puskin");
        user2.setAge(30);
        User user3 = new User();
        user3.setName("Shevchenko");
        user3.setAge(50);
        User user4 = new User();
        user4.setName("Lermontov");
        user4.setAge(25);

        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
    }

    private void seedArticlesTable() {
        Random randomize = new Random();
        List<User> users = userRepository.findAll();

        String[] articleBodies = { "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
                "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
                "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut",
                "ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit",
                "repudiandae veniam quaerat sunt sed\nalias aut fugiat sit autem sed est\nvoluptatem omnis possimus esse voluptatibus quis\nest aut tenetur dolor neque",
                "ut aspernatur corporis harum nihil quis provident sequi\nmollitia nobis aliquid molestiae\nperspiciatis et ea nemo ab reprehenderit accusantium quas\nvoluptate dolores velit et doloremque molestiae",
                "dolore placeat quibusdam ea quo vitae\nmagni quis enim qui quis quo nemo aut saepe\nquidem repellat excepturi ut quia\nsunt ut sequi eos ea sed quas",
                "dignissimos aperiam dolorem qui eum\nfacilis quibusdam animi sint suscipit qui sint possimus cum\nquaerat magni maiores excepturi\nipsam ut commodi dolor voluptatum modi aut vitae",
                "consectetur animi nesciunt iure dolore\nenim quia ad\nveniam autem ut quam aut nobis\net est aut quod aut provident voluptas autem voluptas",
                "quo et expedita modi cum officia vel magni\ndoloribus qui repudiandae\nvero nisi sit\nquos veniam quod sed accusamus veritatis error" };

        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        List<Article> articles = IntStream.range(0, articleBodies.length)
                .mapToObj(i -> {
                    Article newArticle = new Article();
                    newArticle.setText(articleBodies[i]);
                    newArticle.setAuthor(users.get(randomize.nextInt(users.size())));
                    newArticle.setColor(colors[randomize.nextInt(colors.length)]);
                    return newArticle;
                })
                .collect(Collectors.toList());

        articleRepository.saveAll(articles);
    }

}
