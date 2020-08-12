# Robot Cloning

A program allowing robots to clone themselves through special portal technology. A robot is able to move in a user specified way,
portals are added to the map by users which when entered clone a robots moves and proceed to perform the originals preprogrammed moves.

When there are no more portals and no more moves to make the program ends and the results can be queried.

The map is a square grid of x and y coordinates, in the case of a 4x4 grid the following would be created in the simulation:
0,3	1,3	2,3	3,3
0,2 1,2 2,2	3,2
0,1	1,1	2,1 3,1
0,0	1,0	2,0	3,0  

## Core Technologies
* SpringBoot
* Java 11

## Getting Started

This is a spring boot project with a rest api for starting simulation jobs and fetching job results.

To run the robot API:
* ensure java 11 is installed.
* mvn clean install
* mvn spring-boot:run

To hit the api, check out the Robots postman collection. Two end points are provided:

POST start job with a ScenarioRequest body returns 
```JobResult[Success|Error]```

GET job result with jobId parameter returns 
```ScenarioResults[Success|Error]```

## Todo
* More tests
* Scale out using docker and kafka for hitting more services to handle more complex routing scenarios and agent behaviors
