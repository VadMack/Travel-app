package com.vadmack.authserver.security.event;

import com.vadmack.authserver.domain.entity.User;

public class OnRegistrationCompleteEvent extends AbstractCustomEvent {

    public OnRegistrationCompleteEvent(User user, String appUrl) {
        super(user, appUrl);
    }
}
