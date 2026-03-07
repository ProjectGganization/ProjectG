## Postal Code API
Base URL: `/api/postalcodes`

<details>
  <summary><strong>Get All Postal Codes</strong></summary>

**Endpoint:** `GET /api/postalcodes`

### Response Codes

| Code | Description                         |
| ---- | ----------------------------------- |
| 200  | All postal codes found successfully |
| 404  | No postal codes found               |
| 500  | Internal server error               |

### Response Body (200 OK)

Content-Type: `application/json`

```json
[
  {
    "postalCode": "00100",
    "city": "Helsinki"
  }
]
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/postalcodes
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "No postal codes found"
}
```

</details>

<details>
  <summary><strong>Get Postal Code By ID</strong></summary>

**Endpoint:** `GET /api/postalcodes/{postalCode}`

### Path Parameters

| Parameter    | Type   | Required | Description               |
| ------------ | ------ | -------- | ------------------------- |
| `postalCode` | String | Yes      | The postal code (5 chars) |

### Response Codes

| Code | Description                    |
| ---- | ------------------------------ |
| 200  | Postal code found successfully |
| 404  | Postal code not found          |
| 500  | Internal server error          |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "postalCode": "00100",
  "city": "Helsinki"
}
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/postalcodes/00100
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
  <summary><strong>Create New Postal Code</strong></summary>

**Endpoint:** `POST /api/postalcodes`

### Request Body

Content-Type: `application/json`

Provide the postal code fields to create.

```json
{
  "postalCode": "00100",
  "city": "Helsinki"
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 201  | Postal code created successfully  |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)

Content-Type: `application/json`

```json
{
  "postalCode": "00100",
  "city": "Helsinki"
}
```

### Example Request

```bash
curl -X POST http://localhost:8080/api/postalcodes \
     -H "Content-Type: application/json" \
     -d '{
       "postalCode": "00100",
       "city": "Helsinki"
     }'
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Invalid postal code data: ..."
}
```

</details>

<details>
  <summary><strong>Update Postal Code By ID</strong></summary>

**Endpoint:** `PUT /api/postalcodes/{postalCode}`

### Path Parameters

| Parameter    | Type   | Required | Description               |
| ------------ | ------ | -------- | ------------------------- |
| `postalCode` | String | Yes      | The postal code (5 chars) |

### Request Body

Content-Type: `application/json`

Provide the city to update.

```json
{
  "city": "Espoo"
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 200  | Postal code updated successfully  |
| 400  | Invalid input or validation error |
| 404  | Postal code not found             |
| 500  | Internal server error             |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "postalCode": "00100",
  "city": "Espoo"
}
```

### Example Request

```bash
curl -X PUT http://localhost:8080/api/postalcodes/00100 \
     -H "Content-Type: application/json" \
     -d '{
       "city": "Espoo"
     }'
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Postal code not found"
}
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Invalid postal code data: ..."
}
```

</details>

<details>
  <summary><strong>Delete Postal Code By ID</strong></summary>

**Endpoint:** `DELETE /api/postalcodes/{postalCode}`

### Path Parameters

| Parameter    | Type   | Required | Description               |
| ------------ | ------ | -------- | ------------------------- |
| `postalCode` | String | Yes      | The postal code (5 chars) |

### Response Codes

| Code | Description                                 |
| ---- | ------------------------------------------- |
| 204  | Postal code deleted successfully            |
| 400  | Postal code is in use and cannot be deleted |
| 404  | Postal code not found                       |
| 500  | Internal server error                       |

### Response Body (204 No Content)

No body returned on successful deletion.

### Example Request

```bash
curl -X DELETE http://localhost:8080/api/postalcodes/00100
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Postal code not found"
}
```

</details>
