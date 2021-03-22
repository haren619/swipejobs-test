package com.swipejobs.test.matcher.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    private Boolean driverLicenseRequired;
    private List<String> requiredCertificates;
    private Location location;
    private String billRate;
    private Integer workersRequired;
    private Date startDate;
    private String about;
    private String jobTitle;
    private String company;
    private String guid;
    private Integer jobId;
}
