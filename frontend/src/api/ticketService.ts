import apiClient from './apiClient';
import { Ticket } from '../types/ticket';

export const getTicketsByEvent = (eventId: number): Promise<Ticket[]> =>
  apiClient.get<Ticket[]>('/api/tickets').then((tickets) =>
    tickets.filter((t) => t.event.eventId === eventId)
  );
