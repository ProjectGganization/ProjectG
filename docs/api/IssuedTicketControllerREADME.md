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
