package com.vas.travelapp.scheduler;

import com.vas.travelapp.domain.point.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        value = "${poi-poller.enabled}",
        havingValue = "true",
        matchIfMissing = true
)
public class PollingScheduler {

    private final PointService service;

    @Scheduled(cron = "${poi-poller.cron}")
    private void poll(){
        log.info("Starting poller");
        service.pollPoints()
                .doOnSuccess(res -> log.info("New points polled"))
                .doOnError(err -> log.error("Error occurred while polling: {}", err.getMessage()))
                .subscribe();
    }
}
