package com.vadmack.authserver.config.security;

import com.vadmack.authserver.domain.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public abstract class AbstractCustomEvent extends ApplicationEvent {
    private User user;
    private String appUrl;

    public AbstractCustomEvent(User user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }
}
