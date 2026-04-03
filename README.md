# ProjectG - Developer Manual

A full-stack event ticketing platform combining Spring Boot (backend) and React with TypeScript (frontend). The system manages events, venues, tickets, orders, customers, and sellers with role-based authentication.

## Project Overview

This project consists of two main parts:
- **Backend**: Java Spring Boot REST API with Spring Security and JPA
- **Frontend**: React 19 + TypeScript, Vite, Tailwind CSS v4

### Directory Structure

```
ProjectG/
├── backend/                          # Spring Boot REST API
│   ├── pom.xml
│   └── src/main/java/io/ggroup/demo/
│       ├── DemoApplication.java      # Entry point
│       ├── config/
│       │   ├── SecurityConfig.java   # Auth & role-based authorization
│       │   ├── CorsConfig.java       # CORS for frontend (port 3000)
│       │   ├── OpenApiConfig.java    # Swagger/OpenAPI setup
│       │   └── TestLoginConfig.java  # Test admin user
│       ├── controller/               # 9 REST controllers
│       │   ├── CustomerController.java
│       │   ├── EventsController.java
│       │   ├── IssuedTicketController.java
│       │   ├── OrdersController.java
│       │   ├── OrderDetailsController.java
│       │   ├── PostalCodeController.java
│       │   ├── TicketController.java
│       │   ├── UserController.java
│       │   └── VenuesController.java
│       ├── dto/                      # Request/response DTOs
│       │   ├── CreateCustomerRequest.java
│       │   ├── CreateUserRequest.java
│       │   ├── CustomerResponse.java
│       │   └── UserResponse.java
│       ├── model/                    # JPA entities & enums
│       │   ├── User.java, Customer.java, Seller.java
│       │   ├── Event.java, Venue.java, Category.java
│       │   ├── Ticket.java, TicketType.java, IssuedTicket.java
│       │   ├── Order.java, OrderDetails.java, SalesSession.java
│       │   └── enums: Role, AccountStatus, EventStatus, PaymentMethod
│       ├── repository/               # Spring Data JPA repositories
│       └── service/
│           └── UsersDetailsService.java  # Spring Security integration
│
├── frontend/                         # React TypeScript app
│   ├── index.html                    # App entry HTML (fonts, icons)
│   ├── vite.config.ts                # Vite: React plugin, Tailwind, API proxy
│   ├── tailwind.config.ts            # Design system: colors, radius, shadows
│   ├── tsconfig.json                 # TypeScript config (strict, path aliases)
│   ├── package.json
│   └── src/
│       ├── index.tsx                 # React entry point
│       ├── App.tsx                   # Root component — mounts RouterProvider
│       ├── router.tsx                # React Router — all route definitions
│       ├── styles/index.css          # Tailwind v4 @theme tokens + custom classes
│       ├── layouts/
│       │   ├── MainLayout.tsx        # Navbar + children + Footer (public pages)
│       │   ├── AdminLayout.tsx       # Sidebar + children (admin pages)
│       │   ├── Navbar.tsx            # Fixed top nav with search, sign in, admin link
│       │   ├── Sidebar.tsx           # Fixed left nav for admin panel
│       │   └── Footer.tsx            # Site footer
│       ├── pages/
│       │   ├── HomePage.tsx          # Home: hero, about, upcoming events
│       │   └── admin/
│       │       └── CreateEventPage.tsx  # Admin: create event form
│       ├── components/
│       │   ├── EventCard.tsx         # Reusable event card (homepage)
│       │   └── admin/
│       │       └── TicketTierRow.tsx  # Editable ticket tier table row
│       ├── types/
│       │   ├── event.ts              # Event interface
│       │   └── ticketTier.ts         # TicketTier interface
│       ├── config/
│       │   └── env.ts                # API base URL (VITE_API_BASE_URL)
│       ├── features/                 # Feature modules — in progress
│       ├── hooks/                    # Custom React hooks — in progress
│       ├── services/                 # API service modules — in progress
│       ├── store/                    # State management — in progress
│       └── utils/                    # Utility functions — in progress
│
├── docs/
│   ├── api/                          # Per-resource API docs (9 files)
│   └── photos/                       # Architecture & UX diagrams
│
├── build.js                          # API documentation generator
├── APIDOCUMENTATION.md               # API docs template
├── APIDOCUMENTATION.generated.md     # Generated API reference
└── DOKUMENTAATIO.md                  # Finnish project documentation
```

