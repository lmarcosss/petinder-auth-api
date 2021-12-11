package org.ifrs.auth;

import java.util.Arrays;
import java.util.HashSet;
import io.smallrye.jwt.build.Jwt;

public class TokenUtils {
    public static String generateToken(String login, Long userId) {
        return Jwt
            .upn(login) 
            .groups(new HashSet<>(Arrays.asList("User"))) 
            .claim("userId", userId) 
            .sign();
    }
}
