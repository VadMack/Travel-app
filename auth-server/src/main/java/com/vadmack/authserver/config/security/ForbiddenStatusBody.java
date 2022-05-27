package com.vadmack.authserver.config.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ForbiddenStatusBody {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private Integer status = 403;
    private String message;

    public ForbiddenStatusBody(String message) {
        timestamp = LocalDateTime.now();
        this.message = message;
    }
}
