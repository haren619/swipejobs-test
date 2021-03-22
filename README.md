## Prerequisites

Java 8

## How to Run

Build and run the application using maven wrapper.  
./mvnw clean install  
./mvnw spring-boot:run  

Access below url in the browser.
http://localhost:8080/matcher/{workerId}

{workerId} -> worker id needs to be matched with jobs

** Refer ../target/site/jacoco/index.html for code coverage.

## Design

Matcher application will take a workerId and return three appropriate jobs with the highest score using the data retrieved from SwipeJob's workers and jobs apis.

In this solution application uses a basic Rule Engine design pattern. MatchingEngine calculates the matching score for each job against the given worker. MatchingService will then sort the jobs by their score and return 3 jobs with the highest score.

### Matching Rules

* Qualification Matcher   
  Worker should have all the certificates required by the job.
* Distance Matcher  
  Distance between the job and worker location should be less than the maximum distance preferred by the worker.
  Job will be given a score based on the relative distance.
* Driving Licence Matcher  
  Worker should have driver licence if the job requests for it.

## Future Improvements

* Add new matching rule to map worker's availability with job start date.
* Improve distance matcher mechanism to provide more granular ratings.  
* Rate jobs based on number of workers required so that worker will have a higher change.
* Rate jobs by billing rate.





