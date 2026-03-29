## Order API
Base URL: `/api/orders`

**Permissions**

| Endpoint           | Method | Required Role     |
| ------------------ | ------ | ----------------- |
| `/api/orders`      | GET    | `ANYONE`          |
| `/api/orders/{id}` | GET    | `ANYONE`          |
| `/api/orders`      | POST   | `ANYONE`          |
| `/api/orders/{id}` | PUT    | `ADMIN`, `SELLER` |
| `/api/orders/{id}` | DELETE | `ADMIN`           |

<details>
  <summary><strong>Get All Orders</strong></summary>

**Endpoint:** `GET /api/orders`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

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

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

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

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

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

<details>
  <summary><strong>Update Order By ID</strong></summary>

**Endpoint:** `PUT /api/orders/{id}`

**Access Control** `ADMIN`, `SELLER`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique order ID   |

### Request Body
Content-Type: `application/json`

Provide the order fields to update.

```json
{
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

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 200  | Order updated successfully        |
| 400  | Invalid input or validation error |
| 404  | Order not found                   |
| 500  | Internal server error             |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "orderId": 1,
  "customer": {
    "customerId": 3,
    "firstname": "Jane",
    "lastname": "Doe"
  },
  "date": "2026-03-01T18:48:20.008Z",
  "seller": {
    "sellerId": 101,
    "name": "EventSeller Oy"
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
curl -X PUT http://localhost:8080/api/orders/1 \
     -H "Content-Type: application/json" \
     -d '{
       "customer": { "customerId": 3 },
       "date": "2026-03-01T18:48:20.008Z",
       "seller": { "sellerId": 101 },
       "isRefunded": true,
       "isPaid": true,
       "paymentMethod": { "paymentMethod": "string" }
     }'

```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Order not found"
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
  <summary><strong>Delete Order By ID</strong></summary>

**Endpoint:** `DELETE /api/orders/{id}`

**Access Control** `ADMIN`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique order ID   |


### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 204  | Order deleted successfully      |
| 404  | Order not found                 |
| 500  | Internal server error           |

### Response Body (204 No Content)
No body returned on successful deletion.

### Example Request
```bash
curl -X DELETE http://localhost:8080/api/orders/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Order not found"
}
```
</details>