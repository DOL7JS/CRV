# CRV
CRV system is system for registring and unregistring vehicles. In this system user can do:
- User management - create, edit user
- Car management - create, edit car, add owner to car
- Branch office management - create, edit office, add user to office
- Owner management - create, edit owner

This system is developed with:
- [Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MariaDB](https://mariadb.org/download/)
- [Vaadin](https://vaadin.com/)

## Get started
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
### ROLE_Kraj
### ROLE_Okres

## Authentication

## API documentation
Swagger documentation: http://localhost:8080/swagger-ui/index.html

