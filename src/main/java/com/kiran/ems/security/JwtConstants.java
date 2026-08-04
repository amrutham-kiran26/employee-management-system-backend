package com.kiran.ems.security;

public final class JwtConstants {

    private JwtConstants() {
    }

    // 32+ Character Secret Key
    public static final String SECRET_KEY =
            "emsEmployeeManagementSystemJwtSecretKey2026";

    // Token Expiration Time (24 Hours)
    public static final long JWT_EXPIRATION =
            24 * 60 * 60 * 1000;

    // Token Prefix
    public static final String TOKEN_PREFIX =
            "Bearer ";

    // Authorization Header
    public static final String HEADER =
            "Authorization";

}