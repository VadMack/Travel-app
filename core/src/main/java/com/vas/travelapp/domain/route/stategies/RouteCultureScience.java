package com.vas.travelapp.domain.route.stategies;

import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.point.enums.PointType;
import com.vas.travelapp.domain.route.Preferences;
import com.vas.travelapp.domain.route.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RouteCultureScience implements RouteStrategy {

    private static final Set<Integer> POINT_TYPES =
            Set.of(PointType.NO_TYPE.ordinal(), PointType.CAFE.ordinal(), PointType.EVENT.ordinal(), PointType.MUSEUM.ordinal(), PointType.THEATER.ordinal());
    private final RouteService routeService;

    @Override
    public List<Point> buildRoute(Preferences preferences) {
        return routeService.getRoute(preferences, POINT_TYPES);
    }
}
