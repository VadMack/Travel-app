package com.vas.travelapp.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ScrapListDto {
    private List<ScrapReference> results;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScrapReference{
        private String place_id;
    }
}
