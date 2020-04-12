# Simple Kotlin Spring Boot JWT Implementation

#### Short Description
My own example implementation of JWT for Spring Boot applications using [jjwt](https://github.com/jwtk/jjwt). Most implementations I found require a lot of knowledge of Spring Security which can get very complicated, I designed this JWT implementation with minimal use of the built-in Spring Security classes and functions, leveraging the **GenericFilterBean** class to filter every incoming requests.  
Another reason this was made is the lack of tutorials and guides of Spring Boot in Kotlin.

#### How to use
* Install JDK8  
    - Ubuntu >= 18.04: `sudo apt install openjdk-8-jdk`
    - Windows (chocolatey): `choco install jdk8`
* [Install Git](https://git-scm.com/downloads)

* Clone this repository  
```
git clone https://github.com/vmasdani/simple-kotlin-spring-boot-jwt.git
cd simple-kotlin-spring-boot-jwt
```
* Run the application
    - Windows: `gradlew.exe bootRun`
    - Ubuntu: `./gradlew bootRun`
    - Or run via `Spring Boot Application` through Intellij
 
#### How this works
There are 2 main config files, `config/JwtFilter.kt` and `config/WebSecurity.kt`
* `WebSecurity.kt`  
Contains basic Spring Security configuration which enables CORS and disables CSRF protection in order browsers to be able to make requests to the REST API contained in this example application. Also routes every requests to the JwtFilter class.

* `JwtFilter.kt`  
Filters the URI endpoints which do not start with `/auth` and `/generator`. Every incoming requests other than `/auth` and `/generator` must follow the authentication process which uses the secret key via `jwt.secret` from the `application.properties` file. Follows the `Bearer <jwt.secret>` format. Currently only filters the subject (username in this case).  
Generate the JWT Secret using `/generator/jwtsecret` endpoint.

#### Available endpoints
* `/auth/login` RequestBody: username, password | return: String (jwt token)
* `/auth/register` RequestBody: name, username, password
* `/generator/jwtsecret` | return: String (jwt secret for `application.properties`)
* `/generator/bcryptpassword` RequestParam: password | return: String (bcrypt hashed password)
* `/api/users` | return: `List<{id: Int, name: String, username: String, password: String}>`