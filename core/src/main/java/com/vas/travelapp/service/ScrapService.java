package com.vas.travelapp.service;

import com.vas.travelapp.api.mappers.PointMapper;
import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.scrap.ScrapDto;
import com.vas.travelapp.repository.AddressRepository;
import com.vas.travelapp.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final PointMapper mapper;
    private final AddressRepository addrRepo;
    private final PointRepository pointRepo;

    public Point saveScrap(ScrapDto dto){
        var address = mapper.mapAddressFromScrap(dto);
        var point = mapper.mapScrapToPoint(dto);

        if (addrRepo.countBy(address.getCountryName(), address.getCityName(), address.getStreetName(), address.getStreetNumber()) != 0) {
            point.setAddress(addrRepo.getBy(address.getCountryName(), address.getCityName(), address.getStreetName(), address.getStreetNumber()));
        } else {
            address.setId(UUID.randomUUID());
            addrRepo.save(address);
            point.setAddress(address);
        }

        if(pointRepo.countBy(point.getName(), point.getAddress()) != 0){
            System.out.println("Point already exists");
            return pointRepo.getBy(point.getName(), point.getAddress());
        }

        point.setId(UUID.randomUUID());
        pointRepo.save(point);
        return point;
    }
}
