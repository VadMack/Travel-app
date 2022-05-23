package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.PointDto;
import com.vas.travelapp.domain.point.Address;
import com.vas.travelapp.domain.point.Point;
import org.springframework.stereotype.Component;


@Component
public class PointMapper {
    public Point toPoint(PointDto pointDto) {
        if (pointDto == null) {
            return null;
        }

        Point point = new Point();
        point.setName(pointDto.getName());
        point.setAddress(toAddress(pointDto.getAddress()));

        return point;
    }

    private Address toAddress(String address) {
        if (address == null) {
            return null;
        }
        Address addressObj = new Address();
        addressObj.setOptionalAddress(address);
        return addressObj;
    }


}
