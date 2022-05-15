package com.vas.travelapp.domain.point;

public enum Price {
    FREE(0),
    INEXPENSIVE(1, 500),
    MODERATE(501, 1500),
    EXPENSIVE(1501, 5000),
    PREMIUM(5000);

    private Price(int value){
        this.low = value;
        this.high = value;
    }
    private Price(int low, int high) {
        this.low = low;
        this.high = high;
    }

    private final int low;
    private final int high;

    public static Price getByValue(Integer value){
        if(value >= PREMIUM.low){
            return PREMIUM;
        }

        for (Price p: values()){
            if(value >= p.low && value<= p.high){
                return p;
            }
        }

        return FREE;
    }
}
