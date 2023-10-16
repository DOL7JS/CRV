# CRV
CRV system is system for registring and unregistring vehicles. In this system user can do:
- User management - create, edit user
- Car management - create, edit car, add owner to car, sign in, sign out car
- Branch office management - create, edit office, add user to office
- Owner management - create, edit owner

This system is developed with:
- [Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MariaDB](https://mariadb.org/download/)
- [Vaadin](https://vaadin.com/)

## Get started
1. Download repository
2. Start Docker desktop and docker-compose.yml
   - Thanks to volumes it will insert sample data, if you don't want to insert sample data, delete row in docker-compose.yml
     ```
     ./init_data/init_db.sql:/docker-entrypoint-initdb.d/setup.sql
3. Set value of 'appType' in application.properties. That will set that the system will be used with Vaadin and Vaadin authentication or as REST API with JWT authentication. Value have to be VAADIN or JWT.
4. Set environment variables db_url, db_username, db_password and jwt_secret
5.  Start file  NnproBackendApplication.java
## Sample data
In this reposity is folder 'init_data' which contains sample data for this system. In this data are users, which can be used to log in this system. Every user has same password 12345678.
Sample users:
| username 	| password 	| role 	|
|---	|---	|---	|
| admin 	| 12345678 	| ROLE_Admin 	|
| krajKolin 	| 12345678 	| ROLE_Kraj 	|
| okresKO 	| 12345678 	| ROLE_Okres 	|

## Users rights
### ROLE_Admin
  - OwnersListDetail
    - see all owners, can see detail, can add or edit, can see cars of owners
  - OfficeListDetail
    - see all offices, can add or edit any office
  - UserList
    - see all users
  - UserDetail
    - can add or edit any user
  - CarsList
      - see all cars
  - CarDetail
      - see all details, can sign in/sign out car
  - CarAddEdit
      - car add/edit car
### ROLE_Kraj
  - OwnersListDetail
    - see all owners, can see detail, can't add or edit, can see cars of owners
  - OfficeListDetail
    - see offices in same region, can add or edit to/in own region
  - UserList
    - see users in same region
  - UserDetail
    - can add or edit to/in own region
    - can chance role to Kraj or Okres
  - CarsList
     - see all cars
  - CarDetail
     - see all details, no interaction
  - CarAddEdit
     - no access
### ROLE_Okres
  - CarsList
      - see all cars, can add car
  - CarDetail
      - see all details, can sign in/sign out car
  - CarAddEdit
      - car add/edit car
  - OwnersListDetail
      - see all owners, can see detail, can add or edit, can see cars of owner
  - UserDetail
      - can edit own profile

## Authentication
System is using Vaadin-related Spring security to authentication users. This system also contains REST API, which is using [JWT authentication](https://jwt.io/). unfortunately, it can't work both at the same time. This system can work with Vaadin authentication OR as REST API with JWT authentication. It have to be set in application.properties with property 'appType' which have to be VAADIN or JWT. 
## REST API documentation
Swagger documentation: http://localhost:8080/swagger-ui/index.html

