# CA Onboarding Automation System

A Spring Boot application that automates the onboarding process for new CA joiners by handling form submissions, generating UTM links with QR codes, and sending styled welcome emails.

## Features

- Form submission for new joinee registration
- Automatic UTM link generation with TinyURL integration
- QR code generation for UTM links
- Styled HTML email template with Thymeleaf
- MongoDB database integration
- RESTful API endpoints with Swagger/OpenAPI documentation
- CSV export functionality
- Simple and responsive UI
- Comprehensive test coverage
- Test traceability matrix

## Prerequisites

- Java 17 or later
- MongoDB Atlas account
- Maven 3.6 or later
- Gmail account (for SMTP) or other email service
- TinyURL API key

## Setup Instructions

1. **Clone the repository**

```bash
git clone 
```

2. **Environment Configuration**

- Copy `.env.example` to `.env`
- Update the following environment variables:
  - `MONGODB_URI`: Your MongoDB connection string
  - `MAIL_USERNAME`: Your email address
  - `MAIL_PASSWORD`: Your email app password
  - `TINYURL_API_KEY`: Your TinyURL API key

3. **Build and Run**

```bash
mvn clean install
mvn spring-boot:run
```

4. **Access the Application**

- Web Interface: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI Documentation: `http://localhost:8080/v3/api-docs`

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

### Export Joinee Data

**GET** `/api/joinee/export`

Response:
- Success (200): CSV file containing joinee records
- Error (500): "Error exporting data"

## Database Schema

### Joinee Collection
```json
{
    "_id": "ObjectId",
    "name": "String",
    "email": "String",
    "utmLink": "String",
    "shortUrl": "String",
    "qrCodeBase64": "String",
    "createdAt": "Date"
}
```

## Testing

The application includes comprehensive test coverage:

- Unit Tests
  - Service Layer (Email, QR Code, TinyURL)
  - Controller Layer
  - Model Validation
  - DTO Validation
  - Utility Classes

- Integration Tests
  - API Endpoints
  - External Service Integration

Run tests using:
```bash
mvn test
```

A detailed test traceability matrix is available in `test-traceability-matrix.md`.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.


