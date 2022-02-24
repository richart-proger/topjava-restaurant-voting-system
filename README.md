# Restaurant voting system #
>
> Software requirements in <a href="REQUIREMENTS.md">REQUIREMENTS.md</a> file.

### Technology stack:
* Java 17
* Spring 5
* Spring DATA JPA
* Spring MVC
* Spring Security
* Hibernate
* HyperSQL Database
* Ehcache 3
* JUnit 5
* Hamcrest All
* AssertJ
* JSONassert
* Jackson Databind
* SLF4J
* SoapUI
* Maven 3
* Swagger2


### How to Setup

*1. Make a clone of the application project using the link from the repository*

```bash
git clone https://github.com/richart-proger/topjava-restaurant-voting-system
```

*2. Build the project using the mvn package command, and then deploy the project using Apache Tomcat.*

```bash
mvn package
```
***Or run the application using IntelliJ IDEA.***

The app will start running at <http://localhost:8088>.

*3. Explore Rest APIs*

>Documentation is available in Swagger: <http://localhost:8088/restaurant_voting_system/swagger-ui.html> 

Credentials are in Swagger description.
____
#### API endpoints
URL = http://localhost:8088/restaurant_voting_system

| API        | Method | Description                                    | URL                                       | User           |
|------------|--------|------------------------------------------------|-------------------------------------------|----------------|
| Admin      | GET    | Get all profiles                               | {URL}/rest/admin/users                    | Admin          |
|            | POST   | Create or update profile with location         | {URL}/rest/admin/users                    | Admin          |
|            | GET    | Get profile by id                              | {URL}/rest/admin/users{id}                | Admin          |
|            | PUT    | Update profile                                 | {URL}/rest/admin/users{id}                | Admin          |
|            | DELETE | Delete profile                                 | {URL}/rest/admin/users{id}                | Admin          |
|            | GET    | Get profile by email                           | {URL}/rest/admin/users/by-email           | Admin          |
| Profile    | GET    | Get profile                                    | {URL}/rest/profile                        | Authorized     |
|            | PUT    | Update profile                                 | {URL}/rest/profile                        | Authorized     |
|            | DELETE | Delete profile                                 | {URL}/rest/profile                        | Authorized     |
|            | POST   | Register profile                               | {URL}/rest/profile/register               | Unauthorized   |
| Dish       | GET    | Get all dishes                                 | {URL}/rest/admin/dishes                   | Admin          |
|            | GET    | Get dish by id                                 | {URL}/rest/admin/dishes/{id}              | Admin          |
|            | PUT    | Update dish                                    | {URL}/rest/admin/dishes/{id}              | Admin          |
|            | DELETE | Delete dish                                    | {URL}/rest/admin/dishes/{id}              | Admin          |
|            | POST   | Create dish with location                      | {URL}/rest/admin/dishes/restaurants/{id}  | Admin          |
| Restaurant | GET    | Get all restaurants                            | {URL}/rest/restaurants                    | Everyone       |
|            | POST   | Create restaurant with location                | {URL}/rest/restaurants                    | Admin          |
|            | GET    | Get restaurant by id                           | {URL}/rest/restaurants/{id}               | Authorized     |
|            | POST   | Create vote with location                      | {URL}/rest/restaurants/{id}               | Authorized     |
|            | PUT    | Update restaurant                              | {URL}/rest/restaurants/{id}               | Admin          |
|            | DELETE | Delete restaurant                              | {URL}/rest/restaurants/{id}               | Admin          |
|            | GET    | Get a specific restaurant with its menu/menus  | {URL}/rest/restaurants/{id}/menu          | Authorized     |
|            | POST   | Create menu with location                      | {URL}/rest/restaurants/{id}/menu          | Admin          |
|            | PUT    | Update menu                                    | {URL}/rest/restaurants/{id}/menu          | Admin          |
|            | DELETE | Delete menu                                    | {URL}/rest/restaurants/{id}/menu          | Admin          |
|            | GET    | Get all restaurants with menus                 | {URL}/rest/restaurants/menus              | Everyone       |
| Vote       | GET    | Get all user votes                             | {URL}/rest/votes                          | Authorized     |
|            | GET    | Get user vote by id                            | {URL}/rest/votes/{id}                     | Authorized     |
|            | DELETE | Delete user vote                               | {URL}/rest/votes/{id}                     | Authorized     |
|            | GET    | Get all votes history                          | {URL}/rest/votes/history                  | Admin          |
|            | GET    | Get vote by id                                 | {URL}/rest/votes/history/{id}             | Admin          |
---