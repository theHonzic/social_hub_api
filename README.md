# Social hub API

## About the project
This API was created as part of my Social hub mobile application. It leverages the power and flexibility of Ktor web client to provide basic auth functionality as well as providing useful data to the application.
# Tech stack
## API
- Kotlin
- Ktor
- JWT token Auth
- Exposed DB
# Features
## Auth
 - Login
 POST request allowing user to login with email/username and password.
- Register
POST request allowing user to create account.
- Account availibility check
POST request checking the availability of email/phone number/username.
- 2FA
Requesting additional check when user attempts to access the account from different device.
