package com.vadmack.authserver.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "The property 'token' is not defined")
    private String token;
}
