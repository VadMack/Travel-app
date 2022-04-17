package com.vas.travelapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TravelAppApplicationTests {

    @Test
    void contextLoads() {
        int actual = 1;
        int expected = 1;
        assertThat(actual).isEqualTo(expected);
    }

}
