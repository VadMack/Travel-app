package com.vadmack.authserver.domain.dto;

import com.vadmack.authserver.domain.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UserNoIdDto {
    @NotBlank(message = "The property 'username' is not defined")
    private String username;
    @NotBlank(message = "The property 'password' is not defined")
    private String password;
    @NotBlank(message = "The property 'authorities' is not defined")
    private Set<Role> authorities;
}
