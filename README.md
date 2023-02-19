# Architect
- I applied DDD for code organization for this project (domain package - business for internal, application & infrastructure for external)
- Design patterns are applied (creation group: singleton, builder, factory)
- Apply CQRS to divide services (Command & Query)

# Tech stacks
- Framework: Springboot
- ORM: Hibernate
- API consume: RESTFUL
- Log: Log4j & SpringAOP
- Document: OpenAPI3 with Swagger (<host>/swagger-ui/index.html)
- Exception handler: handle with @RestControllerAdivce
- Validation: javax validation & custom validation
- i18n: config i18n with header (Accept-Language key) with message bundle (en & vi)

# Guide
- Please import the attached file `weather.sql` in /documents to your database and then run the project in IDE
or build this project to .jar file and run in any javac
- Import postman file and environment for testing