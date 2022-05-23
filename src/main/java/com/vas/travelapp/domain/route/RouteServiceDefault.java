package com.vas.travelapp.domain.route;

import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.point.PointRepository;
import com.vas.travelapp.domain.point.enums.Price;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteServiceDefault implements RouteService {

    private final PointRepository pointRepository;

    @Override
    public List<Point> getRoute(Preferences preferences, Set<Integer> types) {
        List<Point> points = new ArrayList<>();
        int minutes = preferences.getMinutes();
        Timestamp time = preferences.getCurrentTime();
        Price price = preferences.getPrice();
        int dayOfWeek = preferences.getCurrentTime().toLocalDateTime().getDayOfWeek().getValue();
        int distance = preferences.getDistance();
        double longitude = preferences.getCoordinates().getLongitude();
        double latitude = preferences.getCoordinates().getLatitude();
        Set<Long> blacklist = new HashSet<>();
        blacklist.add(0L);
        if (pointRepository.count(time, price.ordinal(), dayOfWeek, types, longitude, latitude, distance) == 0) {
            return Collections.emptyList();
        }
        while (minutes >= 0) {
            Point point = pointRepository.find(time, price.ordinal(), dayOfWeek, types, longitude, latitude, distance, blacklist);
            if (point == null) {
                break;
            }
            blacklist.add(point.getId());
            time = increaseTime(time, getTime(point));
            minutes -= getTime(point);
            points.add(point);
        }
        return points;
    }

    private int getTime(Point point) {
        return point.getType().getMinutes() + 30;
    }

    @NotNull
    private Timestamp increaseTime(Timestamp time, int point) {
        return Timestamp.valueOf(time.toLocalDateTime().plusMinutes(point));
    }

}
