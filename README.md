# ignitis-task

To setup the application

1. Create a database and run the sql script (current script is only for MariaDB/MySQL) which is included in the zip

2. configure the following application.properties file

example configuration:

spring.datasource.username=root

spring.datasource.password=

spring.datasource.url=jdbc:mysql://localhost:3306/blog2

3. set JVM properties private.key and jwt.expiration.minutes

exmple configuration

-Dprivate.key=secret

-Djwt.expiration.minutes=100

After all the steps are complete the application will be ready for usage
