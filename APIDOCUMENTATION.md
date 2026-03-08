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

!INCLUDE "docs/api/ExampleAPI.md"

</details>

<!-- node build.js -komento päivittää tiedoston nimeltä APIDOCUMENTATION.generated.md, joka copypastaa docs/api tiedostot sinne"
HUOM! Älä ikinä lisää mitään tiedostoon APIDOCUMENTATION.generated.md! -->

<!-- Vinkki vitonen: Ctrl+Shift+V voi avulla pystyy previewaa esim. APIDOCUMENTATION.generated.md :) -->

## 🎭 Event API
<details>
  <summary><strong>Expand Event API</strong></summary>

!INCLUDE "docs/api/EventAPI.md"

</details>

## 🏟️ Venue API
<details>
  <summary><strong>Expand Venue API</strong></summary>

!INCLUDE "docs/api/VenueAPI.md"

</details>

## 🎫 Ticket API
<details>
  <summary><strong>Expand Ticket API</strong></summary>

!INCLUDE "docs/api/TicketAPI.md"

</details>

## 🎟️ Issued Ticket API
<details>
  <summary><strong>Expand Issued Ticket API</strong></summary>

!INCLUDE "docs/api/IssuedTicketAPI.md"

</details>

## 🛒 Order API
<details>
  <summary><strong>Expand Order API</strong></summary>

!INCLUDE "docs/api/OrderAPI.md"

</details>

## 📦 Order Details API
<details>
  <summary><strong>Expand Order Details API</strong></summary>

!INCLUDE "docs/api/OrderDetailsAPI.md"

</details>

## 🏙️ Postal Code API
<details>
  <summary><strong>Expand Postal Code API</strong></summary>

!INCLUDE "docs/api/PostalCodeAPI.md"

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


