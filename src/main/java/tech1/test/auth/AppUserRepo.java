package tech1.test.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech1.test.auth.model.ApplicationUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepo extends JpaRepository<ApplicationUser, UUID> {
    Optional<ApplicationUser> findByUsername(String username);
}
