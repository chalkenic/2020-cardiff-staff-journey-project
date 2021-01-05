# Reflection toolkit (Group 03)

<div class="boxBorder">

## Code Setup - complete via Command Prompt

##### 1. Initial clone 
```bash
# Clone repository
git clone https://git.cardiff.ac.uk/c1936922/group-03-staff-project.git
```

##### 2. Open MySQL & run following scripts from repository:

- schema-sql.sql
- data-sql.sql

These can be found inside folder from filepath: group-03-staff-project\src\main\resources.

Server uses SQL by default, whereas tests are performed using H2 (script not required to run).

##### 3. Build server
```bash
# Navigate into directory folder
cd group-03-staff-project

# Build Gradle server 
./gradlew build
```
##### 4. Launch application
```bash
# Launch application via jar
java -jar "build/libs/group-03-staff-project -0.0.1-SNAPSHOT.jar"

#Alternate option via gradle
./gradlew bootrun

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
