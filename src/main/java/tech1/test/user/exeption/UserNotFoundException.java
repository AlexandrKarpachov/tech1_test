package tech1.test.user.exeption;

import java.util.function.Supplier;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 12.07.2020
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
