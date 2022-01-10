# CSE2115 - Project

### What is application about?
The application is called **TA hiring system (TAHS)**. It allows lecturers to hire TAs and students to apply to become a TA. It also handles declaration/approval of hours, gives possibility for TAs to rate a course and for lecturers to rate students' job.

### Developers
| Name             |     Email                             |
|----------        |:-------------:                        |
| Anna Kalandadze  | a.g.kalandadze@student.tudelft.nl     | 
| Kiril Vasilev    | k.v.vasilev-1@student.tudelft.nl      |
| Oisín Hageman    | o.hageman@student.tudelft.nl          |
| Efe Sozen        | e.sozen@student.tudelft.nl            |
| Eames Trinh      | e.v.trinh@student.tudelft.nl          |
| Jefferson Yeh    | c.yeh-4@student.tudelft.nl            |

### Development
The application is written in Java (version 11) and built with Spring Boot and Gradle. The system is implemented in the form of microservices and their interactions are handled through APIs. There are 6 microservices, which were built in independent modules:
- **Authentication**: is made with the help of spring security. Through this microservice, users can login and receive rights to access endpoint based on their role. The roles our implementation supports are Admin, Lecturer, Student.
- **API-gateway**: is made to redirect request to suitable ports.
- **Lecturer**: represents all lecturer's duties, such as: hire TAs, approve/disapprove hours, rate TA and see all information about candidate TA.
- **Student**: represents all student's functionalities: apply for being a TA, remove application from being a TA, declare hours and accept offer to be a TA.
- **Course**: keeps all information about course: applicants, TAs, recommendations. This microservice also gives recommendations to Lecturers on candidate TAs based on 3 strategies: experience, grade and rating.
- **Management**: keeps additional information such as hours and ratings and deals with the "contract" managements of the current TAs. Furthermore, it notifies candidates whether they were chosen as a TA for a course.

For any other information, please refer to our latest version of implementation.

In the `doc` folder you can find a `pdf` file with all our endpoints, as well as our Sprint Retrospectives throughout the development of the project.

### Running
Please be aware that in order to application to function, all microservices should be running at the same time.

`gradle bootRun`

### Testing
```
gradle test
```

To generate a coverage report:
```
gradle jacocoTestCoverageVerification
```


And
```
gradle jacocoTestReport
```
The coverage report is generated in: build/reports/jacoco/test/html, which does not get pushed to the repo. Open index.html in your browser to see the report.

### Static analysis
```
gradle checkStyleMain
gradle checkStyleTest
gradle pmdMain
gradle pmdTest
```

### Notes
- You should have a local .gitignore file to make sure that any OS-specific and IDE-specific files do not get pushed to the repo (e.g. .idea). These files do not belong in the .gitignore on the repo.
- If you change the name of the repo to something other than template, you should also edit the build.gradle file.
- You can add issue and merge request templates in the .gitlab folder on your repo. 