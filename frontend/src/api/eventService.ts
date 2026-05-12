import apiClient from './apiClient';
import { Event } from '../types/event';

export interface CreateEventRequest {
  title: string;
  description: string;
  startTime: string;
  endTime: string;
  eventStatus: { eventStatus: string };
  venue: { venueId: number };
  category: { category: string };
  seller: { sellerId: number };
}

export const getEvents = (): Promise<Event[]> =>
  apiClient.get<Event[]>('/api/events');

export const getEvent = (id: number): Promise<Event> =>
  apiClient.get<Event>(`/api/events/${id}`);

export const createEvent = (data: CreateEventRequest): Promise<Event> =>
  apiClient.post<Event>('/api/events', data);

export const updateEvent = (id: number, data: CreateEventRequest): Promise<Event> =>
  apiClient.put<Event>(`/api/events/${id}`, data);
