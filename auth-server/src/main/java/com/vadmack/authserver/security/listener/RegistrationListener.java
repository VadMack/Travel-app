package com.vadmack.authserver.security.listener;

import com.vadmack.authserver.domain.entity.TokenType;
import com.vadmack.authserver.security.event.OnRegistrationCompleteEvent;
import com.vadmack.authserver.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final EmailService emailService;

    @Override
    public void onApplicationEvent(@NotNull OnRegistrationCompleteEvent event) {
        emailService.sendLink(event, "/registrationConfirm", "Registration Confirmation", TokenType.REGISTRATION);
    }
}
