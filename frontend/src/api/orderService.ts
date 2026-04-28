import apiClient from './apiClient';

export interface CreateCustomerRequest {
  firstname: string;
  lastname: string;
  email: string;
  phone: string;
}

export interface CustomerResponse {
  customerId: number;
  firstname: string;
  lastname: string;
  email: string;
  phone: string;
}

export interface CreateOrderRequest {
  customer: { customerId: number };
  seller: { sellerId: number };
  date: string;
  isRefunded: boolean;
  isPaid: boolean;
  paymentMethod: { paymentMethod: string };
}

export interface OrderResponse {
  orderId: number;
}

export interface CreateOrderDetailRequest {
  id: { orderId: number; ticketId: number };
  order: { orderId: number };
  ticket: { ticketId: number };
  unitPrice: number;
  quantity: number;
}

const WEB_SELLER_ID = 1;

export const createCustomer = (data: CreateCustomerRequest): Promise<CustomerResponse> =>
  apiClient.post<CustomerResponse>('/api/customers', data);

export const createOrder = (customerId: number, paymentMethod: string): Promise<OrderResponse> =>
  apiClient.post<OrderResponse>('/api/orders', {
    customer: { customerId },
    seller: { sellerId: WEB_SELLER_ID },
    date: new Date().toISOString(),
    isRefunded: false,
    isPaid: true,
    paymentMethod: { paymentMethod },
  } satisfies CreateOrderRequest);

export const createOrderDetail = (
  orderId: number,
  ticketId: number,
  unitPrice: number,
  quantity: number,
): Promise<void> =>
  apiClient.post<void>('/api/orderdetails', {
    id: { orderId, ticketId },
    order: { orderId },
    ticket: { ticketId },
    unitPrice,
    quantity,
  } satisfies CreateOrderDetailRequest);
