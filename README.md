# ProjectG - Developer Manual

A full-stack web application combining Spring Boot (backend) and React with TypeScript (frontend).

## Project Overview

This project consists of two main parts:
- **Backend**: Java Spring Boot REST API
- **Frontend**: React application with TypeScript

### Directory Structure

```
ProjectG/
├── backend/               # Spring Boot backend
│   ├── src/
│   │   └── main/java/io/ggroup/backend/
│   │       ├── config/
│   │       │   └── CorsConfig.java
│   │       ├── controller/
│   │       │   └── HelloController.java
│   │       └── DemoApplication.java
│   └── pom.xml
│
└── frontend/           # React frontend
    ├── src/
    │   └── App.tsx
    └── package.json
```

## Prerequisites

Before running this project, ensure you have:
- Java 21 or higher
- Node.js (v16 or higher recommended)
- npm or yarn
- Maven (or use the included Maven wrapper)

## Backend Setup

The backend runs on port 8080 and provides REST API endpoints.

### API Documentation

The backend includes **Swagger/OpenAPI** documentation for all endpoints. Once the backend is running, you can access:

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Swagger UI provides an interactive interface to explore and test all API endpoints.

### Available Endpoints

- `GET /api/hello` - Returns a greeting message from the server
- `GET /api/status` - Returns current server status with timestamp

### Running the Backend

#### Option 1: Using Spring Boot Dashboard (Recommended for VS Code)

If you have the Spring Boot Dashboard extension installed:

1. Look for the Spring Boot icon in the VS Code sidebar
2. The extension will automatically detect your Spring Boot application
3. Click the "Run" (play) button next to your application name
4. The backend will start in a VS Code terminal

#### Option 2: Using Terminal

Navigate to the backend directory and start the Spring Boot application:

```bash
cd backend
./mvnw spring-boot:run
```

On Windows with PowerShell:
```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

The backend will be available at `http://localhost:8080`

### Configuration

CORS is configured in `CorsConfig.java` to allow requests from the React development server running on port 3000. If you need to change this, modify the `allowedOrigins` setting.

## Frontend Setup

The frontend is a React application built with TypeScript. It communicates with the backend API and displays data fetched from the server.

### Running the Frontend

In a separate terminal, navigate to the frontend directory:

```bash
cd frontend
npm install  # Run this only on first setup
npm start
```

The React app will open in your browser at `http://localhost:3000`

### Features

- Auto-fetches data from the backend on page load
- Displays backend status and messages
- Manual refresh button to re-fetch data
- Error handling when backend is unavailable

## Development Workflow

1. Start the backend server first (port 8080)
2. Start the frontend development server (port 3000)
3. Open `http://localhost:3000` in your browser
4. Make changes to either backend or frontend code
   - Backend: Changes require restart (or use Spring DevTools for auto-reload)
   - Frontend: Changes will hot-reload automatically

## Troubleshooting

**Frontend shows connection error**
- Make sure the backend is running on port 8080
- Check that CORS is properly configured in `CorsConfig.java`

**Port already in use**
- Backend: Change server.port in `application.properties`
- Frontend: React will prompt to use a different port automatically

**Build fails**
- Backend: Run `./mvnw clean install` to rebuild
- Frontend: Delete `node_modules` and run `npm install` again

## Building for Production

### Backend
```bash
cd backend
./mvnw clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd frontend
npm run build
```

The optimized production build will be in the `frontend/build` directory.

## Technology Stack

**Backend:**
- Spring Boot 4.0.1
- Java 21
- Maven
- Lombok

**Frontend:**
- React 18
- TypeScript
- Create React App
