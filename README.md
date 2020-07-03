

# Setup

1. Create a database and run the sql script (current script is only for MariaDB/MySQL) which is included in the zip

2. configure the following application.properties file

example configuration:

spring.datasource.username=root
spring.datasource.password=
spring.datasource.url=jdbc:mysql://localhost:3306/blog2

3. set JVM properties private.key and jwt.expiration.minutes

exmple configuration:

-Dprivate.key=secret
-Djwt.expiration.minutes=100

After all the steps are complete the application will be ready for usage

# Api Documentation

## Auth

| Method | Url       | Decription |
| ------ | --------- | ---------- |
| POST   | /register | Sign up    | 
| POST   | /login    | Log in     |

## Blog entries

| Method | Url                | Decription       | 
| ------ | -------------------| -----------------| 
| POST   | /entries           | Create entry     | 
| GET    | /entries           | Get entries list |
| PUT    | /entries/{entryId} | Update entry     |
| DELETE | /entries/{entryId} | Delete entry     |



## Sample requests and responses

### Register

Get the details of the currently Authenticated User along with basic
subscription information.

**URL** : `/register`

**Method** : `GET`

**Auth required** : No

## Success Response

**Code** : `200 OK`

**Content examples**

For a User with ID 1234 on the local database where that User has saved an
email address and name information.

```json
{
    "email" : "sample@mail.com"
    "password" : "samplePassword"
}
```
### Login

Get the details of the currently Authenticated User along with basic
subscription information.

**URL** : `/login`

**Method** : `POST`

**Auth required** : Email and password Credentials

## Success Response

**Code** : `200 OK`

**Content examples**

For a User with ID 1234 on the local database where that User has saved an
email address and name information.

```json
{
    "email" : "sample@mail.com"
    "password" : "samplePassword"
}
```
