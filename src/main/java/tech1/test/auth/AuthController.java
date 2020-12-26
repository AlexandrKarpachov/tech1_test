package tech1.test.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech1.test.auth.dto.AuthUserResponseDTO;
import tech1.test.auth.dto.RegisterUserResponseDto;
import tech1.test.auth.dto.UserAuthDto;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 25.12.2020
 */
@RestController()
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public AuthUserResponseDTO login(@RequestBody UserAuthDto requestDto) {
        return service.login(requestDto);
    }

    @PostMapping("/register")
    public RegisterUserResponseDto register(@RequestBody UserAuthDto dto) {
        return service.registerUser(dto);
    }
}
