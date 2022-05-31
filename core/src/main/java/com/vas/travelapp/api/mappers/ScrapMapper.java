package com.vas.travelapp.api.mappers;

import com.google.gson.Gson;
import com.vas.travelapp.api.dtos.ScrapDto;
import com.vas.travelapp.domain.point.Address;
import com.vas.travelapp.domain.point.OpeningHours;
import com.vas.travelapp.domain.point.OperationHours;
import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.point.enums.PointType;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Component
public class ScrapMapper {

    public Point scrapDtoToPoint(ScrapDto.ScrapDtoDetails dto) {
        if (dto == null) {
            return null;
        }

        var point = new Point();
        point.setId((long) (Math.random() * 2 * Long.MAX_VALUE - Long.MAX_VALUE));

        point.setName(dto.getName());

        point.setOperationHours(mapOpHours(dto.getOpening_hours()));

        point.setType(PointType.of(dto.getTypes()).orElse(PointType.NO_TYPE));

        point.setAdditionalInfo(getAdditionalInfo(dto));
        if (dto.getPrice_level() != null) {
            point.setPrice(Price.values()[dto.getPrice_level()]);
        }
        point.setAddress(mapAddressFromScrap(dto));
        return point;
    }

    @NotNull
    private String getAdditionalInfo(ScrapDto.ScrapDtoDetails dto) {
        var json = new JSONObject();
        try {
            if (dto.getInternational_phone_number() != null) {
                json.put("phone", dto.getInternational_phone_number());
            }
            if (dto.getWebsite() != null) {
                json.put("website", dto.getWebsite());
            }
            if (dto.getRating() != null) {
                json.put("rating", dto.getRating());
            }
        } catch (JSONException e) {
            log.error(e);
        }
        return json.toString();
    }

    public Address mapAddressFromScrap(ScrapDto.ScrapDtoDetails dto) {
        if (dto == null) {
            return null;
        }
        var address = new Address();
        var addressComponents = dto.getAddress_components();

        addressComponents.stream()
                .filter(addressComponent -> addressComponent.getTypes().contains("country"))
                .findAny().ifPresent(countryComponent -> address.setCountry(countryComponent.getLong_name()));

        addressComponents.stream()
                .filter(addressComponent -> addressComponent.getTypes().contains("administrative_area_level_2"))
                .findAny().ifPresent(countryComponent -> address.setCity(countryComponent.getLong_name()));

        addressComponents.stream()
                .filter(addressComponent -> addressComponent.getTypes().contains("administrative_area_level_2"))
                .findAny().ifPresent(countryComponent -> address.setCity(countryComponent.getLong_name()));

        var street = addressComponents.stream()
                .filter(addressComponent -> addressComponent.getTypes().contains("route"))
                .findAny().map(ScrapDto.AddressComponent::getLong_name).orElse("");

        var streetNumber = addressComponents.stream()
                .filter(addressComponent -> addressComponent.getTypes().contains("street_number"))
                .findAny().map(ScrapDto.AddressComponent::getLong_name).orElse("");

        address.setStreetAddress(String.join(", ", street, streetNumber));

        addressComponents.stream()
                .filter(addressComponent -> addressComponent.getTypes().contains("postal_code"))
                .findAny().ifPresent(countryComponent -> address.setZipCode(countryComponent.getLong_name()));

        address.setLongitude(dto.getGeometry().getLocation().getLng());
        address.setLatitude(dto.getGeometry().getLocation().getLat());
        address.setId((long) (Math.random() * 2 * Long.MAX_VALUE - Long.MAX_VALUE));

        return address;
    }

    public List<OperationHours> mapOpHours(OpeningHours toMap) {

        ArrayList<OperationHours> list = new ArrayList<>();
        if (toMap == null) {
            return list;
        }
        toMap.getPeriods().forEach(period -> {
            if (period.getOpen() != null && period.getClose() != null)
                list.add(new OperationHours(
                        (long) (Math.random() * 2 * Long.MAX_VALUE - Long.MAX_VALUE),
                        DayOfWeek.of(period.getOpen().getDay() + 1),
                        LocalTime.parse(period.getOpen().getTime(), DateTimeFormatter.ofPattern("HHmm")),
                        LocalTime.parse(period.getClose().getTime(), DateTimeFormatter.ofPattern("HHmm")),
                        false
                ));
        });
        return list;
    }
}