## Prerequisites

Before running this project, ensure you have:
- Java 21 or higher
- Node.js v18 or higher
- npm
- Maven (or use the included Maven wrapper)

## Backend Setup

The backend runs on port 8080 and provides REST API endpoints.

### API Documentation

Once the backend is running:

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Available Endpoints

| Resource | Base Path |
|---|---|
| Customers | `/api/customers` |
| Events | `/api/events` |
| Tickets | `/api/tickets` |
| Issued Tickets | `/api/issued-tickets` |
| Orders | `/api/orders` |
| Order Details | `/api/order-details` |
| Venues | `/api/venues` |
| Users | `/api/users` |
| Postal Codes | `/api/postal-codes` |

See `APIDOCUMENTATION.generated.md` or Swagger UI for full endpoint details.

### Running the Backend

#### Option 1: Spring Boot Dashboard (VS Code)

1. Open the Spring Boot icon in the VS Code sidebar
2. Click the Run button next to the application name

#### Option 2: Terminal

```bash
cd backend
./mvnw spring-boot:run
```

Windows PowerShell:
```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

The backend will be available at `http://localhost:8080`

### CORS

`CorsConfig.java` allows requests from `http://localhost:3000`. Update `allowedOrigins` if you change the frontend port.

## Frontend Setup

### Running the Frontend

```bash
cd frontend
npm install       # first time only
npm run dev
```

The app opens at `http://localhost:3000`

### Routes

| Path | Page | Layout |
|---|---|---|
| `/` | Homepage — hero, about, upcoming events | MainLayout |
| `/admin/events/create` | Admin — create event form | AdminLayout |

### Frontend Architecture

The frontend uses **Vite** as the build tool and dev server. API requests to `/api/*` are automatically proxied to `http://localhost:8080` by Vite during development — no CORS issues, no hardcoded hostnames in fetch calls.

Routing is handled by **React Router v7** (`createBrowserRouter`). All routes are defined in `src/router.tsx`. Each route is paired with its layout — `MainLayout` for public pages, `AdminLayout` for admin pages.

Styling is done entirely with **Tailwind CSS v4** using a custom design system defined in `src/styles/index.css` via `@theme`. Custom tokens include the full Material Design 3 color palette, Inter font family, and ambient shadow scale.

### Frontend Scripts

| Command | Description |
|---|---|
| `npm run dev` | Start dev server at localhost:3000 with HMR |
| `npm run build` | Type-check + production build to `dist/` |
| `npm run preview` | Preview the production build locally |

## Development Workflow

1. Start the backend (`./mvnw spring-boot:run` from `/backend`)
2. Start the frontend (`npm run dev` from `/frontend`)
3. Open `http://localhost:3000`
4. Backend changes require a restart (DevTools auto-reload is enabled)
5. Frontend changes hot-reload instantly via Vite HMR

## Troubleshooting

**Frontend shows connection error**
- Ensure backend is running on port 8080
- Check `CorsConfig.java` `allowedOrigins`

**Port already in use**
- Backend: set `server.port` in `application.properties`
- Frontend: change `server.port` in `vite.config.ts`

**Build fails**
- Backend: `./mvnw clean install`
- Frontend: delete `node_modules` and run `npm install`

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

Output goes to `frontend/dist/`.

## Technology Stack

**Backend:**
- Spring Boot 4.0.1
- Java 21
- Maven
- Spring Security (role-based auth: ADMIN, USER)
- Spring Data JPA + H2 (in-memory)
- Lombok
- SpringDoc OpenAPI (Swagger UI)

**Frontend:**
- React 19
- TypeScript 6.0
- Vite 8
- Tailwind CSS v4 (CSS-first, @theme tokens)
- React Router v7
- Material Symbols Outlined (icons)
- Inter (font)
