package com.vadmack.authserver.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordStage2Request {
    @NotBlank(message = "The property 'password' is not defined")
    private String password;
}
