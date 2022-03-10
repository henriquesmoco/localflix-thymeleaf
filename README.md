# localflix-thymeleaf
This is an multi tenant app for renting movies.

##### Technologies:

* Spring MVC - 4.3.3.RELEASE
* Spring Data JPA - 1.10.4.RELEASE
* Thymeleaf - 3.0.2.RELEASE
* Hibernate - 5.2.2.Final
* Liquibase - 3.5.1
* Webjars
* Others

## Tenant in URL Path
Each tenant is identified by the url path, like this:

> http://localhost:8080/localflix-web/**tenant1**/catalog/list

This tenantId **tenant1** maps to a configured datasource in the
server with the same JNDI name **java:/tenant1**

## Updating the database
mvn liquibase:update

mvn liquibase:update -Pproduction -Dliquibase.verbose=false
