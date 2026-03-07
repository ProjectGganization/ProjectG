# API Documentation

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


