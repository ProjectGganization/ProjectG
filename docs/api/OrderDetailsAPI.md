## Order Details API
Base URL: `/api/orderdetails`

**Permissions**

| Endpoint                  | Method | Required Role     |
| ------------------------- | ------ | ----------------- |
| `/api/orderdetails`       | GET    | `ANYONE`          |
| `/api/orderdetails/{id}`  | GET    | `ANYONE`          |
| `/api/orderdetails`       | POST   | `ADMIN`, `SELLER` |
| `/api/orderdetails/{id}`  | PUT    | `ADMIN`, `SELLER` |
| `/api/orderdetails/{id}`  | DELETE | `ADMIN`           |

<details>
  <summary><strong>Get All Order Details</strong></summary>

**Endpoint:** `GET /api/orderdetails`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All order details found successfully |
| 404  | No order details found               |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
[
  {
    "id": 1,
    "orderId": 1001,
    "productId": 501,
    "quantity": 2,
    "price": 49.99
  }
]
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/orderdetails
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No order details found"
}
```
</details>

<!-- 2. Get Example By ID -->
<details>
  <summary><strong>Get Order Details By Id </strong></summary>

**Endpoint:** `GET /api/orderdetails/{id}`

**Access Control** `ADMIN`, `SELLER`, `CUSTOMER`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique order detail example ID |

### Response Codes
| Code | Description                     |
|------| --------------------------------|
| 200  | Order details found successfully     |
| 404  | Order details not found             |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "id": 1,
  "orderId": 1001,
  "productId": 501,
  "quantity": 2,
  "price": 49.99
}

```

### Example Request
```bash
curl -X GET http://localhost:8080/api/orderdetails/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Order details not found"
}
```
</details>

<!-- 3. Create New Order Details -->
<details>
  <summary><strong>Create New Order Details</strong></summary>

**Endpoint:** `POST /api/orderdetails`

**Access Control** `ADMIN`, `SELLER`

### Request Body
Content-Type: `application/json`

Provide the order details fields to create.

```json
  {
    "orderId": 1001,
    "productId": 501,
    "quantity": 2,
    "price": 49.99
  }
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 201  | Order Details created successfully      |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)
Content-Type: `application/json`

```json
  {
    "id": 1,
    "orderId": 1001,
    "productId": 501,
    "quantity": 2,
    "price": 49.99
  }
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/orderdetails \
     -H "Content-Type: application/json" \
     -d '{
        "orderId": 1001,
        "productId": 501,
        "quantity": 2,
        "price": 49.99
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

<!-- 4. Update Example By ID -->
<details>
  <summary><strong>Update Order Details </strong></summary>

**Endpoint:** `PUT /api/orderdetails/{id}`

**Access Control** `ADMIN`, `SELLER`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique order detail ID |

### Request Body
Content-Type: `application/json`

Provide the example fields to update.

```json
{
  "orderId": 1001,
  "productId": 501,
  "quantity": 3,
  "price": 49.99
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 200  | Order details updated successfully     |
| 400  | Invalid input |
| 404  | Order details not found                |
| 500  | Internal server error             |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "id": 1,
  "orderId": 1001,
  "productId": 501,
  "quantity": 3,
  "price": 49.99
}

```

### Example Request
```bash
curl -X PUT http://localhost:8080/api/orderdetails/1 \
-H "Content-Type: application/json" \
-d '{
  "orderId": 1001,
  "productId": 501,
  "quantity": 3,
  "price": 49.99
}'
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Oder details not found"
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

<!-- 5. Delete Example By ID -->
<details>
  <summary><strong>Delete Order Details </strong></summary>

**Endpoint:** `DELETE /api/orderdetails/{id}`

**Access Control** `ADMIN`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique order detail ID |


### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 204  | Order details deleted successfully    |
| 404  | Order details not found               |
| 500  | Internal server error           |

### Response Body (204 No Content)
No body returned on successful deletion.

### Example Request
```bash
curl -X DELETE http://localhost:8080/api/orderdetails/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Order details not found"
}
```
</details>