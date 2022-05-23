package com.vas.travelapp.domain.point.enums;

public enum PointType {
    THEATER(120),
    CAFE(75),
    BAR(120),
    RESTAURANT(90),
    MUSEUM(75),
    GALLERY(120),
    STROLL(60),
    EVENT(90);

    private final int minutes;

    PointType(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

}
