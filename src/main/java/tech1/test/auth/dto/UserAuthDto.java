package tech1.test.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class UserAuthDto {
    private String password;
    private String username;
}
