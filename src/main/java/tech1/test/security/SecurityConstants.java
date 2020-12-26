package tech1.test.security;

public class SecurityConstants {
    public static final long TOKEN_EXPIRATION_TIME = 3_600_000; // 1 hour
    public static final String TOKEN_PREFIX = "Bearer_";
    public static final String AUTH_HEADER_STRING = "Authorization";
    public static final String[] ROUTES_WHITE_LIST = {
            "/auth/login",
            "/auth/register"
    };
}
