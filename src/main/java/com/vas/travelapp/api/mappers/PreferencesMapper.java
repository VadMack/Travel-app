package com.vas.travelapp.api.mappers;

import com.vas.travelapp.api.dtos.PreferencesDto;
import com.vas.travelapp.api.dtos.TransportType;
import com.vas.travelapp.domain.route.Preferences;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class PreferencesMapper {

    public Preferences toPreference(PreferencesDto preferencesDto) {
        if (preferencesDto == null) {
            return null;
        }

        Preferences preferences = new Preferences();
        preferences.setMinutes(preferencesDto.getHours() * 60 - 30);
        preferences.setCurrentTime(Timestamp.valueOf(preferencesDto.getDateTime()));
        preferences.setActivity(preferencesDto.getActivity());
        preferences.setPrice(preferencesDto.getPrice());
        if (preferencesDto.getTransport().equals(TransportType.WALK)) {
            preferences.setDistance(1000);
        }
        if (preferencesDto.getTransport().equals(TransportType.CAR)) {
            preferences.setDistance(7000);
        }
        if (preferencesDto.getTransport().equals(TransportType.PUBLIC)) {
            preferences.setDistance(3000);
        }
        preferences.setCoordinates(new Preferences.Coordinates(preferencesDto.getLongitude(), preferencesDto.getLatitude()));
        return preferences;
    }

}
