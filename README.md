# Reflection toolkit (Group 03)

<div class="boxBorder">

## Code Setup - complete via Command Prompt/Git Bash

### 1. Initial clone 
```bash
# Clone repository
git clone https://git.cardiff.ac.uk/c1936922/group-03-staff-project.git
```

### 2. Open MySQL & run following scripts in order from repository:

1. schema-sql.sql
2. data-sql.sql

These can be found inside folder from filepath: group-03-staff-project\src\main\resources.

- Server uses SQL by default, please make sure .sql files are used 
- All tests are performed using H2 (H2 scripts inside test folder not required for main application usage).

### 3. Build server
```bash
# Navigate into directory folder
cd group-03-staff-project

Build server using Gradle using one below option:


# 1. via command line: 
.\gradlew build

# 2. via git bash: 
./gradlew build
```
### 4. Launch application
```bash
# Launch application via jar
java -jar "build/libs/group-03-staff-project -0.0.1-SNAPSHOT.jar"

#Alternate option via git bash gradle
./gradlew bootrun

#Alternate option via command line
.\gradlew bootrun
```
### 5. Accessing application

navigate to address localhost:8080. Accounts are provided below for view of dummy data. 
</div>

#### Login 

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

### 6. Mozilla Firefox Web browser test
```bash
# Web browser test excluded from initial build. use below on command line to run.
# SERVER MUST BE RUNNING AT TIME OF TEST.
.\gradlew -Dtest.single=shouldAddTagAsUserAndDeleteTagAsAdmin test

#Alternate option via git bash
./gradlew -Dtest.single=shouldAddTagAsUserAndDeleteTagAsAdmin test

```
### 7. Manual Tests


#### A. Add a custom Tag as user, Delete Tag as admin

##### 1. navigate to localhost:8080

##### 2. Enter user credentials and click "login" button:
    - username:     user
    - password:     passw

##### 3. Click "Add a Thought Cloud" button.

##### 4. Fill in textboxes and click "submit":
    - name:         theTest
    - description:  theTestDescription

##### 5. Click "logout" button in navigation bar.

##### 6. Enter admin credentials and click "login" button:
    - username:     admin
    - password:     pass

##### 7. Click "view all tags" button.

##### 8. Locate theTest Thought Cloud at end of list & click delete.

##### 9. Assert that tag is now removed from list after deletion.
    
#### B. Changer user's email address

##### 1. navigate to localhost:8080

##### 2. Enter user credentials and click "login" button:
    - username:     user
    - password:     passw

##### 3. Click "View my Account" button.

##### 4. Click "CHANGE EMAIL ADDRESS" button.

##### 5. Enter "test@test.co.uk" into textbox & click CONFIRM button.

##### 6. Assert that new email address now appears within window.

#### C. Create a new user & log in.

##### 1. navigate to localhost:8080

##### 2. click "create account" button

##### 3. Enter following credentials into textboxes & click Register:
    - Username:         NewTestUser
    - Email address:    newtest@tester.co.uk
    - Password:         AaronTester1!
    - Password confirm: AaronTester1!

##### 4. Assert that box on main screen reads account creation.

##### 5. Login with new user.

##### 6. Assert login successful & within dashboard window.

