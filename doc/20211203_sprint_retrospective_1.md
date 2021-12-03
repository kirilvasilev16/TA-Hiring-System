# Sprint 1
## Plans and distribution

During this sprint, we plan to work on setting up the microservices (modules with separate databases and gradle files), setting up the H2 database as well as providing basic implementation to them (mostly must have issues). The workload is distributed on gitlab.
Lecturer - Anna
Management - Kiril
Authentication - Efe
Student - Oisin
Course - Jeff, Eames
Course seems the most time-consuming, so 2 people are working on it.
Testing is included and always provided to the code we write.

During the sprint 2 meetings are held: on Monday and on Wednesday. The purpose of those is to discuss progress and ask questions.

## Progress during sprint
### 1. Lecturer microservice
### Entities
#### 1. Lecturer
* netId
* name
* password
* email
* listOfOwnCourses
#### 2. Course
* id
* candidateTas

#### 3. Student
* id

### Endpoints
all start with /lecturer
1. ***POST***

**Add lecturer**
* /addLecturer: get a body {Lecturer} and saves to database
2. ***GET***

**Get all lecturers**
* /getAll - finds all lecturers

**Get specific lecturer**
* /get/?netId={netId} - find specififc lecturer by netId

**Get all courses of a lecturer**
* /courses/?netId={netId} - find all courses of a lecturer 

**Get specific course of a lecturer**
* /courses/course/?netId={netId} - find specific course from lecturer

**Get all candidate TAs for a course**
* /courses/course/candidateTas/?netId={netId} - find all candidate TAs for a course
3. ***PATCH***

**Choose a TA for a course**
* /courses/course/?netId={netId}/?studentId={studentNetId} - choose a TA for a specific course: sends a post request to "http://localhost:8080/course/ + neededCourse.getId() + /addTA/ + studentNetId"

**Add a new course to a lecturer**
* /courses/addCourse/?netId={netId} - add new course to a lecturer: I expect the request from course


### Exceptions
1. Lecturer not found 
2. Course not found

### Issues
1. Lecturer can browse through own courses: endpoint get Courses
2. Lecturer can examine students' data: endpoint get CandidateTas
3. Select TAs for course: endpoint choose TA

### Problems
The main problem was to set up microservice and create dependencies with other microservices. 

### 2. Student microservice
### Issues covered:
#14, #16, #18
### Entities
#### Student
* netId
* name
* passedCourses
* candidateCourses
* taCourses

### Endpoints
all start with /student
#### GET
**Get specific student**
* /get?id={id} - gets the Student with the provided netId

**Get map of passed courses with grades**
* /getpassedcourses?id={id} - gets a map of passed course ids with the corresponding grades, that belong to the student with provided netId

**Get set of candidate courses**
* /getcandidatecourses?id={id} - gets the set of course ids that a student want to apply for, that belong to the student with provided netId

**Get set of ta courses**
* /getcandidatecourses?id={id} - gets the set of course ids that a student is a TA for, that belong to the student with provided netId

#### POST
**Add student to database**
* /addstudent - adds the student in method body (with only netid and name) to the database

#### PUT
**Apply as TA for course**
* /apply?netId={netId}&courseId={courseId} - applies a student with provided netId apply for a course with provided courseId, but only if the student has passed the course already

**Accept as TA for course**
* /accept?netId={netId}&courseId={courseId} - accepts a student with provided netId as a TA for the course with provided courseId, but only if the student has applied for it already
### Exceptions
* Student Not Found

### Problems
The most difficult part for the Student microservice was setting up the module structure, and finding an architecture distribution that removed redundant information storage, while keeping calls between microservices to a minimum.


### 3. Management microservice
### Issues covered:
#4, #5, #6, #11, #33
### Problem covered:
Set up Management microservice so that it can easily interact with the other microservices.
### Time spent on issues:
14 hours
### Entities
#### Management
* id
* courseId
* studentId
* amountOfHours
* approvedHours
* declaredHours
* rating

### Endpoints
All starting with /management:

1. ***POST***

**Create management**
* /create?courseId={courseId}&studentId={studentId}&amountOfHours={amountOfHours} - returns the newly created Management object

2. ***GET***

**Get all Management objects**
* /findAll

**Get specific Management object**
* /get?courseId={courseId}&studentId={studentId}

**Send a contract**
* /sendContract?studentId={studentId}&email={email}

3. ***PUT***

**Set rating for a Management object**
* /rate?courseId={courseId}&studentId={studentId}&rating={rating}

**Update declared hours for a Management object**
* /declareHours?courseId={courseId}&studentId={studentId}&hours={hours}

