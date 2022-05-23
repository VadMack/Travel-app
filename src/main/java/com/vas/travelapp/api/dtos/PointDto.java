package com.vas.travelapp.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PointDto {
    private String name;
    private String address;
    private String phone;
    private String link;

}
