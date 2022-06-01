package com.vas.travelapp.api.dtos;

import com.vas.travelapp.domain.point.OpeningHours;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
public class ScrapDto {

    private ScrapDtoDetails result;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapDtoDetails{
        @NotBlank(message = "Name field is mandatory")
        private String name;

        @NotNull(message = "Operation hours is mandatory")
        private OpeningHours opening_hours;

        @NotEmpty(message = "Address_components is mandatory")
        private List<AddressComponent> address_components;

        @NotNull(message = "Geometry os mandatory")
        private Geometry geometry;

        @NotNull(message = "Types is mandatory")
        private List<String> types;

        private String international_phone_number;
        private String website;
        private Integer rating;
        private Integer price_level;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressComponent{
        private String long_name;
        private List<String> types;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Geometry{
        private Location location;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Location{
            private Double lat;
            private Double lng;
        }
    }
}
