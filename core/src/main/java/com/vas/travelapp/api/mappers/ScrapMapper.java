package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.PriceDto;
import com.vas.travelapp.api.dtos.ScrapDto;
import com.vas.travelapp.domain.point.Address;
import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ScrapMapper {

    public Point scrapDtoToPoint(ScrapDto dto) {
        if (dto == null) {
            return null;
        }

        var point = new Point();

        point.setName(dto.getName());
        point.setOperationHours(dto.getOperationHours());
        point.setType(dto.getType());
        point.setAdditionalInfo(getAdditionalInfo(dto));
        point.setPrice(getPrice(dto.getAverageReceiptRub()));

        var address = mapAddressFromScrap(dto);
        point.setAddress(address);

        return point;
    }

    private Price getPrice(Integer averageReceiptRub) {
        return Price.valueOf(PriceDto.getByValue(averageReceiptRub).name());
    }

    @NotNull
    private String getAdditionalInfo(ScrapDto dto) {
        var json = new JSONObject();
        try {
            if (dto.getDescription() != null) {
                json.put("description", dto.getDescription());
            }
            if (dto.getPhoneNumber() != null) {
                json.put("phone", dto.getPhoneNumber());
            }
            if (dto.getTags() != null) {
                json.put("tags", dto.getTags());
            }
            if (dto.getRating() != null) {
                json.put("rating", dto.getRating());
            }
            if (dto.getSuggestedDurationHrs() != null) {
                json.put("suggestedDuration", dto.getSuggestedDurationHrs());
            }
        } catch (JSONException e) {
            log.error(e);
        }
        return json.toString();
    }

    public Address mapAddressFromScrap(ScrapDto dto) {
        if (dto == null) {
            return null;
        }
        var address = new Address();

        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setStreetAddress(dto.getStreet() + " " + dto.getStreetNumber());
        address.setZipCode(Integer.parseInt(dto.getPostalCode()));

        address.setLongitude(dto.getLongitude());
        address.setLatitude(dto.getLatitude());

        return address;
    }
}
