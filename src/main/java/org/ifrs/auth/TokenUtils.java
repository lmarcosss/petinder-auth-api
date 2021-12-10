package org.ifrs.auth;

import java.util.Arrays;
import java.util.HashSet;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.ConfigProvider;

public class TokenUtils {
    public static String generateToken(String login, Long userId) {
        // String secretKey = ConfigProvider.getConfig().getValue("petinder.secret", String.class);
        return Jwt
            .upn(login) 
            .groups(new HashSet<>(Arrays.asList("User"))) 
            .claim("userId", userId) 
            .sign();
    }
}
