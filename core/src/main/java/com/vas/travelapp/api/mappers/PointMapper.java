package com.vas.travelapp.api.mappers;

import com.vas.travelapp.domain.point.Address;
import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.point.Price;
import com.vas.travelapp.domain.scrap.ScrapDto;
import org.bson.json.JsonObject;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class PointMapper {
    public Point mapScrapToPoint(ScrapDto dto){
        var point = new Point();

        point.setName(dto.getName());
        point.setWorkSchedule(dto.getOperationHours());
        point.setType(dto.getType());

        var json = new JSONObject();
        try {
            if (dto.getDescription() != null){
                json.put("description", dto.getDescription());
            }
            if (dto.getPhoneNumber() != null){
                json.put("phone", dto.getPhoneNumber());
            }
            if (dto.getTags() != null){
                json.put("tags", dto.getTags());
            }
            if (dto.getRating() != null){
                json.put("rating", dto.getRating());
            }
            if (dto.getSuggestedDurationHrs() != null){
                json.put("suggestedDuration", dto.getSuggestedDurationHrs());
            }
        } catch (JSONException e){
            System.out.println(e.getMessage());
        }
        point.setAdditionalInfo(new JsonObject(json.toString()));

        if(dto.getAverageReceiptRub() != null) {
            point.setPrice(Price.getByValue(dto.getAverageReceiptRub()));
        }

        return point;
    }

    public Address mapAddressFromScrap(ScrapDto dto){
        var address = new Address();

        address.setCountryName(dto.getCountry());
        address.setCityName(dto.getCity());
        address.setStreetName(dto.getStreet());
        address.setStreetNumber(dto.getStreetNumber());
        if(dto.getPostalCode() != null) {
            address.setPostalCode(dto.getPostalCode());
        }
        if(dto.getLongitude() != null) {
            address.setLongitude(dto.getLongitude());
        }
        if(dto.getLatitude() != null) {
            address.setLatitude(dto.getLatitude());
        }

        return address;
    }
}
