package tech1.test.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech1.test.auth.dto.AuthUserResponseDTO;
import tech1.test.auth.dto.RegisterUserResponseDto;
import tech1.test.auth.dto.UserAuthDto;
import tech1.test.auth.exceptions.PasswordNotValidException;
import tech1.test.auth.model.ApplicationUser;
import tech1.test.security.JwtTokenProvider;

import java.util.Optional;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 25.12.2020
 */
@Slf4j
@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final AppUserService userService;

    private final PasswordEncoder bCryptPasswordEncoder;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       AppUserService userService, PasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public AuthUserResponseDTO login(UserAuthDto dto) {
        try {
            String username = dto.getUsername();
            Optional<ApplicationUser> user = userService.findByName(username);
            if (!user.isPresent()) {
                log.info("User {} does not exists", dto.getUsername());
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, dto.getPassword()));
            String token = jwtTokenProvider.createToken(username);

            log.info("user {} logged", dto.getUsername());
            return new AuthUserResponseDTO(token, username);
        } catch (AuthenticationException e) {
            log.info("Invalid username or password");
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    public RegisterUserResponseDto registerUser(UserAuthDto dto) {
        validatePassword(dto.getPassword());
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        userService.save(dto);
        return new RegisterUserResponseDto(true);
    }

    private void validatePassword(String password) {
        if (password.length() < 8 || password.length() >= 16) {
            log.info("Password length must be in range 8-16");
            throw new PasswordNotValidException("Password length must be in range 8-16");
        }
    }

}
