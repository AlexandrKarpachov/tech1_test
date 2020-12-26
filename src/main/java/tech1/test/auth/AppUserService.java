package tech1.test.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech1.test.auth.dto.UserAuthDto;
import tech1.test.auth.model.ApplicationUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 25.12.2020
 */
@Service
public class AppUserService implements UserDetailsService {

    @Autowired
    private AppUserRepo userRepo;

    public Optional<ApplicationUser> findByName(String name) {
        return userRepo.findByUsername(name);
    }

    public void save(UserAuthDto dto) {
        userRepo.save(AuthMapper.AuthDtoToEntity(dto));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo
                .findByUsername(username)
                .map(u ->
                        new org.springframework.security.core.userdetails.User(
                                u.getUsername(),
                                u.getPassword(),
                                true,
                                true,
                                true,
                                true,
                                new ArrayList<>()
                        ))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
