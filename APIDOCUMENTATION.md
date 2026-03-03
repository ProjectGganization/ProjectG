# API Documentation

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
  Create a new order detail
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


