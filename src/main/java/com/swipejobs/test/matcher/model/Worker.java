package com.swipejobs.test.matcher.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
    private Integer rating;
    private Boolean isActive;
    private List<String> certificates;
    private List<String> skills;
    private JobSearchAddress jobSearchAddress;
    private String transportation;
    private Boolean hasDriversLicense;
    private List<Availability> availability;
    private String phone;
    private String email;
    private Name name;
    private Integer age;
    private String guid;
    private Integer userId;
}
