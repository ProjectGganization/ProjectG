import apiClient from './apiClient';
import { PostalCode } from '../types/event';

export const getPostalCode = (code: string): Promise<PostalCode> =>
  apiClient.get<PostalCode>(`/api/postalcodes/${code}`);

export const createPostalCode = (code: string, city: string): Promise<PostalCode> =>
  apiClient.post<PostalCode>('/api/postalcodes', { postalCode: code, city });
