## User API
Base URL: `/api/user`

**Permissions**

| Endpoint           | Method | Required Role               |
| ------------------ | ------ | --------------------------- |
| `/api/user`        | GET    | `ADMIN`                     |
| `/api/user/{id}`   | GET    | `ADMIN`, `SELLER`(Only own) |
| `/api/user`        | POST   | `ADMIN`                     |
| `/api/user/{id}`   | PUT    | `ADMIN`                     |
| `/api/user/{id}`   | DELETE | `ADMIN`                     |

<details>
  <summary><strong>Get All Users</strong></summary>

**Endpoint:** `GET /api/users`

**Access Control** `ADMIN`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All users found successfully |
| 404  | No users found               |

### Response Body (200 OK)
Content-Type: `application/json`

```json
[
{
  "userId": 1,
  "email": "matti.meikalainen@esimerkki.com",
  "accountCreated": "2026-03-18",
  "statusName": "Active"
}
]
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/users
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No users found"
}
```
</details>

<details>
  <summary><strong>Get User By ID</strong></summary>

**Endpoint:** `GET /api/users/{id}`

**Access Control** `ADMIN`, `SELLER`(Only own)

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique user ID |

### Response Codes
| Code | Description                     |
|------| --------------------------------|
| 200  | User found successfully      |
| 404  | User not found               |

### Response Body (200 OK)
Content-Type: `application/json`

```json
{
  "userId": 1,
  "email": "matti.meikalainen@esimerkki.com",
  "accountCreated": "2026-03-18",
  "statusName": "Active"
}
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/users/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "User not found"
}
```
</details>

<details>
  <summary><strong>Create New User</strong></summary>

**Endpoint:** `POST /api/users`

**Access Control** `ADMIN`

### Request Body
Content-Type: `application/json`

Provide the example fields to create.

```json
{
 "email": "matti.meikalainen@esimerkki.com",
  "password": "vahvaSalasana1",
  "accountStatus": "Active"
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 201  | User created successfully      |
| 400  | Invalid user data |

### Response Body (201 Created)
Content-Type: `application/json`

```json
{
  "userId": 1,
  "email": "matti.meikalainen@esimerkki.com",
  "accountCreated": "2026-03-18",
  "statusName": "Active"
}
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/users \
     -H "Content-Type: application/json" \
     -d '{
       "email": "matti.meikalainen@esimerkki.com",
       "password": "vahvaSalasana1",
       "accountStatus": "Active",
     }'
```

### Example Response (400 Bad Request)
```json
{
  "status": 400,
  "error": "Invalid user data: "
}
```

</details>

<details>
  <summary><strong>Delete User By ID</strong></summary>

**Endpoint:** `DELETE /api/users/{id}`

**Access Control** `ADMIN`

### Path Parameters
| Parameter | Type    | Required | Description           |
|-----------|---------|----------|-----------------------|
| `id`      | Integer | Yes      | The unique user ID |


### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 204  | User deleted successfully    |
| 404  | User not found               |

### Response Body (204 No Content)
No body returned on successful deletion.

### Example Request
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "User not found"
}
```
</details>