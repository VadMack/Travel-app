package com.vadmack.authserver.domain.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@CompoundIndex(name = "ind_username_userType", def = "{'username': 1, 'userType': 1}", unique = true)
@Document("users")
public class User implements UserDetails {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";

    @Id
    private Long id;
    private String username;
    private String password;
    @Indexed
    private String email;
    private Set<Role> authorities;
    private boolean enabled = true;
    private UserType userType;

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

    public User(String username, String password, Set<Role> authorities, boolean enabled) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    public User(Long id, String username, Set<Role> authorities, boolean enabled, UserType userType) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.enabled = enabled;
        this.userType = userType;
    }
}
