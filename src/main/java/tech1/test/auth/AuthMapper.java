package tech1.test.auth;

import tech1.test.auth.dto.UserAuthDto;
import tech1.test.auth.model.ApplicationUser;

public class AuthMapper {
    public static ApplicationUser AuthDtoToEntity(UserAuthDto dto) {
        return new ApplicationUser(dto.getUsername(), dto.getPassword());
    }

}
