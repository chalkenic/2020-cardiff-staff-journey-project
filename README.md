# Reflection toolkit (Group 03)

## Code Setup

##### 1. Initial clone 
```bash
# Clone repository
git clone https://git.cardiff.ac.uk/c1936922/group-03-staff-project.git
```

##### 2. Run the following scripts within MySQL Workbench in order:



- schema-sql.sql
- data.sql

##### 3. Build server
```bash
# Navigate into directory folder
cd group-03-staff-project

# Build Gradle server 
./gradlew build
```

Server uses SQL by default, whereas tests are performed using H2.



## Login ##
#### username: david112
password: bigboss
<br/>roles: User

###### use case:
viewing dummy data of finished user
<br/>creation of custom activities 
<br/>Participating on an activity via provided list
<br/>reflecting on a participated activity

#### username: user
password: passw
<br/>roles: User

##### use case:
viewing dummy data of finished user
<br/>creation of custom activities 
<br/>Participating on an activity via provided list
<br/>reflecting on a participated activity

#### username: admin
password: pass
<br/>roles: Admin

##### use case:
creating official activities
<br/>viewing averages of data provided by users
<br/>creating official tags.

