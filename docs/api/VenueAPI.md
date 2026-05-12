## Venue API
Base URL: `/api/venues`

### Admin UI — Venues Page

The admin page (`VenuesPage.tsx`) allows admins to list, create, and update venues.

**Soft delete** — the delete button in the UI does **not** call `DELETE /api/venues/{id}`. Instead it marks the row as deleted visually (red background, strikethrough, "Poistettu" badge). The change is UI-only and is not persisted. The delete can be undone in the same session with the undo button. The `DELETE` endpoint remains available for direct API use.

**Postal code auto-creation** — when creating or updating a venue, if the entered postal code does not exist in the database, the admin page automatically calls `POST /api/postalcodes` to create it before saving the venue.

**Permissions**

| Endpoint           | Method | Required Role     |
| ------------------ | ------ | ----------------- |
| `/api/venues`      | GET    | `ANYONE`          |
| `/api/venues/{id}` | GET    | `ANYONE`          |
| `/api/venues`      | POST   | `ADMIN`           |
| `/api/venues/{id}` | PUT    | `ADMIN`           |
| `/api/venues/{id}` | DELETE | `ADMIN`           |

<details>
  <summary><strong>Get All Venues</strong></summary>

**Endpoint:** `GET /api/venues`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Response Codes

| Code | Description                   |
| ---- | ----------------------------- |
| 200  | All venues found successfully |
| 404  | No venues found               |
| 500  | Internal server error         |

### Response Body (200 OK)

Content-Type: `application/json`

```json
[
  {
    "venueId": 1,
    "name": "Helsinki Arena",
    "address": "Mannerheimintie 13",
    "postalCode": {
      "postalCode": "00100",
      "city": "Helsinki"
    }
  }
]
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/venues
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "No venues found"
}
```

</details>

<details>
  <summary><strong>Get Venue By ID</strong></summary>

**Endpoint:** `GET /api/venues/{id}`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique venue ID |

### Response Codes

| Code | Description              |
| ---- | ------------------------ |
| 200  | Venue found successfully |
| 404  | Venue not found          |
| 500  | Internal server error    |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "venueId": 1,
  "name": "Helsinki Arena",
  "address": "Mannerheimintie 13",
  "postalCode": {
    "postalCode": "00100",
    "city": "Helsinki"
  }
}
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/venues/1
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
  <summary><strong>Create New Venue</strong></summary>

**Endpoint:** `POST /api/venues`

**Access Control** `ADMIN`

### Request Body

Content-Type: `application/json`

Provide the venue fields to create.

```json
{
  "name": "Helsinki Arena",
  "address": "Mannerheimintie 13",
  "postalCode": {
    "postalCode": "00100"
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 201  | Venue created successfully        |
| 400  | Invalid input or validation error |
| 404  | Postal code not found             |
| 500  | Internal server error             |

### Response Body (201 Created)

Content-Type: `application/json`

```json
{
  "venueId": 1,
  "name": "Helsinki Arena",
  "address": "Mannerheimintie 13",
  "postalCode": {
    "postalCode": "00100",
    "city": "Helsinki"
  }
}
```

### Example Request

```bash
curl -X POST http://localhost:8080/api/venues \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Helsinki Arena",
       "address": "Mannerheimintie 13",
       "postalCode": { "postalCode": "00100" }
     }'
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Postal code is required when postalCode object is provided"
}
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Postal code not found"
}
```

</details>

<details>
  <summary><strong>Update Venue By ID</strong></summary>

**Endpoint:** `PUT /api/venues/{id}`

**Access Control** `ADMIN`

> **Fix:** Null checks for `name`, `address`, and `postalCode` were inverted in a previous version — all three fields are now correctly required and validated.

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique venue ID |

### Request Body

Content-Type: `application/json`

Provide the venue fields to update.

```json
{
  "name": "Päivitetty Arena",
  "address": "Mannerheimintie 15",
  "postalCode": {
    "postalCode": "00200"
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 200  | Venue updated successfully        |
| 400  | Invalid input or validation error |
| 404  | Venue or postal code not found    |
| 500  | Internal server error             |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "venueId": 1,
  "name": "Päivitetty Arena",
  "address": "Mannerheimintie 15",
  "postalCode": {
    "postalCode": "00200",
    "city": "Espoo"
  }
}
```

### Example Request

```bash
curl -X PUT http://localhost:8080/api/venues/1 \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Päivitetty Arena",
       "address": "Mannerheimintie 15",
       "postalCode": { "postalCode": "00200" }
     }'
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Venue not found"
}
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Invalid venue data: ..."
}
```

</details>

<details>
  <summary><strong>Delete Venue By ID</strong></summary>

**Endpoint:** `DELETE /api/venues/{id}`

**Access Control** `ADMIN`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique venue ID |

### Response Codes

| Code | Description                |
| ---- | -------------------------- |
| 204  | Venue deleted successfully |
| 404  | Venue not found            |
| 500  | Internal server error      |

### Response Body (204 No Content)

No body returned on successful deletion.

### Example Request

```bash
curl -X DELETE http://localhost:8080/api/venues/1
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Venue not found"
}
```

</details>
