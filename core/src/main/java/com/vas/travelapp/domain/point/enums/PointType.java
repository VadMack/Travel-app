package com.vas.travelapp.domain.point.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public enum PointType {
    THEATER(120, "movie_theater"),
    CAFE(75, "cafe"),
    BAR(120, "bar"),
    RESTAURANT(90, "restaurant"),
    MUSEUM(75, "museum"),
    GALLERY(120, "art_gallery"),
    STROLL(60, "park"),
    EVENT(90, "tourist_attraction"),
    NO_TYPE(0, "");

    private final int minutes;
    private final String googleType;

    PointType(int minutes, String googleType) {
        this.minutes = minutes;
        this.googleType = googleType;
    }

    public int getMinutes() {
        return minutes;
    }
    public String getGoogleType() {
        return googleType;
    }

    public static Optional<PointType> of(List<String> googleTypes) {
        return Arrays.stream(values()).filter(v -> googleTypes.contains(v.googleType)).findFirst();
    }
}
