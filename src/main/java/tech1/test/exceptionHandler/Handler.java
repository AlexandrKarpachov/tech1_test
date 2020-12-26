package tech1.test.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import tech1.test.article.exceptions.ColorNotExistsException;
import tech1.test.auth.exceptions.JwtAuthenticationException;
import tech1.test.auth.exceptions.PasswordNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tech1.test.auth.exceptions.UserAlreadyExistsException;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
@Slf4j
@ControllerAdvice
public class Handler {

    @ExceptionHandler(ColorNotExistsException.class)
    public ResponseEntity<String> handleUserNotFoundException(ColorNotExistsException exception) {
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<String> handleJwtAuthenticationException(JwtAuthenticationException exception) {
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public ResponseEntity<String> handlePasswordNotValidException(PasswordNotValidException exception) {
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

}
