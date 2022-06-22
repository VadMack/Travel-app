package com.vas.travelapp.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Cities {
    NIZHNY_NOVGOROD("56.325505", "43.978087");

    private final String latitude;
    private final String longitude;
}
