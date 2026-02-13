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
  "error": "Event not found"
}
```
