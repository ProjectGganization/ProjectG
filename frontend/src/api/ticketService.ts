import apiClient from './apiClient';
import { Ticket } from '../types/ticket';

export interface CreateTicketRequest {
  ticketType: string;
  event: { eventId: number };
  unitPrice: number;
  inStock: number;
  orderLimit: number;
}

export const getTicketsByEvent = (eventId: number): Promise<Ticket[]> =>
  apiClient.get<Ticket[]>('/api/tickets').then((tickets) =>
    tickets.filter((t) => t.event.eventId === eventId)
  );

export const createTicket = (data: CreateTicketRequest): Promise<Ticket> =>
  apiClient.post<Ticket>('/api/tickets', data);
