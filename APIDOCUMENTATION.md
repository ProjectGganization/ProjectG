# API Documentation

## Events API

Base URL: `/api/events`

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get all Events
</span>
</summary>

Get all events

**Endpoint:** `GET /api/events`

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | All events found successfully |
| 404  | No events found |
| 500  | Internal server error |

**Response Body (200 OK):**

Content-Type: `application/json`

``` json
{
  "event_id": 1,
  "title": "Event name",
  "description": "Event description",
  "start_time": "2026-03-15T19:00:00",
  "end_time": "2026-03-15T23:00:00",
  "venue": {
    "venue_id": 1,
    "name": "Venue name"
  },
  "event_status": {
    "id": 1,
    "status": "Active"
  }
}
```
**Example Request:**

``` bash
curl -X GET http://localhost:8080/api/events
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "No events found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get Event by ID
</span>
</summary>

Retrieve a single event by its unique identifier.

**Endpoint:** `GET /api/events/{id}`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | Event found successfully |
| 404  | Event not found          |
| 500  | Internal server error    |

**Response Body (200 OK):**

Content-Type: `application/json`

```json
{
  "id": 1,
  "name": "Event Name",
  "description": "Event Description",
  "startDate": "2026-03-15T19:00:00",
  "endDate": "2026-03-15T23:00:00",
  "venue": {
    "id": 1,
    "name": "Venue Name"
  },
  "category": {
    "id": 1,
    "name": "Category Name"
  },
  "eventStatus": {
    "id": 1,
    "status": "Active"
  }
}
```

**Example Request:**

```bash
curl -X GET http://localhost:8080/api/events/1
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "Event not found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Delete Event by ID
</span>
</summary>

Delete a single event by its unique identifier.

**Endpoint:** `DELETE /api/events/{id}`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

**Response Codes:**

| Code | Description                    |
| ---- | ------------------------------ |
| 204  | Event deleted successfully     |
| 404  | Event not found                |
| 500  | Internal server error          |

**Response Body (204 No Content):**

No body returned on successful deletion.

**Example Request:**

```bash
curl -X DELETE http://localhost:8080/api/events/1
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "Event not found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Create New Event
</span>
</summary>

Add a new event. This endpoint is used by administrators to manage upcoming events.

**Endpoint:** `POST /api/events`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `title`      | String | Yes    | The name of the event |
| `description`| String | No     | Detailed information about the event |
| `start_time` | String (ISO) | Yes | Event start date and time |
| `end_time` | String (ISO) | No | Event end date and time |
| `venue_id` | Integer | Yes | ID of the venue where the event is held |
| `category` | Integer | Yes | ID of the event caregory |  

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 201  | Event created successfully |
| 400  | Invalid input or validation error |
| 500  | Internal server error    |

**Response Body (201 Created):**

Content-Type: `application/json`

``` json
{
  "event_id": 1,
  "title": "Event name",
  "description": "Event description",
  "start_time": "2026-03-15T19:00:00",
  "end_time": "2026-03-15T23:00:00",
  "venue": {
    "venue_id": 1,
    "name": "Venue name"
  },
  "event_status": {
    "id": 1,
    "status": "Active"
  }
}
```
**Example Request:**

``` bash
curl -X POST http://localhost:8080/api/events \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Event name",
       "description": "Event description",
       "start_time": "2026-07-15T18:00:00",
       "end_time": "2026-03-15T23:00:00",
       "venue_id": 1
     }'
