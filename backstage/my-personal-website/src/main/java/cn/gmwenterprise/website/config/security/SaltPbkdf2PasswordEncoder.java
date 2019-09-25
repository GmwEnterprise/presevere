package cn.gmwenterprise.website.config.security;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class SaltPbkdf2PasswordEncoder extends Pbkdf2PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return super.matches(rawPassword, encodedPassword);
    }
}
