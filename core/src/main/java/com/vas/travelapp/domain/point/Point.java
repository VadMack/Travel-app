package com.vas.travelapp.domain.point;

import lombok.*;
import lombok.experimental.Accessors;
import org.bson.json.JsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document("points")
@TypeAlias("point")
public class Point {
    @Id
    private UUID id;

    private String name;
    @DBRef
    private Address address;
    private List<String> tags;
    private PointType type;
    @DBRef
    private List<OperationHours> workSchedule;
    private Price price;
    private JsonObject additionalInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(name, that.name)
                && Objects.equals(address, that.address)
                && Objects.equals(tags, that.tags)
                && type == that.type
                && Objects.equals(workSchedule, that.workSchedule)
                && price == that.price
                && Objects.equals(additionalInfo, that.additionalInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, tags, type, workSchedule, price, additionalInfo);
    }
}
