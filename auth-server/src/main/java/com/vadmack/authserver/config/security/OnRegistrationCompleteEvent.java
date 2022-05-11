package com.vadmack.authserver.config.security;

import com.vadmack.authserver.domain.entity.User;

public class OnRegistrationCompleteEvent extends AbstractCustomEvent {

    public OnRegistrationCompleteEvent(User user, String appUrl) {
        super(user, appUrl);
    }
}
