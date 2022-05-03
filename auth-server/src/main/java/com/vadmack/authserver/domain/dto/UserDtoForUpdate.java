package com.vadmack.authserver.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDtoForUpdate {
    @NotBlank(message = "The property 'username' is not defined")
    private String username;
    @NotBlank(message = "The property 'password' is not defined")
    private String password;
}
