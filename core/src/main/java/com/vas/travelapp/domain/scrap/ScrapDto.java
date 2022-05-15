package com.vas.travelapp.domain.scrap;

import com.vas.travelapp.domain.point.OperationHours;
import com.vas.travelapp.domain.point.PointType;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ScrapDto {
    @NotNull(message = "Name field is mandatory")
    private String name;

    @NotNull(message = "Operation hours is mandatory")
    private List<OperationHours> operationHours;

    @NotNull(message = "Country is mandatory")
    private String country;

    @NotNull(message = "City is mandatory")
    private String city;

    @NotNull(message = "Street is mandatory")
    private String street;

    @NotNull(message = "Street number is mandatory")
    private String streetNumber;

    private String postalCode;
    private Double latitude;
    private Double longitude;

    @NotNull(message = "Type is mandatory")
    private PointType type;

    private String description;
    private String phoneNumber;
    private List<String> tags;
    private Integer rating;
    private Integer suggestedDurationHrs;
    private Integer averageReceiptRub;
}
