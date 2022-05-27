package com.vas.travelapp.api.dtos;

public enum PriceDto {
    FREE(0),
    INEXPENSIVE(1, 500),
    MODERATE(501, 1500),
    EXPENSIVE(1501, 5000),
    PREMIUM(5000);

    private final int low;
    private final int high;

    PriceDto(int value) {
        this.low = value;
        this.high = value;
    }

    PriceDto(int low, int high) {
        this.low = low;
        this.high = high;
    }

    public static PriceDto getByValue(Integer value) {
        if (value >= PREMIUM.low) {
            return PREMIUM;
        }

        for (PriceDto p : values()) {
            if (value >= p.low && value <= p.high) {
                return p;
            }
        }

        return FREE;
    }
}
