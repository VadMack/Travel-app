package com.vas.travelapp.domain.user;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User implements UserDetails {
    private String username;
    private String password;
    private Set<Role> authorities;
    private boolean enabled = true;

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    public User(String username, Set<Role> authorities, boolean enabled) {
        this.username = username;
        this.authorities = authorities;
        this.enabled = enabled;
    }
}