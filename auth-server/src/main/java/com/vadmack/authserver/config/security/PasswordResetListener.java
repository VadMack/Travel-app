package com.vadmack.authserver.config.security;

import com.vadmack.authserver.domain.entity.TokenType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {
    private final EmailUtil emailUtil;

    @Override
    public void onApplicationEvent(@NotNull OnPasswordResetEvent event) {
        emailUtil.sendLink(event, "/passwordReset", "Password Reset", TokenType.PASSWORD_RESET);
    }

}
