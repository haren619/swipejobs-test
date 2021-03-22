package com.swipejobs.test.matcher.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobSearchAddress {
    private String unit;
    private Integer maxJobDistance;
    private Double longitude;
    private Double latitude;
}
