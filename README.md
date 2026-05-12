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
│       │   ├── H2Config.java         # Registers H2 console via JakartaWebServlet
│       │   └── TestLoginConfig.java  # Creates test users on startup
│       ├── controller/               # REST controllers
│       │   ├── AuthController.java   # POST /api/auth/login → { email, role }
│       │   ├── CustomerController.java
│       │   ├── EventsController.java
│       │   ├── IssuedTicketController.java
│       │   ├── OrdersController.java
│       │   ├── OrderDetailsController.java
│       │   ├── PostalCodeController.java
│       │   ├── SellerController.java
│       │   ├── TicketController.java
│       │   ├── UserController.java
│       │   ├── VenuesController.java
│       │   ├── InspectController.java
│       │   ├── RegisterController.java
│       │   ├── CategoryController.java
│       │   ├── MyyntiraporttiController.java
│       │   └── KuittiController.java
│       ├── dto/
│       │   ├── CreateOrderRequest.java       # { customerId }
│       │   ├── CreateOrderDetailsRequest.java # { id: { orderId, ticketId }, quantity }
│       │   ├── CreateCustomerRequest.java
│       │   ├── CreateUserRequest.java
│       │   ├── CustomerResponse.java
│       │   ├── UserResponse.java
│       │   ├── LoginRequest.java
│       │   ├── RegisterRequest.java
│       │   ├── MyyntiraporttiDTO.java
│       │   └── KuittiDTO.java
│       ├── model/                    # JPA entities
│       │   ├── User.java, Customer.java, Seller.java
│       │   ├── Event.java, Venue.java, Category.java
│       │   ├── Ticket.java, IssuedTicket.java
│       │   ├── Order.java, OrderDetails.java
│       │   ├── PaymentMethod.java, EventStatus.java, PostalCode.java
│       │   └── ErrorResponse.java
│       ├── repository/               # Spring Data JPA repositories
│       └── service/
│       │   ├── EmailService.java         # Sends email
│       │   ├── KuittiService.java        # Fetches receipt for a specific order
│       │   ├── QRService.java            # Generates QR code of 10 characters lenght 
│           └── UsersDetailsService.java  # Assigns roles: ADMIN / SELLER / CUSTOMER
│
├── frontend/                         # React TypeScript app
│   ├── index.html
│   ├── vite.config.ts                # Vite: React plugin, Tailwind, API proxy → :8080
│   ├── tsconfig.json
│   ├── package.json
│   └── src/
│       ├── router.tsx                # All route definitions
│       ├── styles/index.css          # Tailwind v4 @theme design tokens
│       ├── context/
│       │   └── AuthContext.tsx       # Session auth: { email, role } stored in localStorage
│       ├── layouts/
│       │   ├── MainLayout.tsx        # Navbar + Footer (public pages)
│       │   ├── AdminLayout.tsx       # Sidebar (admin pages)
│       │   ├── Navbar.tsx            # Search, Sign In/out, Admin link (role-gated)
│       │   ├── Sidebar.tsx           # Admin nav + Home link
│       │   └── Footer.tsx
│       ├── pages/
│       │   ├── HomePage.tsx          # Hero, about, searchable event grid
│       │   ├── EventDetailPage.tsx   # Event info + ticket purchase
│       │   ├── SignIn.tsx
│       │   ├── RegisterPage.tsx
│       │   └── admin/
│       │       ├── DashboardPage.tsx     # Stats + upcoming events (clickable)
│       │       ├── CreateEventPage.tsx   # Create event + ticket tiers
│       │       ├── EditEventPage.tsx     # Edit existing event
│       │       └── TicketFetcherPage.tsx # QR scanner
│       ├── components/
│       │   ├── EventCard.tsx
│       │   ├── CheckoutModal.tsx     # Multi-step ticket purchase flow
│       │   └── admin/
│       │       └── TicketTierRow.tsx
│       ├── api/
│       │   ├── apiClient.ts          # Axios wrapper (credentials: include)
│       │   ├── eventService.ts       # getEvents, getEvent, createEvent, updateEvent
│       │   ├── ticketService.ts      # getTicketsByEvent, createTicket
│       │   ├── orderService.ts       # createCustomer, createOrder, createOrderDetail
│       │   ├── venueService.ts       # getVenues
│       │   └── sellerService.ts      # getSellers
│       ├── types/
│       │   ├── event.ts              # Event, Venue, Category, Seller interfaces
│       │   ├── ticket.ts             # Ticket interface
│       │   └── ticketTier.ts         # TicketTier interface (admin form)
│       └── utils/
│           └── categoryImage.ts      # Maps category → hero image
│
├── docs/
│   ├── api/                          # Per-resource API docs
│   └── photos/                       # Architecture & UX diagrams
│
├── build.js
├── APIDOCUMENTATION.md
├── APIDOCUMENTATION.generated.md
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

