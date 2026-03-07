## IssuedTicket API
Base URL: `/api/issuedtickets`

<details>
  <summary><strong>Get All Issued Tickets</strong></summary>

**Endpoint:** `GET /api/issuedtickets`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All issued tickets found successfully |
| 404  | No issued tickets found         |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "issuedTicketId": 1,
  "order": { "orderId": 42 },
  "qrCode": "ABC123DEF456",
  "ticket": { "ticketId": 7 }
}
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/issuedtickets
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No issued tickets found"
}
```
</details>

<details>
  <summary><strong>Get Issued Ticket By ID</strong></summary>

**Endpoint:** `GET /api/issuedtickets/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique issued ticket ID |

### Response Codes
| Code | Description                     |
|------| --------------------------------|
| 200  | Issued ticket found successfully|
| 404  | Issued ticket not found         |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "issuedTicketId": 1,
  "order": { "orderId": 42 },
  "qrCode": "ABC123DEF456",
  "ticket": { "ticketId": 7 }
}
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/issuedtickets/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Issued ticket not found"
}
```
</details>

<details>
  <summary><strong>Create New Issued Ticket</strong></summary>

**Endpoint:** `POST /api/issuedtickets`

### Request Body
Content-Type: `application/json`

```json
{
  "order": { "orderId": 42 },
  "qrCode": "ABC123DEF456",
  "ticket": { "ticketId": 7 }
}
```

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 201  | Issued ticket created successfully |
| 400  | Invalid issued ticket data      |
| 500  | Internal server error           |

### Response Body (201 Created)
Content-Type: `application/json`

```json
{
  "issuedTicketId": 1,
  "order": { "orderId": 42 },
  "qrCode": "ABC123DEF456",
  "ticket": { "ticketId": 7 }
}
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/issuedtickets \
  -H "Content-Type: application/json" \
  -d '{"order": {"orderId": 42}, "qrCode": "ABC123DEF456", "ticket": {"ticketId": 7}}'
```

### Example Response (400 Bad Request)
```json
{
  "status": 400,
  "error": "Invalid issued ticket data: ..."
}
```

</details>

<details>
  <summary><strong>Update Issued Ticket</strong></summary>

**Endpoint:** `PUT /api/issuedtickets/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique issued ticket ID |

### Request Body
Content-Type: `application/json`

```json
{
  "order": { "orderId": 42 },
  "qrCode": "ABC123DEF456",
  "ticket": { "ticketId": 7 }
}
```

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | Issued ticket updated successfully |
| 400  | Invalid issued ticket data      |
| 404  | Issued ticket not found         |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "issuedTicketId": 1,
  "order": { "orderId": 42 },
  "qrCode": "ABC123DEF456",
  "ticket": { "ticketId": 7 }
}
```

### Example Request
```bash
curl -X PUT http://localhost:8080/api/issuedtickets/1 \
  -H "Content-Type: application/json" \
  -d '{"order": {"orderId": 42}, "qrCode": "ABC123DEF456", "ticket": {"ticketId": 7}}'
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "IssuedTicket not found"
}
```

</details>

<details>
  <summary><strong>Delete Issued Ticket</strong></summary>

**Endpoint:** `DELETE /api/issuedtickets/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique issued ticket ID |

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 204  | Issued ticket deleted successfully |
| 404  | Issued ticket not found         |
| 500  | Internal server error           |

### Response Body (204 No Content)
No content returned on successful deletion.

### Example Request
```bash
curl -X DELETE http://localhost:8080/api/issuedtickets/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "IssuedTicket not found"
}
```

</details>