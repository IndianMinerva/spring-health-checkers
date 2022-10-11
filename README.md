### How to run locally

**Prerequisites** : Docker, Java11, maven
** Execute the below commands:
- docker run -p 27017:27017 bitnami/mongodb:latest
- mvn spring-boot:run

##### What to observe
Open any mongoDB editor (E.g. robo3T) and look for collections **appHealth** and **aggregatedAppHealth**.


##### How does it work:

This project contains two scheduler jobs. One scheduler job runs every 10 seconds and checks if the magnificient app is running and notes the status in the **appHealth** collection. The status of the magnificient could be in one of the three states **OK**, **ERROR**, **NOT_REACHABLE**.

The other scheduler gets invoked every 2 minutes and aggregates the unprocessed status from the afore mentioned batch job. If the process has not been reachable at all for the duration of the 2 minutes, the aggregator marks the aggregated app-status as **NOT_REACHABLE**. In all the other cases (**OR** or **ERROR**), the application is reachable and so it marks the aggregated status as **REACHABLE**.



#### What else could be done?
- Have various ways to notify the admin about the unreachability of the magnificient-app E.g: Queue, REST call, Mail, SMS  
- Mocking the interactions with the python application
- using an embedded MongoDB to test the insertion of the records using flapdoodle and writing a few @DataMongoTest-s
