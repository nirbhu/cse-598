# Sports Concussion Assessment System

A web-based system for tracking and monitoring sports-related concussion symptoms. This application helps athletes, coaches, and healthcare professionals collaborate in managing concussion risks.

## Features

- **Symptom Recording**: Athletes can log their symptoms after each game
- **Real-time Monitoring**: Coaches and healthcare professionals can track athletes' conditions
- **Risk Assessment**: Automatic severity rating calculation (Green/Yellow/Red)
- **Role-based Access**: Different views and permissions for athletes, coaches, and healthcare staff
- **Mobile-Friendly**: Responsive design works on all devices

## Getting Started

### Prerequisites

- Java 21 or later
- Maven 3.6+ (or use included Maven wrapper)

### Running the Application

2. Run using the start script:
 - /start.sh


3. Access at `http://localhost:3000`

## Test Accounts

### Athletes
- Username: `athlete1` / Password: `password`
- Username: `athlete2` / Password: `password`
- Can record symptoms and view personal history

### Coach
- Username: `coach1` / Password: `password`
- Can view all athletes' summaries and track severity ratings

### Healthcare Professional
- Username: `healthcare1` / Password: `password`
- Full access to detailed symptom histories and medical notes
- Receives alerts for high-risk cases

## Severity Rating System

The system uses three levels to indicate risk:

- 🟢 **NO_DIFFERENCE**: Stable condition
- 🟡 **UNSURE**: Requires monitoring
- 🔴 **VERY_DIFFERENT**: Immediate attention needed

## Technology Stack

- Backend: Spring Boot 3.2.3
- Frontend: Thymeleaf + Bootstrap 5.3.2
- Security: Spring Security
- Database: H2 (in-memory)

## Development

To run in development mode with hot-reload:
mvn spring-boot:run -Dspring-boot.run.profiles=dev

Sample users:
- Username: `athlete1` / Password: `password`
- Username: `coach1` / Password: `password`
- Username: `healthcare1` / Password: `password`
