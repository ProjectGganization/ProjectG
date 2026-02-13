# API Documentation

## Events API

Base URL: `/api/events`

### Get Event by ID

Retrieve a single event by its unique identifier.

**Endpoint:** `GET /api/events/{id}`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | Event found successfully |
| 404  | Event not found          |
| 500  | Internal server error    |

**Response Body (200 OK):**

Content-Type: `application/json`

```json
{
  "id": 1,
  "name": "Event Name",
  "description": "Event Description",
  "startDate": "2026-03-15T19:00:00",
  "endDate": "2026-03-15T23:00:00",
  "venue": {
    "id": 1,
    "name": "Venue Name"
  },
  "category": {
    "id": 1,
    "name": "Category Name"
  },
  "eventStatus": {
    "id": 1,
    "status": "Active"
  }
}
```

**Example Request:**

```bash
curl -X GET http://localhost:8080/api/events/1
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "Not Found"
}
```

---

### Get Event Count

Returns the total number of events in the system.

**Endpoint:** `GET /api/events/count`

**Response Codes:**

| Code | Description                  |
| ---- | ---------------------------- |
| 200  | Count retrieved successfully |

**Response Body (200 OK):**

Content-Type: `application/json`

```json
5
```

**Example Request:**

```bash
curl -X GET http://localhost:8080/api/events/count
```

---

## Notes

- All endpoints return JSON format
- Authentication/Authorization requirements will be added in future versions
- Swagger UI documentation available at `/swagger-ui.html` when the application is running
