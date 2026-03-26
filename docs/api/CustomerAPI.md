## Customer API
Base URL: `/api/customers`

<details>
  <summary><strong>Get All Customers</strong></summary>

**Endpoint:** `GET /api/customers`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All customers found successfully |
| 404  | No customers found               |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
[
{
  "customerId": 1,
  "firstname": "John",
  "email": "john.doe@example.com",
  "phone": "+358401234567"
}
]
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/customers
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No customers found"
}
```
</details>

<details>
  <summary><strong>Get Customer By ID</strong></summary>

**Endpoint:** `GET /api/customers/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique customer ID |

### Response Codes
| Code | Description                     |
|------| --------------------------------|
| 200  | Customer found successfully      |
| 404  | Customer not found               |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "customerId": 1,
  "firstname": "John",
  "email": "john.doe@example.com",
  "phone": "+358401234567"
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
  "error": "Customer not found"
}
```
</details>

<details>
  <summary><strong>Create New Customer</strong></summary>

**Endpoint:** `POST /api/customers`

### Request Body
Content-Type: `application/json`

Provide the customer fields to create.

```json
{
    "firstname": "Matti",
    "lastname": "Mallikas",
    "email": "matti@hotmail.com",
    "phone": "001"
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 201  | Customer created successfully      |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)
Content-Type: `application/json`

```json
{
    "customerId": 1,
    "firstname": "Matti",
    "lastname": "Mallikas",
    "email": "matti@hotmail.com",
    "phone": "001"
}
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/customers \
     -H "Content-Type: application/json" \
     -d '{
        "firstname": "Matti",
        "lastname": "Mallikas",
        "email": "matti@hotmail.com",
        "phone": "001"
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
  <summary><strong>Update Customer By ID</strong></summary>

**Endpoint:** `PUT /api/customers/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique customer ID |

### Request Body
Content-Type: `application/json`

Provide the customer fields to update.

```json
{
    "firstname": "Matti",
    "lastname": "Mallikas",
    "email": "matti@hotmail.com",
    "phone": "001"
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 200  | Customer updated successfully      |
| 400  | Invalid input or validation error |
| 404  | Customer not found                 |
| 500  | Internal server error             |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "customerId": 1,
  "firstname": "John",
  "lastname": "Mallikas",
  "email": "john.mallikas@example.com",
  "phone": "+358401234567"
}
```

### Example Request
```bash
curl -X PUT http://localhost:8080/api/customers/1 \
     -H "Content-Type: application/json" \
     -d '{
        "customerId": 1,
        "firstname": "John",
        "lastname": "Mallikas",
        "email": "john.mallikas@example.com",
        "phone": "+358401234567"
     }'
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Customer not found"
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
  <summary><strong>Delete Customer By ID</strong></summary>

**Endpoint:** `DELETE /api/examples/{id}`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique customer ID |


### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 204  | Customer deleted successfully    |
| 404  | Customer not found               |
| 500  | Internal server error           |

### Response Body (204 No Content)
No body returned on successful deletion.

### Example Request
```bash
curl -X DELETE http://localhost:8080/api/customers/3
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "Customer with id 3 not found"
}
```
</details>