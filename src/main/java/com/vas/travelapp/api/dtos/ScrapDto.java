package com.vas.travelapp.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ScrapDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String address;
    private String phone;
    private String website;

}
