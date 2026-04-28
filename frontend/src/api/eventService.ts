import apiClient from './apiClient';
import { Event } from '../types/event';

export const getEvents = (): Promise<Event[]> =>
  apiClient.get<Event[]>('/api/events');

export const getEvent = (id: number): Promise<Event> =>
  apiClient.get<Event>(`/api/events/${id}`);
