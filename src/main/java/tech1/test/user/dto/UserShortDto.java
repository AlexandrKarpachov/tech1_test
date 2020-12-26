package tech1.test.user.dto;

import tech1.test.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 12.07.2020
 */
@AllArgsConstructor
@Data
public class UserShortDto {
    private UUID id;
    private String name;
    private Integer age;

    public static UserShortDto fromModel(User user) {
        return new UserShortDto(user.getId(), user.getName(), user.getAge());
    }
}
