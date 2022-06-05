package com.vas.travelapp.domain.point;

import com.vas.travelapp.domain.point.enums.PointType;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "point")
@Table(name = "points", schema = "public")
public class Point {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    @Column(columnDefinition = "varchar(1024)")
    private String additionalInfo;
    private PointType type;
    private Price price;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_hours_id")
    private List<OperationHours> operationHours;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point that = (Point) o;
        return Objects.equals(name, that.name)
                && Objects.equals(address, that.address)
                && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, type);
    }
}
