package com.vadmack.authserver.util;

import com.vadmack.authserver.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PageableServiceTest {

    @InjectMocks
    PageableService pageableService;

    @Test
    public void validateParamsSuccess1() {
        Assertions.assertDoesNotThrow(() ->
                pageableService.validateParams(1, 1));
    }

    @Test
    public void validateParamsSuccess2() {
        Assertions.assertDoesNotThrow(() ->
                pageableService.validateParams(null, null));
    }


    @Test
    public void validateParamsException1() {
        Assertions.assertThrows(ValidationException.class, () ->
                pageableService.validateParams(1, null));
    }

    @Test
    public void validateParamsException2() {
        Assertions.assertThrows(ValidationException.class, () ->
                pageableService.validateParams(null, 1));
    }
}
