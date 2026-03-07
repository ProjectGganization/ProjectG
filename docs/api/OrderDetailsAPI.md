## Order Details API
Base URL: `/api/orderdetails`

1. Get All Order Details
2. Get 
3. Create New Order Details
4. Update 
5. Delete 

<details>
  <summary><strong>Get All Order Details</strong></summary>

**Endpoint:** `GET /api/orderdetails`

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
  <summary><strong>Get Example By ID</strong></summary>

**Endpoint:** `GET /api/examples/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique example ID |

### Response Codes
| Code | Description                     |
|------| --------------------------------|
| 200  | Example found successfully      |
| 404  | Example not found               |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "example_id": 1,
  "title": "Example name",
  "description": "Example description",
  "date": "2026-03-15T19:00:00",
  "example_status": {
    "id": 1,
    "status": "Active"
  }
}
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/examples/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Example not found"
}
```
</details>

<!-- 3. Create New Order Details -->
<details>
  <summary><strong>Create New Order Details</strong></summary>

**Endpoint:** `POST /api/orderdetails`

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
  <summary><strong>Update Example By ID</strong></summary>

**Endpoint:** `PUT /api/examples/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique example ID |

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
| 200  | Example updated successfully      |
| 400  | Invalid input or validation error |
| 404  | Example not found                 |
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
curl -X PUT http://localhost:8080/api/examples/1 \
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
  "error": "Example not found"
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
  <summary><strong>Delete Example By ID</strong></summary>

**Endpoint:** `DELETE /api/examples/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique example ID |


### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 204  | Example deleted successfully    |
| 404  | Example not found               |
| 500  | Internal server error           |

### Response Body (204 No Content)
No body returned on successful deletion.

### Example Request
```bash
curl -X DELETE http://localhost:8080/api/examples/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Example not found"
}
```
</details>