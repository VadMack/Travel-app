package com.vas.travelapp.domain.point;

import com.vas.travelapp.api.dtos.RequestDto;
import com.vas.travelapp.api.dtos.ScrapListDto;
import com.vas.travelapp.api.dtos.TypeActivity;
import com.vas.travelapp.api.mappers.ScrapMapper;
import com.vas.travelapp.common.Cities;
import com.vas.travelapp.domain.point.enums.PointType;
import com.vas.travelapp.domain.point.enums.Price;
import com.vas.travelapp.domain.route.Preferences;
import com.vas.travelapp.domain.route.stategies.*;
import com.vas.travelapp.web.PlacesWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PointService {

    private final ApplicationContext context;

    private final PointRepository pointRepository;

    private final PlacesWebService placesWebService;

    private final ScrapMapper scrapMapper;

    public Point save(Point point) {
        return pointRepository.save(point);
    }

    public List<Point> generateRoute(Preferences preferences) {
        TypeActivity activity = preferences.getActivity();
        RouteStrategy routeStrategy = null;
        if (activity == TypeActivity.FUN) {
            routeStrategy = context.getBean(RouteFun.class);
        }
        if (activity == TypeActivity.FAMILY) {
            routeStrategy = context.getBean(RouteFamily.class);
        }
        if (activity == TypeActivity.ROMANCE) {
            routeStrategy = context.getBean(RouteRomance.class);
        }
        if (activity == TypeActivity.YAMMY) {
            routeStrategy = context.getBean(RouteYammy.class);
        }
        if (activity == TypeActivity.CULTURE_SCIENCE) {
            routeStrategy = context.getBean(RouteCultureScience.class);
        }
        if (routeStrategy == null) {
            routeStrategy = context.getBean(RouteFun.class);
        }
        return generateRoute(routeStrategy, preferences);
    }

    private List<Point> generateRoute(RouteStrategy routeStrategy, Preferences preferences) {
        return routeStrategy.buildRoute(preferences);
    }

    public Mono<Void> pollPoints() {
        var city = Cities.values()[new Random().nextInt(Cities.values().length)];
        var types = Stream.of(PointType.values()).map(PointType::getGoogleType).toList();

        return placesWebService.pollPointsList(
                        city.getLatitude(),
                        city.getLongitude(),
                        types
                )
                .map(ScrapListDto::getResults)
                .flatMapMany(Flux::fromIterable)
                .flatMap(reference -> placesWebService.pollPoint(reference.getPlace_id()))
                .map(scrap -> {
                    var point = scrapMapper.scrapDtoToPoint(scrap.getResult());
                    if (point != null) {
                        return save(point);
                    }
                    return null;
                })
                .then();

    }

    @PostConstruct
    void save() {
        pointRepository.save(new Point());
    }

    public List<Point> findAll(RequestDto requestDto) {
        Set<Integer> prices = requestDto.getPrices().stream().map(Price::ordinal).collect(Collectors.toSet());
        Set<Integer> types = requestDto.getTypes().stream().map(PointType::ordinal).collect(Collectors.toSet());
        if (requestDto.getOpen()) {
            int dayOfWeek = ZonedDateTime.now().getDayOfWeek().getValue();
            Timestamp time = Timestamp.valueOf(ZonedDateTime.now().toLocalDateTime());
            return pointRepository.findPointsByRequestOpen(time, prices, dayOfWeek, types, requestDto.getLongitude(), requestDto.getLatitude(), 10000);
        }
        return pointRepository.findPointsByRequest(prices, types, requestDto.getLongitude(), requestDto.getLatitude(), 10000);
    }

}
