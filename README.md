#Hotel Management

The system has been implemented using Spring Boot for ease of Testing and implementation. There is no backend configured, the implementation and testing have been handled as in-memory operation. The solution can be extended to configure a database from the Service Layer 
The Hotel default setup is loaded on Application Boot as part of an initializing Bean. This can be extended to an api or other source as well.

### The port is configured to run at 7000

Run `./gradle bootRun` to start the application

##List of APIS
The APIs provide a JSON response, with Light/AC flag as true for ON and false for OFF.
The expected format of the output can be seen in the log file in the path configured in the application.properties file. It is defaulted to ```F:/application.log```

`curl <<ip/localhost>>:7000` can be used to invoke the apis 

```
/hotel
/hotel/movement/{mainCorridorNumber}/{subCorridorNumer}
```

where mainCorridorNumber denotes the main corridor index as 1 or 2
subCorridor indicates the sub corridor for the respective Main Corridor

Sample APIs and Responses:
```
curl localhost:7000/hotel
```
```
Response:
{"hotelFloors":{"1":{"floorName":"Floor 1","mainCorridors":{"1":{"light":true,"ac":true}},"subCorridors":{"1":{"light":false,"ac":true},"2":{"light":false,"ac":true}}},"2":{"floorNam
e":"Floor 2","mainCorridors":{"1":{"light":true,"ac":true}},"subCorridors":{"1":{"light":false,"ac":true},"2":{"light":false,"ac":true}}}}}
```
This api prints the current Hotel state in the Response JSON as well as the log file

```
curl localhost:7000/hotel/movement/1/2
```
```
Response:
{"hotelFloors":{"1":{"floorName":"Floor 1","mainCorridors":{"1":{"light":true,"ac":true}},"subCorridors":{"1":{"light":false,"ac":true},"2":{"light":true,"ac":false}}},"2":{"floorNam
e":"Floor 2","mainCorridors":{"1":{"light":true,"ac":true}},"subCorridors":{"1":{"light":false,"ac":true},"2":{"light":false,"ac":true}}}}}
```
The response shows light is turned On and Ac is turned Off for the subcorridor#2 of the MainCorridor#1


This api changes the state of the respective sub corridor during movement and prints the current Hotel state in the Response JSON as well as the log file
After 1 minute as it is configured, if we consume the hotel API, the response would look like below:
```
curl localhost:7000/hotel
```
```
Response:
{"hotelFloors":{"1":{"floorName":"Floor 1","mainCorridors":{"1":{"light":true,"ac":true}},"subCorridors":{"1":{"light":false,"ac":true},"2":{"light":false,"ac":true}}},"2":{"floorNam
e":"Floor 2","mainCorridors":{"1":{"light":true,"ac":true}},"subCorridors":{"1":{"light":false,"ac":true},"2":{"light":false,"ac":true}}}}}
```

We could test multiple sessions by hitting the same API within 1 minute and it will be a rolling time period for resetting the lights and AC.
For example, if we notice movement in the same corridor within 1 minute, the light would be turned off 1 minute from the second hit and not from the first hit.
 
### Log File for Reviewing the Hotel status
A separate log file will be created for the application in the directory configured in the application.properties.
The log file will represent the format as provided in the requirements.
It will be printed when the api for printing hotel is invoked as well as when there is a floor movement.

### Junit
have been written for utility classes. It can be executed by the following command:

```
./gradlew test
```

The program has been tested by causing a Floor movement for Floor 1 followed by a floor movement in Floor 2 within 1 minute successfully. 