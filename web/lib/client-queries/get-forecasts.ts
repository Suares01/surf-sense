import { Beach } from '../types';
import { query } from './client-axios-instance';

interface Rating {
  name: string;
  type: string;
  value: number;
}

export interface HourlyForecast {
  time: string;
  waveHeight: number;
  waveDirection: number;
  wavePeriod: number;
  swellWaveHeight: number;
  swellWaveDirection: number;
  swellWavePeriod: number;
  temperature: number;
  visibility: number;
  windSpeed: number;
  windDirection: number;
  ratings: Rating[];
}

export interface DailyForecast {
  date: string;
  precipitationProbability: number;
  swellWaveDirectionDominant: number;
  swellWaveHeightMax: number;
  temperatureMax: number;
  temperatureMin: number;
  waveDirectionDominant: number;
  waveHeightMax: number;
}

export interface Forecast {
  date: string;
  beach: Beach;
  hourly: HourlyForecast[];
  daily: DailyForecast;
}

export interface WeatherData {
  date: string;
  forecasts: Forecast[];
}

export async function getForecasts() {
  return await query.get<WeatherData[]>('/api/forecasts');
}
