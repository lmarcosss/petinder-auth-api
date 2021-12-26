package org.ifrs.auth;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.smallrye.jwt.build.Jwt;

public class TokenUtils {
    public static String generateToken(String login, Long userId) {
        return Jwt
            .upn(login) 
            .groups(new HashSet<>(Arrays.asList("User"))) 
            .claim("userId", userId)
            .expiresIn(3000000)
            .sign();
    }

    public static Long getUserId(JsonWebToken token) {
        return Long.parseLong(token.claim("userId").get().toString());
    }
}
