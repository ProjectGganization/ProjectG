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
  customerId: number;
}

export interface OrderResponse {
  orderId: number;
}

export interface CreateOrderDetailRequest {
  orderId: number;
  ticketId: number;
  quantity: number;
}

export interface Order {
  orderId: number;
  customer: { firstname: string; lastname: string; email: string };
  date: string;
  isPaid: boolean;
  isRefunded: boolean;
  paymentMethod: { paymentMethod: string } | null;
}

export const getOrders = (): Promise<Order[]> =>
  apiClient.get<Order[]>('/api/orders');

export const createCustomer = (data: CreateCustomerRequest): Promise<CustomerResponse> =>
  apiClient.post<CustomerResponse>('/api/customers', data);

export const createOrder = (customerId: number): Promise<OrderResponse> =>
  apiClient.post<OrderResponse>('/api/orders', {
    customerId,
  } satisfies CreateOrderRequest);

export const createOrderDetail = (
  orderId: number,
  ticketId: number,
  quantity: number,
): Promise<void> =>
  apiClient.post<void>('/api/orderdetails', {
    orderId,
    ticketId,
    quantity,
  } satisfies CreateOrderDetailRequest);
