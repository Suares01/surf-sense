import { Beach, Position } from '../types';
import { query } from './client-axios-instance';

export interface SaveBeach {
  name: string;
  lat: number;
  lng: number;
  position: Position;
  countryCode: string;
  country: string;
  city: string;
  image?: string;
}

export async function saveBeach(data: SaveBeach) {
  try {
    const { data: beach } = await query.post<Beach>('/api/beach', data);

    return beach;
  } catch (error) {
    console.log(error);
  }
}
