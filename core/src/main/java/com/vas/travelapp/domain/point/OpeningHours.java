package com.vas.travelapp.domain.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHours{
    private List<OperationHours> periods;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperationHours{
        private OpHour close;
        private OpHour open;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class OpHour{
            private Integer day;
            private String time;
        }
    }
}
