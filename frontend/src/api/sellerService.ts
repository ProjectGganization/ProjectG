import apiClient from './apiClient';
import { Seller } from '../types/event';

export type { Seller };

export const getSellers = (): Promise<Seller[]> =>
  apiClient.get<Seller[]>('/api/sellers');
