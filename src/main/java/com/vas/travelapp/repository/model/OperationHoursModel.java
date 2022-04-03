package com.vas.travelapp.repository.model;

import com.vas.travelapp.domain.entity.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
@Document("points")
public class PointModel {
    @Id
    private Long id;

    private AddressModel address;

    private List<String> tags;

    private List<OperationHoursModel> authorities;
    private boolean enabled = true;
}
