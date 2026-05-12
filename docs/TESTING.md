# Testing

## Libraries

Testing support is provided by `spring-boot-starter-test`, added to `backend/pom.xml` with `test` scope:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

This starter bundles the following libraries:

| Library                     | Purpose                                                        |
| --------------------------- | -------------------------------------------------------------- |
| **JUnit 5** (JUnit Jupiter) | Test framework — `@Test`, `@ExtendWith`, lifecycle annotations |
| **Mockito**                 | Mocking dependencies — `@Mock`, `@InjectMocks`, `when()`       |
| **AssertJ**                 | Fluent assertions — `assertThat()`                             |
| **Spring Test**             | Spring context and MVC testing utilities                       |

The existing `spring-boot-starter-webmvc-test` starter was kept for Spring MVC slice tests (`@WebMvcTest`).

---

## Test structure

Tests live under `backend/src/test/java/` and mirror the main source package structure:

```
backend/src/test/java/
└── io/ggroup/demo/
    └── controller/
        └── EventsControllerTest.java
```

---

## Tests added

### `EventsControllerTest`

**File:** `backend/src/test/java/io/ggroup/demo/controller/EventsControllerTest.java`

Unit tests for `EventsController` using Mockito to mock repository dependencies. No Spring context is loaded — tests are fast and isolated.

| Test                                        | Description                                                                         |
| ------------------------------------------- | ----------------------------------------------------------------------------------- |
| `getEventCount_returnsCount`                | Verifies `GET /api/events/count` returns HTTP 200 and the count from the repository |
| `getAllEvents_whenEventsExist_returnsOk`    | Verifies `GET /api/events` returns HTTP 200 when events are present                 |
| `getAllEvents_whenNoEvents_returnsNotFound` | Verifies `GET /api/events` returns HTTP 404 when the repository is empty            |

---

## Running the tests

All commands are run from the `backend/` directory.

**Run all tests:**

```bash
./mvnw test
```

**Run a single test class:**

```bash
./mvnw test -Dtest=EventsControllerTest
```

**Run a single test method:**

```bash
./mvnw test -Dtest=EventsControllerTest#getEventCount_returnsCount
```

**Skip tests during a build:**

```bash
./mvnw package -DskipTests
```

Test reports are generated in `backend/target/surefire-reports/` after each run.
