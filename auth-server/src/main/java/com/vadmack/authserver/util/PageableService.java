package com.vadmack.authserver.util;

import com.vadmack.authserver.exception.ValidationException;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class PageableService {

    public void validateParams(@Nullable Integer pageNumber, @Nullable Integer pageSize) {
        if (pageNumber != null ^ pageSize != null) {
            throw new ValidationException("Parameters \"pageNumber\" and \"pageSize\" should be set both or both empty");
        }
    }
}
