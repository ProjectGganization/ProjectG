# API Documentation

<!-- Tämä tiedosto rakennettu siten, että se sisältää upotukset docs/api tiedostoihin kutsutun 
!INCLUDE komennon avulla, joka on toteutettu nyt Noden kanssa. -->

## 📑 Table of Contents
- [Example API](#example-api)
- [Event API](#event-api)
- [Venue API](#venue-api)
- [Ticket API](#ticket-api)
- [Issued Ticket API](#issued-ticket-api)
- [Order API](#order-api)
- [Order Details API](#order-details-api)
- [Postal Code](#postal-code-api)

<!-- Step 1: Luo otsikko esim. ## Example API ja lisää siihen upotus seuraavasti: -->
<!-- Step 2: Aja terminaalista komento node build.js -->
<!-- Step 3: Kirjaa otsikko yllä olevaan Table Of Contents -->
## 📝 Example API
<details>
  <summary><strong>Expand Example API</strong></summary>

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
[
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
]
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

</details>

<!-- node build.js -komento päivittää tiedoston nimeltä APIDOCUMENTATION.generated.md, joka copypastaa docs/api tiedostot sinne"
HUOM! Älä ikinä lisää mitään tiedostoon APIDOCUMENTATION.generated.md! -->

<!-- Vinkki vitonen Ctrl+Shift+V voi avulla pystyy previewaa APIDOCUMENTATION.generated.md :) -->

## 🎭 Event API
<details>
  <summary><strong>Expand Event API</strong></summary>

## Event API

Base URL: `/api/events`

1. Get All Events
2. Get Event By ID
3. Create New Event
4. Update Event By ID
5. Delete Event By ID

<details>
  <summary><strong>Get All Events</strong></summary>

**Endpoint:** `GET /api/events`

### Response Codes

| Code | Description                   |
| ---- | ----------------------------- |
| 200  | All events found successfully |
| 404  | No events found               |
| 500  | Internal server error         |

### Response Body (200 OK)

Content-Type: `application/json`

```json
[
  {
    "eventId": 1,
    "title": "Spring Music Festival",
    "description": "Live event in city center",
    "startTime": "2026-05-15T18:00:00",
    "endTime": "2026-05-15T22:00:00",
    "venue": {
      "venueId": 1,
      "name": "Helsinki Arena",
      "address": "Mannerheimintie 13"
    }
  }
]
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/events
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "No events found"
}
```

</details>

<details>
  <summary><strong>Get Event By ID</strong></summary>

**Endpoint:** `GET /api/events/{id}`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

### Response Codes

| Code | Description              |
| ---- | ------------------------ |
| 200  | Event found successfully |
| 404  | Event not found          |
| 500  | Internal server error    |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "eventId": 1,
  "title": "Spring Music Festival",
  "description": "Live event in city center",
  "startTime": "2026-05-15T18:00:00",
  "endTime": "2026-05-15T22:00:00",
  "venue": {
    "venueId": 1,
    "name": "Helsinki Arena",
    "address": "Mannerheimintie 13"
  }
}
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/events/1
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Event not found"
}
```

</details>

<details>
  <summary><strong>Create New Event</strong></summary>

**Endpoint:** `POST /api/events`

### Request Body

Content-Type: `application/json`

Provide the event fields to create. Venue must reference an existing venue id.

```json
{
  "title": "Spring Music Festival",
  "description": "Live event in city center",
  "startTime": "2026-05-15T18:00:00",
  "endTime": "2026-05-15T22:00:00",
  "venue": {
    "venueId": 1
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 201  | Event created successfully        |
| 400  | Invalid input or validation error |
| 404  | Venue not found                   |
| 500  | Internal server error             |

### Response Body (201 Created)

Content-Type: `application/json`

```json
{
  "eventId": 1,
  "title": "Spring Music Festival",
  "description": "Live event in city center",
  "startTime": "2026-05-15T18:00:00",
  "endTime": "2026-05-15T22:00:00",
  "venue": {
    "venueId": 1,
    "name": "Helsinki Arena",
    "address": "Mannerheimintie 13"
  }
}
```

### Example Request

```bash
curl -X POST http://localhost:8080/api/events \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Spring Music Festival",
       "description": "Live event in city center",
       "startTime": "2026-05-15T18:00:00",
       "endTime": "2026-05-15T22:00:00",
       "venue": { "venueId": 1 }
     }'
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Venue id is required"
}
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Venue not found"
}
```

</details>

<details>
  <summary><strong>Update Event By ID</strong></summary>

**Endpoint:** `PUT /api/events/{id}`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

### Request Body

Content-Type: `application/json`

Provide the event fields to update. Venue must reference an existing venue id.

```json
{
  "title": "Updated Spring Festival",
  "description": "Updated event details",
  "startTime": "2026-05-15T19:00:00",
  "endTime": "2026-05-15T23:00:00",
  "venue": {
    "venueId": 2
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 200  | Event updated successfully        |
| 400  | Invalid input or validation error |
| 404  | Event or venue not found          |
| 500  | Internal server error             |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "eventId": 1,
  "title": "Updated Spring Festival",
  "description": "Updated event details",
  "startTime": "2026-05-15T19:00:00",
  "endTime": "2026-05-15T23:00:00",
  "venue": {
    "venueId": 2,
    "name": "Espoo Arena",
    "address": "Länsiväylä 20"
  }
}
```

### Example Request

```bash
curl -X PUT http://localhost:8080/api/events/1 \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Updated Spring Festival",
       "description": "Updated event details",
       "startTime": "2026-05-15T19:00:00",
       "endTime": "2026-05-15T23:00:00",
       "venue": { "venueId": 2 }
     }'
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Event not found"
}
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Venue is required"
}
```

</details>

<details>
  <summary><strong>Delete Event By ID</strong></summary>

**Endpoint:** `DELETE /api/events/{id}`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

### Response Codes

| Code | Description                |
| ---- | -------------------------- |
| 204  | Event deleted successfully |
| 404  | Event not found            |
| 500  | Internal server error      |

### Response Body (204 No Content)

No body returned on successful deletion.

### Example Request

```bash
curl -X DELETE http://localhost:8080/api/events/1
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Event not found"
}
```

</details>


</details>

## 🏟️ Venue API
<details>
  <summary><strong>Expand Venue API</strong></summary>

## Venue API

Base URL: `/api/venues`

1. Get All Venues
2. Get Venue By ID
3. Create New Venue
4. Update Venue By ID
5. Delete Venue By ID

<details>
  <summary><strong>Get All Venues</strong></summary>

**Endpoint:** `GET /api/venues`

### Response Codes

| Code | Description                   |
| ---- | ----------------------------- |
| 200  | All venues found successfully |
| 404  | No venues found               |
| 500  | Internal server error         |

### Response Body (200 OK)

Content-Type: `application/json`

```json
[
  {
    "venueId": 1,
    "name": "Helsinki Arena",
    "address": "Mannerheimintie 13",
    "postalCode": {
      "postalCode": "00100",
      "city": "Helsinki"
    }
  }
]
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/venues
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "No venues found"
}
```

</details>

<details>
  <summary><strong>Get Venue By ID</strong></summary>

**Endpoint:** `GET /api/venues/{id}`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique venue ID |

### Response Codes

| Code | Description              |
| ---- | ------------------------ |
| 200  | Venue found successfully |
| 404  | Venue not found          |
| 500  | Internal server error    |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "venueId": 1,
  "name": "Helsinki Arena",
  "address": "Mannerheimintie 13",
  "postalCode": {
    "postalCode": "00100",
    "city": "Helsinki"
  }
}
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/venues/1
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Venue not found"
}
```

</details>

<details>
  <summary><strong>Create New Venue</strong></summary>

**Endpoint:** `POST /api/venues`

### Request Body

Content-Type: `application/json`

Provide the venue fields to create.

```json
{
  "name": "Helsinki Arena",
  "address": "Mannerheimintie 13",
  "postalCode": {
    "postalCode": "00100"
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 201  | Venue created successfully        |
| 400  | Invalid input or validation error |
| 404  | Postal code not found             |
| 500  | Internal server error             |

### Response Body (201 Created)

Content-Type: `application/json`

```json
{
  "venueId": 1,
  "name": "Helsinki Arena",
  "address": "Mannerheimintie 13",
  "postalCode": {
    "postalCode": "00100",
    "city": "Helsinki"
  }
}
```

### Example Request

```bash
curl -X POST http://localhost:8080/api/venues \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Helsinki Arena",
       "address": "Mannerheimintie 13",
       "postalCode": { "postalCode": "00100" }
     }'
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Postal code is required when postalCode object is provided"
}
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Postal code not found"
}
```

</details>

<details>
  <summary><strong>Update Venue By ID</strong></summary>

**Endpoint:** `PUT /api/venues/{id}`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique venue ID |

### Request Body

Content-Type: `application/json`

Provide the venue fields to update.

```json
{
  "name": "Päivitetty Arena",
  "address": "Mannerheimintie 15",
  "postalCode": {
    "postalCode": "00200"
  }
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 200  | Venue updated successfully        |
| 400  | Invalid input or validation error |
| 404  | Venue or postal code not found    |
| 500  | Internal server error             |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "venueId": 1,
  "name": "Päivitetty Arena",
  "address": "Mannerheimintie 15",
  "postalCode": {
    "postalCode": "00200",
    "city": "Espoo"
  }
}
```

### Example Request

```bash
curl -X PUT http://localhost:8080/api/venues/1 \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Päivitetty Arena",
       "address": "Mannerheimintie 15",
       "postalCode": { "postalCode": "00200" }
     }'
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Venue not found"
}
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Invalid venue data: ..."
}
```

</details>

<details>
  <summary><strong>Delete Venue By ID</strong></summary>

**Endpoint:** `DELETE /api/venues/{id}`

### Path Parameters

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique venue ID |

### Response Codes

| Code | Description                |
| ---- | -------------------------- |
| 204  | Venue deleted successfully |
| 404  | Venue not found            |
| 500  | Internal server error      |

### Response Body (204 No Content)

No body returned on successful deletion.

### Example Request

```bash
curl -X DELETE http://localhost:8080/api/venues/1
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Venue not found"
}
```

</details>


</details>

## 🎫 Ticket API
<details>
  <summary><strong>Expand Ticket API</strong></summary>

## Ticket API
Base URL: `/api/examples`

1. Get All Tickets
2. 
3. Create New Ticket

<details>
  <summary><strong>Get All Tickets</strong></summary>

**Endpoint:** `GET /api/tickets`

### Response Codes
| Code | Description                     |
|------|---------------------------------|
| 200  | All tickets found successfully  |
| 404  | No tickets found                |
| 500  | Internal server error           |

### Response Body (200 OK)
Content-Type: `application/json`

```json
[
{
  "ticketId": 1,
  "ticketType": {
    "ticketType": "string"
  },
  "unitPrice": 10,
  "inStock": 1,
  "orderLimit": 1
}
]
```

### Example Request
```bash
curl -X GET http://localhost:8080/api/tickets
```

### Example Response (404 Not Found)
```json
{
  "status": 404,
  "error": "No tickets found"
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

<!-- 3. Create New Ticket -->
<details>
  <summary><strong>Create New Ticket</strong></summary>

**Endpoint:** `POST /api/tickets`

### Request Body
Content-Type: `application/json`

Provide the ticket fields to create.

```json
{
  "ticketType": {
    "ticketType": "string"
  },
  "unitPrice": 10,
  "inStock": 1,
  "orderLimit": 1
}
```

### Response Codes
| Code | Description                       |
|------|-----------------------------------|
| 201  | Ticket created successfully       |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)
Content-Type: `application/json`

```json
{
  "ticketId": 1,
  "ticketType": {
    "ticketType": "string"
  },
  "unitPrice": 10,
  "inStock": 1,
  "orderLimit": 1
}
```

### Example Request
```bash
curl -X POST http://localhost:8080/api/examples \
     -H "Content-Type: application/json" \
     -d '{
     "unitPrice": 12,
     "inStock": 107,
     "orderLimit": 107
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

</details>

## 🎟️ Issued Ticket API
<details>
  <summary><strong>Expand Issued Ticket API</strong></summary>

## IssuedTicket API

Base URL: `/api/issuedtickets`

1. Get All Issued Tickets
2. Get Issued Ticket By ID
3. Create New Issued Ticket
4. Update Issued Ticket By ID
5. Delete Issued Ticket By ID

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

</details>

## 🛒 Order API
<details>
  <summary><strong>Expand Order API</strong></summary>

## Order API
Base URL: `/api/orders`

1. Get All Orders
2. Get Order By ID
3. Create New Order
4. Update Order By ID
5. Delete Order By ID

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

<details>
  <summary><strong>Update Order By ID</strong></summary>

**Endpoint:** `PUT /api/orders/{id}`

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

</details>

## 📦 Order Details API
<details>
  <summary><strong>Expand Order Details API</strong></summary>

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

</details>

## 🏙️ Postal Code API
<details>
  <summary><strong>Expand Postal Code API</strong></summary>

## Postal Code API

Base URL: `/api/postalcodes`

1. Get All Postal Codes
2. Get Postal Code By ID
3. Create New Postal Code
4. Update Postal Code By ID
5. Delete Postal Code By ID

<details>
  <summary><strong>Get All Postal Codes</strong></summary>

**Endpoint:** `GET /api/postalcodes`

### Response Codes

| Code | Description                         |
| ---- | ----------------------------------- |
| 200  | All postal codes found successfully |
| 404  | No postal codes found               |
| 500  | Internal server error               |

### Response Body (200 OK)

Content-Type: `application/json`

```json
[
  {
    "postalCode": "00100",
    "city": "Helsinki"
  }
]
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/postalcodes
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "No postal codes found"
}
```

</details>

<details>
  <summary><strong>Get Postal Code By ID</strong></summary>

**Endpoint:** `GET /api/postalcodes/{postalCode}`

### Path Parameters

| Parameter    | Type   | Required | Description               |
| ------------ | ------ | -------- | ------------------------- |
| `postalCode` | String | Yes      | The postal code (5 chars) |

### Response Codes

| Code | Description                    |
| ---- | ------------------------------ |
| 200  | Postal code found successfully |
| 404  | Postal code not found          |
| 500  | Internal server error          |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "postalCode": "00100",
  "city": "Helsinki"
}
```

### Example Request

```bash
curl -X GET http://localhost:8080/api/postalcodes/00100
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Postal code not found"
}
```

</details>

<details>
  <summary><strong>Create New Postal Code</strong></summary>

**Endpoint:** `POST /api/postalcodes`

### Request Body

Content-Type: `application/json`

Provide the postal code fields to create.

```json
{
  "postalCode": "00100",
  "city": "Helsinki"
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 201  | Postal code created successfully  |
| 400  | Invalid input or validation error |
| 500  | Internal server error             |

### Response Body (201 Created)

Content-Type: `application/json`

```json
{
  "postalCode": "00100",
  "city": "Helsinki"
}
```

### Example Request

```bash
curl -X POST http://localhost:8080/api/postalcodes \
     -H "Content-Type: application/json" \
     -d '{
       "postalCode": "00100",
       "city": "Helsinki"
     }'
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Invalid postal code data: ..."
}
```

</details>

<details>
  <summary><strong>Update Postal Code By ID</strong></summary>

**Endpoint:** `PUT /api/postalcodes/{postalCode}`

### Path Parameters

| Parameter    | Type   | Required | Description               |
| ------------ | ------ | -------- | ------------------------- |
| `postalCode` | String | Yes      | The postal code (5 chars) |

### Request Body

Content-Type: `application/json`

Provide the city to update.

```json
{
  "city": "Espoo"
}
```

### Response Codes

| Code | Description                       |
| ---- | --------------------------------- |
| 200  | Postal code updated successfully  |
| 400  | Invalid input or validation error |
| 404  | Postal code not found             |
| 500  | Internal server error             |

### Response Body (200 OK)

Content-Type: `application/json`

```json
{
  "postalCode": "00100",
  "city": "Espoo"
}
```

### Example Request

```bash
curl -X PUT http://localhost:8080/api/postalcodes/00100 \
     -H "Content-Type: application/json" \
     -d '{
       "city": "Espoo"
     }'
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Postal code not found"
}
```

### Example Response (400 Bad Request)

```json
{
  "code": 400,
  "message": "Invalid postal code data: ..."
}
```

</details>

<details>
  <summary><strong>Delete Postal Code By ID</strong></summary>

**Endpoint:** `DELETE /api/postalcodes/{postalCode}`

### Path Parameters

| Parameter    | Type   | Required | Description               |
| ------------ | ------ | -------- | ------------------------- |
| `postalCode` | String | Yes      | The postal code (5 chars) |

### Response Codes

| Code | Description                                 |
| ---- | ------------------------------------------- |
| 204  | Postal code deleted successfully            |
| 400  | Postal code is in use and cannot be deleted |
| 404  | Postal code not found                       |
| 500  | Internal server error                       |

### Response Body (204 No Content)

No body returned on successful deletion.

### Example Request

```bash
curl -X DELETE http://localhost:8080/api/postalcodes/00100
```

### Example Response (404 Not Found)

```json
{
  "code": 404,
  "message": "Postal code not found"
}
```

</details>


</details>
