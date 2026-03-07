## Venue API
Base URL: `/api/venues`

<details>
  <summary><strong>Get All Venues</strong></summary>

**Endpoint:** `GET /api/venues`

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
