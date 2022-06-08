package com.vadmack.authserver.security.event;

import com.vadmack.authserver.domain.entity.User;

public class OnPasswordResetEvent extends AbstractCustomEvent {
    public OnPasswordResetEvent(User user, String appUrl) {
        super(user, appUrl);
    }
}
