package com.vadmack.authserver.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "The property 'email' is not defined")
    private String email;
    @NotBlank(message = "The property 'appUrl' is not defined")
    private String appUrl;
}
