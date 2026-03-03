## Order API
Base URL: `/api/orders`

1. Get All Orders
2. Get Order By ID
3. Create New Order

<details>
  <summary><strong>Get All Orders</strong></summary>

**Endpoint:** `GET /api/orders`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All orders found successfully   |
| 404  | No orders found                 |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
[
  {
    "orderId": 1,
    "customer": {
      "customerId": 3
    },
    "date": "2026-03-01T18:48:20.008Z",
    "seller": {
      "sellerId": 101
    },
    "isRefunded": true,
    "isPaid": true,
    "paymentMethod": {
      "paymentMethod": "string"
    }
  }
]
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/orders
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No orders found"
}
```
</details>

<details>
  <summary><strong>Get Order By ID</strong></summary>

**Endpoint:** `GET /api/orders/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique order ID   |

### Response Codes
| Code | Description                     |
|------| --------------------------------|
| 200  | Order found successfully        |
| 404  | Order not found                 |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "orderId": 1,
  "customer": {
    "customerId": 3
  },
  "date": "2026-03-01T18:48:20.008Z",
  "seller": {
    "sellerId": 101
  },
  "isRefunded": true,
  "isPaid": true,
  "paymentMethod": {
    "paymentMethod": "string"
  }
}
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/orders/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Order not found"
}
```
</details>

<details>
  <summary><strong>Create New Order</strong></summary>

**Endpoint:** `POST /api/orders`

### Request Body
Content-Type: `application/json`

Provide the order fields to create.

```json
{
  "customerId": 3,
  "sellerId": 2,
  "date": "2026-03-01T18:48:20.008Z",
  "isRefunded": false,
  "isPaid": true,
  "paymentMethod": "bank"
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 201  | Order created successfully        |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)
Content-Type: `application/json`

```json
{
  "orderId": 1,
  "customer": {
    "customerId": 3
  },
  "date": "2026-03-01T18:48:20.008Z",
  "seller": {
    "sellerId": 101
  },
  "isRefunded": true,
  "isPaid": true,
  "paymentMethod": {
    "paymentMethod": "string"
  }
}
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/orders \
     -H "Content-Type: application/json" \
     -d '{
       "customerId": 3,
       "sellerId": 2,
       "isRefunded": false,
       "isPaid": true,
       "paymentMethod": "bank"
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