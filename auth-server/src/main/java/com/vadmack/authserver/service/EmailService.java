package com.vadmack.authserver.service;

import com.vadmack.authserver.security.event.AbstractCustomEvent;
import com.vadmack.authserver.domain.entity.TokenType;
import com.vadmack.authserver.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final VerificationTokenService verificationTokenService;
    private final MailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * @param event
     * @param url       - path from base app url
     *                  Example: "/resetPassword"
     * @param subject   - subject of the email
     * @param tokenType
     */
    public void sendLink(AbstractCustomEvent event, String url, String subject, TokenType tokenType) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.createVerificationToken(user, token, tokenType);

        String recipientAddress = user.getEmail();
        String confirmationUrl = url + "?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom(senderEmail);
        email.setText(event.getAppUrl() + confirmationUrl);
        mailSender.send(email);
    }
}
