package com.takdelivery.util;

import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class PasswordEncoder {

    public String encode(String rawPassword) {
        // En un proyecto real se usaría BCrypt, aquí usamos un hash simple o base64 para que compile
        return Base64.getEncoder().encodeToString(rawPassword.getBytes());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
