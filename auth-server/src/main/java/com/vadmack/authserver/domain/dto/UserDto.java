package com.vadmack.authserver.domain.dto;

import com.vadmack.authserver.domain.entity.Role;
import com.vadmack.authserver.domain.entity.UserType;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private Set<Role> authorities;
    private UserType userType;
    private Boolean enabled;
    private String refreshToken;
}
