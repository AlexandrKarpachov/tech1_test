package tech1.test.article.exceptions;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 22.12.2020
 */
public class ColorNotExistsException extends Exception {
    public ColorNotExistsException(String msg) {
        super(msg);
    }
}
