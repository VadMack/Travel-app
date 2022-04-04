package com.vas.travelapp.repository.model;

import com.vas.travelapp.common.PointType;
import com.vas.travelapp.common.PriceLevel;
import lombok.*;
import org.bson.json.JsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
@Document("points")
public class PointModel {
    @Id
    private Long id;

    private String name;
    private AddressModel address;
    private List<String> tags;
    private PointType type;
    private List<OperationHoursModel> workSchedule;
    private PriceLevel priceLevel;
    private JsonObject additionalInfo;
}
