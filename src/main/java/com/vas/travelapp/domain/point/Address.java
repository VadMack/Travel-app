package com.vas.travelapp.domain.point;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document("addresses")
@TypeAlias("address")
public class Address {
    @Id
    private Long id;

    private String streetNumber;
    private String streetName;
    private String cityName;
    private String countryName;
    private String postalCode;
    private Double latitude;
    private Double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return Objects.equals(streetNumber, that.streetNumber)
                && Objects.equals(streetName, that.streetName)
                && Objects.equals(cityName, that.cityName)
                && Objects.equals(countryName, that.countryName)
                && Objects.equals(postalCode, that.postalCode)
                && Objects.equals(latitude, that.latitude)
                && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetNumber, streetName, cityName, countryName, postalCode, latitude, longitude);
    }
}
