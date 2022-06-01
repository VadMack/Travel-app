package com.vas.travelapp.web;

import com.vas.travelapp.api.dtos.ScrapDto;
import com.vas.travelapp.api.dtos.ScrapListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlacesWebService {

    private final WebClient webClient;

    @Value("${poi-poller.placesKey}")
    private String key;

    @Value("${poi-poller.placesApiUrl}")
    private String PLACES_URL;

    public Mono<ScrapListDto> pollPointsList(String latitude, String longitude, List<String> types) {
        var location = String.join(",", latitude, longitude);
        var type = String.join(",", types);
        return webClient
                .get()
                .uri(String.join("", PLACES_URL,
                        "/nearbysearch/json?",
                        "location=", location,
                        "&radius=", "50000",
                        "&type=", type,
                        "&key=", key
                )).retrieve().bodyToMono(ScrapListDto.class);
    }

    public Mono<ScrapDto> pollPoint(String placeId) {
        return webClient.get()
                .uri(String.join("", PLACES_URL,
                        "/details/json?",
                        "place_id=", placeId,
                        "&key=", key,
                        "&language=", "ru"
                )).retrieve().bodyToMono(ScrapDto.class);
    }
}