```

**Example Response (404 Not Found):**

```json
{
  "status": 400,
  "error": "Invalid event data: "
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Update Event by ID
</span>
</summary>

Update an existing event by its unique identifier. The server will use the path `id` as the event's id — do not rely on any `event_id` value in the body.

**Endpoint:** `PUT /api/events/{id}`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique event ID |

**Request Body:**

Content-Type: `application/json`

Provide the event fields to update. Example body:

```json
{
  "title": "Updated Event name",
  "description": "Updated description",
  "start_time": "2026-07-15T18:00:00",
  "end_time": "2026-07-15T23:00:00",
  "venue_id": 1,
  "category": 2
}
```

**Response Codes:**

| Code | Description                    |
| ---- | ------------------------------ |
| 200  | Event updated successfully     |
| 400  | Invalid input or validation error |
| 404  | Event not found                |
| 500  | Internal server error          |

**Response Body (200 OK):**

Content-Type: `application/json`

```json
{
  "event_id": 1,
  "title": "Updated Event name",
  "description": "Updated description",
  "start_time": "2026-07-15T18:00:00",
  "end_time": "2026-07-15T23:00:00",
  "venue": {
    "venue_id": 1,
    "name": "Venue name"
  },
  "event_status": {
    "id": 1,
    "status": "Active"
  }
}
```

**Example Request:**

```bash
curl -X PUT http://localhost:8080/api/events/1 \
     -H "Content-Type: application/json" \
     -d '{
       "title": "Updated Event name",
       "description": "Updated description",
       "start_time": "2026-07-15T18:00:00",
       "venue_id": 1
     }'
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "Event not found"
}
```
</details>

---

## Tickets API

Base URL: `/api/tickets`

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get all Tickets
</span>
</summary>

Retrieve a list of all ticket types available in the system

**Endpoint:** `GET /api/tickets`

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | Tickets found successfully |
| 404  | No tickets found |
| 500  | Internal server error |

**Response Body (200 OK):**

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

**Example Request:**

``` bash
curl -X GET http://localhost:8080/api/tickets
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "No tickets found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Create New Ticket
</span>
</summary>

Create a new ticket for a specific event.

**Endpoint:** `POST /api/tickets`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `event_id` | Integer | Yes  | The ID of the event ticket belongs to |
| `ticket_type`| String | Yes | Type of the ticket (e.g., Adult, Student) |
| `unitprice` | Decimal | Yes | Price pet ticket |
| `in_stock` | Integer | Yes  | Total number of tickets available |
| `order_limit` | Integer | Yes | Max tickets per single order |

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 201  | Ticket created successfully |
| 400  | Invalid input or validation error |
| 500  | Internal server error    |

**Response Body (201 Created):**

Content-Type: `application/json`

``` json
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

**Example Request:**

``` bash
curl -X 'POST' \'http://localhost:8080/api/tickets' \
     -H 'accept: application/json' \
     -H 'Content-Type: application/json' \
     -d '{
     "unitPrice": 12,
     "inStock": 107,
     "orderLimit": 107
  }'
```

**Example Response (404 Not Found):**

```json
{
  "status": 400,
  "error": "Invalid input data: "
}
```
</details>

---

## Orders API

Base URL: `/api/orders`

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get all Orders
</span>
</summary>

Retrieve a list of all orders available in the system

**Endpoint:** `GET /api/orders`

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | Orders found successfully |
| 404  | No orders found |
| 500  | Internal server error |

**Response Body (200 OK):**

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

**Example Request:**

``` bash
curl -X GET http://localhost:8080/api/orders
```

**Example Response (404 Not Found):**
```json
{
  "status": 404,
  "error": "No orders found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get Order by ID
</span>
</summary>
Retrieve a single order by its unique identifier.

**Endpoint:** `GET /api/orders/{id}`

**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Integer | Yes      | The unique order ID |

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | Order found successfully |
| 404  | Order not found          |
| 500  | Internal server error    |

**Response Body (200 OK):**

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

**Example Request:**

```bash
curl -X GET http://localhost:8080/api/orders/1
```

**Example Response (404 Not Found):**

```json
{
  "status": 404,
  "error": "Order not found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Create New Order
</span>
</summary>

Create a new order.

**Endpoint:** `POST /api/orders`

**Request Body:**

Content-Type: `application/json`

Provide the order fields to create. Example body:

```json
{
  "orderId": 1,
  "customerId": 3,
  "sellerId": 2,
  "date": "2026-03-01T18:48:20.008Z",
  "isRefunded": false,
  "isPaid": true,
  "paymentMethod": "bank"
}
```

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 201  | Order created successfully |
| 400  | Invalid input or validation error |
| 500  | Internal server error    |

**Response Body (201 Created):**

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
**Example Request:**

``` bash
curl -X POST http://localhost:8080/api/orders \
     -H "Content-Type: application/json" \
     -d '{
       "orderId": 1,
       "customerId": 3,
       "sellerId": 2,
       "isRefunded": false,
       "isPaid": true,
       "paymentMethod": "bank"
     }'
```

**Example Response (400 Bad Request):**

```json
{
  "status": 400,
  "error": "Invalid input data: "
}
```
</details>

---

</details>


## OrderDetails API

Base URL: `/api/orderdetails`

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get all Order Details
</span>
</summary>

Get all order details

**Endpoint:** `GET /api/ordersdetails`

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 200  | Order Details Found Successfully |
| 404  | No Data Found |
| 500  | Internal Server Error |


**Response Body (200 OK):**

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

**Example Request:**

``` bash
curl -X GET http://localhost:8080/api/orderdetails
```

**Example Response (404 Not Found):**
```json
{
  "status": 404,
  "message": "No order details found"
}
```
</details>

---

<details>
<summary>
<span style="font-size: 1.5em; font-weight: bold; font-style: italic;">
  Get Order Details By ID
</span>
</summary>
Creates a new order detail for an order.

**Endpoint:** `POST /api/orderdetails/{id}`

Request body

{
  "Id": 1001,
  "productId": 501,
  "quantity": 2,
  "price": 49.99
}

Retrieves a single order detail by its ID.
**Path Parameters:**

| Parameter | Type    | Required | Description         |
| --------- | ------- | -------- | ------------------- |
| `id`      | Long | Yes      | The unique order ID |

**Response Codes:**

| Code | Description              |
| ---- | ------------------------ |
| 201  | Order found successfully |
| 404  | Order not found          |
| 500  | Internal server error    |

**Response Body (201 CREATED):**

Content-Type: `application/json`

```json
{
  "id": 10,
  "orderId": 1001,
  "productId": 501,
  "quantity": 2,
  "price": 49.99
}
```

**Example Request:**

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

**Example Response (404 Not Found):**

```json
{
  "status": 400,
  "message": "Invalid order detail data: <error message>"
}
```
</details>

---


