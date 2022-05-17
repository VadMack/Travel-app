package com.vadmack.authserver.config.security;

import com.vadmack.authserver.domain.entity.TokenType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final EmailUtil emailUtil;

    @Override
    public void onApplicationEvent(@NotNull OnRegistrationCompleteEvent event) {
        emailUtil.sendLink(event, "/registrationConfirm", "Registration Confirmation", TokenType.PASSWORD_RESET);
    }
}
