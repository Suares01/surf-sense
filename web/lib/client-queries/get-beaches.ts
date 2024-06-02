import { Beach } from '../types';
import { query } from './client-axios-instance';

export async function getBeaches() {
  const { data } = await query.get<Beach[]>('/api/beach');

  return data;
}
