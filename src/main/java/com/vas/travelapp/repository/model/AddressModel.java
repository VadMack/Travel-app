package com.vas.travelapp.repository.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Document("addresses")
public class AddressModel {
    @Id
    private Long id;

    private String streetNumber;
    private String StreetName;
    private String cityName;
    private String countryName;
    private String postalCode;
    private Double latitude;
    private Double longitude;
}
