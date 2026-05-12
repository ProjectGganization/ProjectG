import apiClient from './apiClient';
import { Venue } from '../types/event';

export const getVenues = (): Promise<Venue[]> =>
  apiClient.get<Venue[]>('/api/venues');

export const createVenue = (data: { name: string; address: string; postalCode: string }): Promise<Venue> =>
  apiClient.post<Venue>('/api/venues', {
    name: data.name,
    address: data.address,
    postalCode: { postalCode: data.postalCode },
  });

export const updateVenue = (id: number, data: { name: string; address: string; postalCode: string }): Promise<Venue> =>
  apiClient.put<Venue>(`/api/venues/${id}`, {
    name: data.name,
    address: data.address,
    postalCode: { postalCode: data.postalCode },
  });

export const deleteVenue = (id: number): Promise<void> =>
  apiClient.delete<void>(`/api/venues/${id}`);
