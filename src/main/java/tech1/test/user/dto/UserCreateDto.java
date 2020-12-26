package tech1.test.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateDto {
    @NotNull
    private String name;

    private Integer age;
}
