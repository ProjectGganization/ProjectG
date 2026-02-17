# API Documentation

## Events API

Base URL: `/api/events`

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get all Events
</span>
</summary>

Get all events.

**Endpoint:** `GET /api/events`

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | All events found successfully |
| 404  | No events found |
| 500  | Internal server error |

**Response Body (200 OK):**

Content-Type: `application/json`

``` json
{
  "event_id": 1,
  "title": "Event name",
  "description": "Event description",
  "start_time": "2026-03-15T19:00:00",
  "end_time": "2026-03-15T23:00:00",
  "venue": {
    "venue_id": 1,
    "name": "Venue name"
  },
  "event_status": {
    "id": 1,
    "status": "Active"
  }
}
```
**Example Request:**

``` bash
curl -X GET http://localhost:8080/api/events
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "No events found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get Event by ID
</span>
</summary>

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
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Delete Event by ID
</span>
</summary>

Delete a single event by its unique identifier.

**Endpoint:** `DELETE /api/events/{id}`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

**Response Codes:**

| Code | Description                    |
| ---- | ------------------------------ |
| 204  | Event deleted successfully     |
| 404  | Event not found                |
| 500  | Internal server error          |

**Response Body (204 No Content):**

No body returned on successful deletion.

**Example Request:**

```bash
curl -X DELETE http://localhost:8080/api/events/1
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "Event not found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Create New Event
</span>
</summary>

Add a new event. This endpoint is used by administrators to manage upcoming events.

**Endpoint:** `POST /api/events`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `title`      | String | Yes    | The name of the event |
| `description`| String | No     | Detailed information about the event |
| `start_time` | String (ISO) | Yes | Event start date and time |
| `end_time` | String (ISO) | No | Event end date and time |
| `venue_id` | Integer | Yes | ID of the venue where the event is held |
| `category` | Integer | Yes | ID of the event caregory |  

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 201  | Event created successfully |
| 400  | Invalid input or validation error |
| 500  | Internal server error    |

**Response Body (201 Created):**

Content-Type: `application/json`

``` json
{
  "event_id": 1,
  "title": "Event name",
  "description": "Event description",
  "start_time": "2026-03-15T19:00:00",
  "end_time": "2026-03-15T23:00:00",
  "venue": {
    "venue_id": 1,
    "name": "Venue name"
  },
  "event_status": {
    "id": 1,
    "status": "Active"
  }
}
```
**Example Request:**

``` bash
curl -X POST http://localhost:8080/api/events \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Event name",
       "description": "Event description",
       "start_time": "2026-07-15T18:00:00",
       "end_time": "2026-03-15T23:00:00",
       "venue_id": 1
     }'
```

**Example Response (404 Not Found):**

```json
{
  "status": 400,
  "error": "Invalid event data: "
}
```
</details>

---