| Resource       | Base Path             |
| -------------- | --------------------- |
| Customers      | `/api/customers`      |
| Events         | `/api/events`         |
| Tickets        | `/api/tickets`        |
| Issued Tickets | `/api/issued-tickets` |
| Orders         | `/api/orders`         |
| Order Details  | `/api/order-details`  |
| Venues         | `/api/venues`         |
| Users          | `/api/users`          |
| Postal Codes   | `/api/postal-codes`   |

See `APIDOCUMENTATION.generated.md` or Swagger UI for full endpoint details.

### Running the Backend

The backend requires a database profile. Two options:

#### Option A: H2 in-memory (recommended for local dev)

No setup needed — uses an embedded database with automatic test data.

Create `backend/src/main/resources/application.properties` with:

```properties
spring.application.name=demo
spring.profiles.active=h2
```

Then run:

```bash
cd backend
./mvnw spring-boot:run
```

Test data is seeded automatically on every startup:

| Account  | Email               | Password      | Role     |
| -------- | ------------------- | ------------- | -------- |
| Admin    | `admin@test.com`    | `admin123`    | ADMIN    |
| Seller   | `seller@test.com`   | `seller123`   | SELLER   |
| Customer | `customer@test.com` | `customer123` | CUSTOMER |
| Seller   | `sami@gg.com`       | `seller123`   | SELLER   |
| Customer | `daniel@gmail.com`  | `customer123` | CUSTOMER |

Seeded data includes: 3 events, 10 ticket types, 3 venues, payment methods (card/bank/cash).

> **Note:** H2 is in-memory — all data resets on every backend restart. Log out and back in after restarting.

H2 console available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

#### Option B: Render PostgreSQL (shared cloud DB)

Create `backend/src/main/resources/application.properties` with:

```properties
spring.application.name=demo
spring.profiles.active=local
```

Then fill in `application-local.properties` with credentials from the Render dashboard (ask a teammate for the file).

#### Running

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

| Path                       | Page                                      | Access         |
| -------------------------- | ----------------------------------------- | -------------- |
| `/`                        | Homepage — hero, about, searchable events | Public         |
| `/events/:id`              | Event detail + ticket purchase            | Public         |
| `/signin`                  | Sign in                                   | Public         |
| `/register`                | Register                                  | Public         |
| `/admin`                   | Dashboard — stats + upcoming events       | ADMIN / SELLER |
| `/admin/events/create`     | Create event + ticket tiers               | ADMIN / SELLER |
| `/admin/events/:id/edit`   | Edit existing event                       | ADMIN / SELLER |
| `/admin/ticket-fetcher`    | QR code scanner                           | ADMIN / SELLER |

### Role Permissions

| Action                | ADMIN | SELLER | CUSTOMER |
| --------------------- | ----- | ------ | -------- |
| View events & tickets | ✓     | ✓      | ✓        |
| Purchase tickets      | ✓     | ✓      | ✓        |
| Create / edit events  | ✓     | ✓      | —        |
| Create / edit tickets | ✓     | ✓      | —        |
| Access admin panel    | ✓     | ✓      | —        |
| Delete resources      | ✓     | —      | —        |
| Manage users          | ✓     | —      | —        |

### Frontend Architecture

The frontend uses **Vite** as the build tool and dev server. API requests to `/api/*` are automatically proxied to `http://localhost:8080` by Vite during development — no CORS issues, no hardcoded hostnames in fetch calls.

Routing is handled by **React Router v7** (`createBrowserRouter`). All routes are defined in `src/router.tsx`. Each route is paired with its layout — `MainLayout` for public pages, `AdminLayout` for admin pages.

Styling is done entirely with **Tailwind CSS v4** using a custom design system defined in `src/styles/index.css` via `@theme`. Custom tokens include the full Material Design 3 color palette, Inter font family, and ambient shadow scale.

### Frontend Scripts

| Command           | Description                                 |
| ----------------- | ------------------------------------------- |
| `npm run dev`     | Start dev server at localhost:3000 with HMR |
| `npm run build`   | Type-check + production build to `dist/`    |
| `npm run preview` | Preview the production build locally        |

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

- Spring Boot 4
- Java 21
- Maven
- Spring Security — session-based auth, roles: ADMIN / SELLER / CUSTOMER
- Spring Data JPA
- H2 in-memory (dev) / PostgreSQL (production)
- SpringDoc OpenAPI (Swagger UI)

**Frontend:**

- React 19 + TypeScript
- Vite (dev server + proxy to :8080)
- Tailwind CSS v4 (CSS-first, @theme design tokens)
- React Router v7
- Axios (API client with session credentials)
- Material Symbols Outlined (icons)
- Inter (font)
