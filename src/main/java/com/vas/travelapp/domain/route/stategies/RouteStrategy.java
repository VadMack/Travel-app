package com.vas.travelapp.domain.route.stategies;

import com.vas.travelapp.domain.point.Point;
import com.vas.travelapp.domain.route.Preferences;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RouteStrategy {
    List<Point> buildRoute(Preferences preferences);
}
