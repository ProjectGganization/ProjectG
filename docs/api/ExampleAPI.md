<!-- Step 1: Luo uusi md-tiedosto kansioon nimeltä docs/api nimeämällä tiedosti oman APIn mukaan esim. ExampleAPI.md -->

<!-- Step 2: Kirjoita koko tiedostolle otsikko seuraavasti ja kerro base URL -->
## Example API
Base URL: `/api/examples`

<!-- Step 3: Alla olevasta Example API rungosta voi poimia itselleen sopivat mallit -->

HUOM! Tehdään jokaisen APIn dokumentaatio seuraavassa järjestyksesä.
1. Get All Examples
2. Get Example By ID
3. Create New Example
4. Update Example By ID
5. Delete Example By ID

<!-- 1. Get All Examples -->
<details>
  <summary><strong>Get All Examples</strong></summary>

**Endpoint:** `GET /api/examples`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All examples found successfully |
| 404  | No examples found               |
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
curl -X GET http://localhost:8080/api/examples
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No examples found"
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

<!-- 3. Create New Example -->
<details>
  <summary><strong>Create New Example</strong></summary>

**Endpoint:** `POST /api/examples`

### Request Body
Content-Type: `application/json`

Provide the example fields to create.

```json
{
  "title": "Esimerkillinen nimi",
  "description": "Hauska esimerkki",
  "date": "2026-01-10T18:12:02",
  "example_status": 2
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 201  | Example created successfully      |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)
Content-Type: `application/json`

```json
{
  "example_id": 1,
  "title": "Esimerkillinen nimi",
  "description": "Hauska esimerkki",
  "date": "2026-01-10T18:12:02",
  "example_status": {
    "id": 2,
    "status": "Inactive"
  }
}
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/examples \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Esimerkillinen nimi",
       "description": "Hauska esimerkki",
       "date": "2026-01-10T18:12:02",
       "example_status": 2
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