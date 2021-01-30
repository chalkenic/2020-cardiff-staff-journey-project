# Reflection toolkit (Group 03)

<div class="boxBorder">

## Code Setup - complete via Command Prompt/Git Bash

##### 1. Initial clone 
```bash
# Clone repository
git clone https://git.cardiff.ac.uk/c1936922/group-03-staff-project.git
```

##### 2. Open MySQL & run following scripts in order from repository:

1. schema-sql.sql
2. data-sql.sql

These can be found inside folder from filepath: group-03-staff-project\src\main\resources.

- Server uses SQL by default, please make sure .sql files are used 
- All tests are performed using H2 (H2 scripts inside test folder not required for main application usage).

##### 3. Build server
```bash
# Navigate into directory folder
cd group-03-staff-project

# Build Gradle server 
.\gradlew build
```
##### 4. Launch application
```bash
# Launch application via jar
java -jar "build/libs/group-03-staff-project -0.0.1-SNAPSHOT.jar"

#Alternate option via gradle
.\gradlew bootrun

```
##### 5. Accessing application

navigate to address localhost:8080. Accounts are provided below for view of dummy data. 
</div>

## Login ##

| Username | Password | Role | Use Case |
| ---------| -------- | ---- | -------- |
| david112 | bigboss  | USER | Custom activity & Tag creation |
|  |  |  | viewing dummy data of user progressing in app |
|  |  |  | Participating in activities via list |
|  |  |  | Reflecting on participated activity |
|  |  |  | Viewing of visual progress graphs |
| user | passw | USER | custom activity & Tag creation |
|  |  |  | viewing dummy data of user progressing in app |
|  |  |  | participating in activities via list |
|  |  |  | Reflecting on participated activity |
|  |  |  | Viewing of visual progress graphs |
| admin | pass  | ADMIN | Official activity creation |
|  |  |  | viewing averages of tag completion provided by users |
|  |  |  | viewing averages of activity participation rates via user data |
|  |  |  | official tag creation |
