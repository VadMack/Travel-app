package com.vadmack.authserver.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegistrationRequest extends AuthRequest {
    @NotBlank(message = "The property 'email' is not defined")
    private String email;
    @NotBlank(message = "The property 'appUrl' is not defined")
    private String appUrl;
}
