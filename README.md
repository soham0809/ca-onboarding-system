# CA Onboarding Automation System

A Spring Boot application that automates the onboarding process for new CA joiners by handling form submissions, generating UTM links, and sending welcome emails.

## Features

- Form submission for new joinee registration
- Automatic UTM link generation
- HTML email template with Thymeleaf
- MySQL database integration
- RESTful API endpoints
- Simple and responsive UI

## Prerequisites

- Java 17 or later
- MongoDB Atlas account
- Maven 3.6 or later
- Gmail account (for SMTP) or other email service

## Setup Instructions

1. **Clone the repository**

```bash
git clone <repository-url>
cd ca-onboarding-system
```

2. **Configure Database**

- Create a MongoDB Atlas cluster
- Update the MongoDB connection string in `src/main/resources/application.properties`
- The application will automatically create the required collections

3. **Configure Email**

- Update email configuration in `src/main/resources/application.properties`
- For Gmail, you'll need to:
  - Enable 2-Step Verification
  - Generate an App Password
  - Use the App Password in the configuration

4. **Build and Run**

```bash
mvn clean install
mvn spring-boot:run
```

5. **Access the Application**

- Web Form: `http://localhost:8080`
- API Endpoint: `http://localhost:8080/api/joinee/submit`
- Test Endpoint: `http://localhost:8080/api/joinee/test`

## API Documentation

### Submit New Joinee

**POST** `/api/joinee/submit`

Request Body:
```json
{
    "name": "John Doe",
    "email": "john@example.com"
}
```

Response:
- Success (200): "Registration successful! Please check your email."
- Error (500): "An error occurred during registration. Please try again."

## Database Collections

### Joinee Collection
```json
{
    "_id": "ObjectId",
    "name": "String",
    "email": "String",
    "utmLink": "String",
    "createdAt": "Date"
}
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.


