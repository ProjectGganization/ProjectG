## Inspect API
Base URL: `/api/inspect`

**Permissions**

| Endpoint                 | Method | Required Role               |
| ------------------------ | ------ | --------------------------- |
| `/api/inspect/{qrCode}`  | GET    | `ADMIN`, `SELLER`           |

<details>
  <summary><strong>Get Issued Ticket By QR code</strong></summary>

**Endpoint:** `GET /api/inspect/{qrCode}`

### Path Parameters
| Parameter | Type    | Required | Description                         |
|-----------|---------|----------|------------------------------------ |
| `qrCode`  | String  | Yes      | The unique QR code of issued ticket |

### Response Codes
| Code | Description                      |
|------| ---------------------------------|
| 200  | Issued ticket found with QR Code |
| 404  | Issued ticket not found with QR code |
| 500  | Internal server error            |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "issuedTicketId": 1,
  "order": {
    "orderId": 1
  },
  "ticket": {
    "ticketId": 1
  },
  "qrCode": "ABC123XYZ"
}

```

### Example Request
```bash
curl -X GET http://localhost:8080/api/inspect/ABC123XYZ
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Issued ticket not found with QR code"
}
```
</details>