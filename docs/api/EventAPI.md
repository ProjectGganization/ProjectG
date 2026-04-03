## Event API
Base URL: `/api/events`

**Permissions**

| Endpoint           | Method | Required Role     |
| ------------------ | ------ | ----------------- |
| `/api/events`      | GET    | `ANYONE`          |
| `/api/events/{id}` | GET    | `ANYONE`          |
| `/api/events`      | POST   | `ADMIN`, `SELLER` |
| `/api/events/{id}`  | PUT    | `ADMIN`, `SELLER` |
| `/api/events/{id}`  | DELETE | `ADMIN`           |

<details>
  <summary><strong>Get All Events</strong></summary>

**Endpoint:** `GET /api/events`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Response Codes

| Code | Description                   |
| ---- | ----------------------------- |
| 200  | All events found successfully |
| 404  | No events found               |
| 500  | Internal server error         |

### Response Body (200 OK)

Content-Type: `application/json`

```json
[
  {
    "eventId": 1,
    "title": "Spring Music Festival",
    "description": "Live event in city center",
    "startTime": "2026-05-15T18:00:00",
    "endTime": "2026-05-15T22:00:00",
    "venue": {
      "venueId": 1,
      "name": "Helsinki Arena",
      "address": "Mannerheimintie 13"
    }
  }
]
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/events
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "No events found"
}
```

</details>

<details>
  <summary><strong>Get Event By ID</strong></summary>

**Endpoint:** `GET /api/events/{id}`
**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

### Response Codes

| Code | Description              |
| ---- | ------------------------ |
| 200  | Event found successfully |
| 404  | Event not found          |
| 500  | Internal server error    |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "eventId": 1,
  "title": "Spring Music Festival",
  "description": "Live event in city center",
  "startTime": "2026-05-15T18:00:00",
  "endTime": "2026-05-15T22:00:00",
  "venue": {
    "venueId": 1,
    "name": "Helsinki Arena",
    "address": "Mannerheimintie 13"
  }
}
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/events/1
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Event not found"
}
```

</details>

<details>
  <summary><strong>Create New Event</strong></summary>

**Endpoint:** `POST /api/events`

**Access Control** `ADMIN`, `SELLER`

### Request Body

Content-Type: `application/json`

Provide the event fields to create. Venue must reference an existing venue id.

```json
{
  "title": "Spring Music Festival",
  "description": "Live event in city center",
  "startTime": "2026-05-15T18:00:00",
  "endTime": "2026-05-15T22:00:00",
  "venue": {
    "venueId": 1
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 201  | Event created successfully        |
| 400  | Invalid input or validation error |
| 404  | Venue not found                   |
| 500  | Internal server error             |

### Response Body (201 Created)

Content-Type: `application/json`

```json
{
  "eventId": 1,
  "title": "Spring Music Festival",
  "description": "Live event in city center",
  "startTime": "2026-05-15T18:00:00",
  "endTime": "2026-05-15T22:00:00",
  "venue": {
    "venueId": 1,
    "name": "Helsinki Arena",
    "address": "Mannerheimintie 13"
  }
}
```

### Example Request

```bash
curl -X POST http://localhost:8080/api/events \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Spring Music Festival",
       "description": "Live event in city center",
       "startTime": "2026-05-15T18:00:00",
       "endTime": "2026-05-15T22:00:00",
       "venue": { "venueId": 1 }
     }'
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Venue id is required"
}
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Venue not found"
}
```

</details>

<details>
  <summary><strong>Update Event By ID</strong></summary>

**Endpoint:** `PUT /api/events/{id}`

**Access Control** `ADMIN`, `SELLER`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

### Request Body

Content-Type: `application/json`

Provide the event fields to update. Venue must reference an existing venue id.

```json
{
  "title": "Updated Spring Festival",
  "description": "Updated event details",
  "startTime": "2026-05-15T19:00:00",
  "endTime": "2026-05-15T23:00:00",
  "venue": {
    "venueId": 2
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 200  | Event updated successfully        |
| 400  | Invalid input or validation error |
| 404  | Event or venue not found          |
| 500  | Internal server error             |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "eventId": 1,
  "title": "Updated Spring Festival",
  "description": "Updated event details",
  "startTime": "2026-05-15T19:00:00",
  "endTime": "2026-05-15T23:00:00",
  "venue": {
    "venueId": 2,
    "name": "Espoo Arena",
    "address": "Länsiväylä 20"
  }
}
```

### Example Request

```bash
curl -X PUT http://localhost:8080/api/events/1 \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Updated Spring Festival",
       "description": "Updated event details",
       "startTime": "2026-05-15T19:00:00",
       "endTime": "2026-05-15T23:00:00",
       "venue": { "venueId": 2 }
     }'
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Event not found"
}
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Venue is required"
}
```

</details>

<details>
  <summary><strong>Delete Event By ID</strong></summary>

**Endpoint:** `DELETE /api/events/{id}`

**Access Control** `ADMIN`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

### Response Codes

| Code | Description                |
| ---- | -------------------------- |
| 204  | Event deleted successfully |
| 404  | Event not found            |
| 500  | Internal server error      |

### Response Body (204 No Content)

No body returned on successful deletion.

### Example Request

```bash
curl -X DELETE http://localhost:8080/api/events/1
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Event not found"
}
```

</details>
