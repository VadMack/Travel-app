package com.vadmack.authserver.security.listener;

import com.vadmack.authserver.domain.entity.TokenType;
import com.vadmack.authserver.security.event.OnPasswordResetEvent;
import com.vadmack.authserver.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetListener implements ApplicationListener<OnPasswordResetEvent> {
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(@NotNull OnPasswordResetEvent event) {
        emailService.sendLink(event, "/passwordReset", "Password Reset", TokenType.PASSWORD_RESET);
    }

}
