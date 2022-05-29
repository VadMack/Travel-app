package com.vadmack.authserver.util;

import com.vadmack.authserver.exception.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SortServiceTest {

    @InjectMocks
    SortService sortService;

    @Test
    public void createSortSuccess() {
        String[] params = {"name:0", "weight:1"};
        List<Sort.Order> orderList = new ArrayList<>();

        orderList.add(new Sort.Order(Sort.Direction.ASC, "name"));
        orderList.add(new Sort.Order(Sort.Direction.DESC, "weight"));
        Sort expected = Sort.by(orderList);

        Sort received = sortService.createSort(params);
        Assertions.assertEquals(expected, received);
    }

    @Test
    public void createSortExceptionWrongValue1() {
        String[] params = {"name:600", "weight:1"};
        Assertions.assertThrows(ValidationException.class, () -> sortService.createSort(params));
    }

    @Test
    public void createSortExceptionWrongValue2() {
        String[] params = {"name:1", "weight:600"};
        Assertions.assertThrows(ValidationException.class, () -> sortService.createSort(params));
    }

    @Test
    public void createSortExceptionWrongFormat1() {
        String[] params = {"1:name", "1:weight"};
        Assertions.assertThrows(ValidationException.class, () -> sortService.createSort(params));
    }

    @Test
    public void createSortExceptionWrongFormat2() {
        String[] params = {"name-1", "weight-600"};
        Assertions.assertThrows(ValidationException.class, () -> sortService.createSort(params));
    }

    @Test
    public void createSortExceptionWrongFormat3() {
        String[] params = {"name_1", "weight_600"};
        Assertions.assertThrows(ValidationException.class, () -> sortService.createSort(params));
    }
}
