## Ticket API
Base URL: `/api/tickets`

**Permissions**

| Endpoint            | Method | Required Role     |
| ------------------- | ------ | ----------------- |
| `/api/tickets`      | GET    | `ANYONE`          |
| `/api/tickets/{id}` | GET    | `ANYONE`          |
| `/api/tickets`      | POST   | `ADMIN`, `SELLER` |
| `/api/tickets/{id}` | PUT    | `ADMIN`, `SELLER` |
| `/api/tickets/{id}` | DELETE | `ADMIN`           |

<details>
  <summary><strong>Get All Tickets</strong></summary>

**Endpoint:** `GET /api/tickets`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All tickets found successfully  |
| 404  | No tickets found                |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
[
{
  "ticketId": 1,
  "ticketType": {
    "ticketType": "string"
  },
  "unitPrice": 10,
  "inStock": 1,
  "orderLimit": 1
}
]
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/tickets
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No tickets found"
}
```
</details>

<details>
  <summary><strong>Get Ticket By ID </strong></summary>

**Endpoint:** `GET /api/tickets/{id}`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique ticket ID |

### Response Codes
| Code | Description                     |
|------| --------------------------------|
| 200  | Ticket found successfully      |
| 404  | Ticket not found               |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "example_id": 1,
  "title": "Spring Music Festival",
  "description": "Live event in city center",
  "date": "2026-03-15T19:00:00",
  "example_status": {
    "id": 1,
    "status": "Active"
  }
}
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/tickets/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Ticket not found"
}
```
</details>

<details>
  <summary><strong>Create New Ticket</strong></summary>

**Endpoint:** `POST /api/tickets`

**Access Control** `ADMIN`, `SELLER`

### Request Body
Content-Type: `application/json`

Provide the ticket fields to create.

```json
{
  "ticketType": {
    "ticketType": "string"
  },
  "unitPrice": 10,
  "inStock": 1,
  "orderLimit": 1
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 201  | Ticket created successfully       |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)
Content-Type: `application/json`

```json
{
  "ticketId": 1,
  "ticketType": {
    "ticketType": "string"
  },
  "unitPrice": 10,
  "inStock": 1,
  "orderLimit": 1
}
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/tickets \
     -H "Content-Type: application/json" \
     -d '{
     "unitPrice": 12,
     "inStock": 107,
     "orderLimit": 107
     }'
```

### Example Response (400 Bad Request)
```json
{
  "status": 400,
  "error": "Invalid input data: "
}
```

</details>

<details>
  <summary><strong>Update Ticket by ID </strong></summary>

**Endpoint:** `PUT /api/tickets/{id}`

**Access Control** `ADMIN`, `SELLER`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique ticket ID |

### Request Body
Content-Type: `application/json`

Provide the example fields to update.

```json
{
  "title": "Päivitetty nimi",
  "description": "Päivi sano moi",
  "date": "2026-01-10T18:12:02",
  "example_status": 1
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 200  | Ticket updated successfully      |
| 400  | Invalid input or validation error |
| 404  | Ticket not found                 |
| 500  | Internal server error             |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "example_id": 1,
  "title": "Päivitetty nimi",
  "description": "Päivi sano moi",
  "date": "2026-01-10T18:11:01",
  "example_status": {
    "id": 1,
    "status": "Active"
  }
}
```

### Example Request
```bash
curl -X PUT http://localhost:8080/api/tickets/1 \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Päivitetty nimi",
       "description": "Päivi sano moi",
       "date": "2026-01-10T18:11:01",
       "example_status": 1
     }'
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Ticket not found"
}
```

### Example Response (400 Bad Request)
```json
{
  "status": 400,
  "error": "Invalid input data: "
}
```

</details>

<details>
  <summary><strong>Delete Ticket By ID</strong></summary>

**Endpoint:** `DELETE /api/tickets/{id}`

**Access Control** `ADMIN`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique ticket ID |


### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 204  | Ticket deleted successfully    |
| 404  | Ticket not found               |
| 500  | Internal server error           |

### Response Body (204 No Content)
No body returned on successful deletion.

### Example Request
```bash
curl -X DELETE http://localhost:8080/api/tickets/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Ticket not found"
}
```
</details>