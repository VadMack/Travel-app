package com.vas.travelapp.domain.route;

import com.vas.travelapp.domain.point.Point;

import java.util.List;
import java.util.Set;

public interface RouteService {
    List<Point> getRoute(Preferences preferences, Set<Integer> types);
}
