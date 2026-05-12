import apiClient from './apiClient';
import { Venue } from '../types/event';

export const getVenues = (): Promise<Venue[]> =>
  apiClient.get<Venue[]>('/api/venues');
