package com.vas.travelapp.domain.point;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "addresses")
@Table(name = "addresses", schema = "travelapp")
public class Address {
    @Id
    private Long id;

    private String streetAddress;
    @Column(length = 192)
    private String city;
    @Column(length = 10)
    private String zipCode;
    @Column(length = 96)
    private String country;

    private Double latitude;
    private Double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address that = (Address) o;
        return Objects.equals(streetAddress, that.streetAddress)
                && Objects.equals(city, that.city)
                && Objects.equals(country, that.country)
                && Objects.equals(zipCode, that.zipCode)
                && Objects.equals(latitude, that.latitude)
                && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, city, country, zipCode, latitude, longitude);
    }
}
