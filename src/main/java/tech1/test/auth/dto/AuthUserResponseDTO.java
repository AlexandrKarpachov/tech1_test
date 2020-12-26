package tech1.test.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthUserResponseDTO {
    private String token;
    private String username;
}