**Update approved hours for a Management object**
* /approveHours?courseId={courseId}&studentId={studentId}&hours={hours}


### Exceptions
1. **InvalidIdException** - thrown for invalid Management object provided
2. **InvalidContractHoursException** - thrown for invalid amount of hours declared
3. **InvalidApprovedHoursException** - thrown for invalid amount of hours approved
4. **InvalidRatingException - thrown** for rating outside of [1, 10] provided
### 4. Authentication microservice
### Entities
#### 1. Authentication
* id
* netId
* name
* password

#### 2. Role
* id
* roleName


### Endpoints
1. ***POST***
**login and authenticate**
* /login - logs user in with netId and password, sends out JWT token to authorize users for other requests.

****
2. ***GET***

**Get all users**
* /findAll - returns all users as Authentication objects. Added with the purpose of testing whether roles work.


### Exceptions
1. UsernameNotFoundException - when netId of user is not found.
2. ServletException - when servlet encounters difficulty in handling requests
3. AlgorithmMismatchError - when the algorithm that hashes the JWT token does not match the defined token.
4. SignatureVerificationException – if the signature is invalid.
5. TokenExpiredException – if the token has expired.
6. InvalidClaimException – if a claim contained a different value than the expected one.

### User story
As a user, I want to be able to authenticate myself with a NetID and a password.

Task 1: Authenticate Users by allowing them to log in.
Task 2: Create a JWT token for each logged in user
Task 3: Have different authorization levels for endpoints and make sure unauthorized users cannot access content, even if they are logged in.


### Main Problems: 
1. We need to think about the architecture that will be used to authorize users for which content will be accessible for them. Do we send every request to Authentication microservice to first authorize users or do we authorize them within each microservice?

2. The secret for the JWT token is not secured right now, it just exists in the code as a string, that should be fixed, but I do not know what to do about it.

### Adjustments: 
Do more research beforehand about testing and spring security in general and then move on to the implementation. I have planned and researched and then moved to implementation, but more research will also be useful.


### 5. Course microservice
### Entities
#### 1. Course
* courseID (course code-year ie. CSE130-2020)
* name
* courseSize
* requiredTAs
* startingDate
* lecturerSet
* candidateTas
* hiredTas


### Endpoints
all start with /course
1. ***POST***

**Add Course**
* /makeCourse : create Course and saves to database

**Add Candidate TA**
* /addCandidateTa?courseId={courseId}&studentId={studentId} - Add student as candidate TA by studentID

**Add Lecturer**
* /addLecturer?courseId={courseId}&lecturerId={lecturerId} - Add lecturer as a course lecturer

**Hire TA**
* /hireTa?courseId={courseId}&studentId={studentId}&hours={hours} - Hire candidate TA as TA

2. ***GET***

**Get specific course**
* /get?courseId={courseId} - finds specific course by courseID 

**Get specific course student size**
* /size?courseId={courseId} - get specific course student size by courseID

**Get specific course lecturers**
* /lecturers?courseId={courseId} - get specific course lecturers set by courseID

**Get specific course required amount of TAs**
* /requiredTas?courseId={courseId} - get specific course required TAs amount by courseID

**Get specific course candidate TA recommendation**
* /taRecommendation?courseId={courseId} - get specific course TA recommendation by courseID

**Get Average Worked Hours**
* /averageWorkedHours?courseId={courseId} - get specific course TAs average worked hours by courseID

**Get Candidate TAs**
* /candidates?courseId={courseId} - get all candidate TAs from specific course

**Get Hired TAs**
* /tas?courseId={courseId} - get all hired TAs from specific course


3. ***PATCH***

**Patch specific course size**
* /updateSize?courseId={courseId}&size={size} - update student size of specific course by courseID


4. ***DELETE***

**Remove Candidate TA**
* /removeAsCandidate?courseId={courseId}&studentId={studentId} - remove student as candidate TA by studentID for specific course by courseID



### Exceptions
1. Course not found
2. InvalidCandidateException
3. InvalidHiringException

### Issues covered:
#1, #3, #7, #16, #29, #30, #31, #32

### Notes / Problems
Most of our issues stemmed from inexperience with using and setting up Spring and other frameworks. As well, due to courses complexity and wanting to avoid monolithic class to maintain system extendability, took several architecture design iterations before arriving at current choice to delegate some functionality to service classes.

HireTA functionality still needs to be linked to the Management microservice.

Upon the corresponding implementations in other microservices, List/Array queries will be made as opposed to the current single-object queries currently employed.