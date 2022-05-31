package com.vas.travelapp.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Cities {
    NIZHNY_NOVGOROD("56.325505", "43.978087"),
    KAZAN("55.802027", "49.109566"),
    UFA("54.737510", "55.957372"),
    IVANOVO("57.001121", "40.975238");

    private final String latitude;
    private final String longitude;
}
