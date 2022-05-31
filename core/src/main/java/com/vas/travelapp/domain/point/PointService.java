package com.vas.travelapp.domain.point;

import com.vas.travelapp.api.dtos.ScrapDto;
import com.vas.travelapp.api.dtos.ScrapListDto;
import com.vas.travelapp.api.dtos.TypeActivity;
import com.vas.travelapp.api.mappers.ScrapMapper;
import com.vas.travelapp.common.Cities;
import com.vas.travelapp.domain.point.enums.PointType;
import com.vas.travelapp.domain.route.Preferences;
import com.vas.travelapp.domain.route.stategies.*;
import com.vas.travelapp.web.PlacesWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
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

    /*public void generateData() {
        ArrayList<Point> points = new ArrayList<>(150);
        for (int i = 0; i < 150; i++) {
            Point point = new Point();
            point.setName("Point " + i);
            point.setType(PointType.values()[i % PointType.values().length]);
            point.setPrice(Price.values()[i % Price.values().length]);
            Address address = new Address();
            address.setStreetAddress("Maiakovski st. " + i);
            address.setCity("Tiraspol");
            address.setCountry("MD");
            address.setZipCode(3300);
            address.setLatitude(46.852624 + (double) i / 100);
            address.setLongitude(29.630269 + (double) i / 100);
            point.setAddress(address);

            List<OperationHours> operationHours = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                operationHours.add(new OperationHours().setDayOfWeek(DayOfWeek.values()[i % DayOfWeek.values().length])
                        .setOpeningTime(LocalTime.of(8, 0, 0))
                        .setCloseTime(LocalTime.of(20, 0, 0))
                        .setPoint(point));
            }
            operationHours.add(new OperationHours().setDayOfWeek(DayOfWeek.SUNDAY).setClosed(true).setPoint(point));
            point.setOperationHours(operationHours);
            points.add(point);
        }
        pointRepository.saveAll(points);
    }*/

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
}
