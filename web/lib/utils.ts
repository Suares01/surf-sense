import { CriterionType, RatingType } from '@/app/(app)/ratings/page';
import axios from 'axios';
import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

import type { ApiError } from './types';

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs));
}

export function getUrl() {
  const VERCEL_URL = process.env.VERCEL_URL;
  const NEXT_PUBLIC_VERCEL_URL = process.env.NEXT_PUBLIC_VERCEL_URL;
  const LOCALHOST = 'http://localhost:3001';

  return VERCEL_URL
    ? `https://${VERCEL_URL}`
    : NEXT_PUBLIC_VERCEL_URL
      ? `https://${NEXT_PUBLIC_VERCEL_URL}`
      : LOCALHOST;
}

export function displayAcceptedTypes(acceptedTypes: string[]) {
  return acceptedTypes
    .join(', ')
    .replace(/(image\/)/g, '')
    .toLocaleUpperCase();
}

export function getApiError(error: unknown) {
  if (axios.isAxiosError<ApiError>(error)) return error.response?.data;
}

export function capitalizeFirstLetter(text: string): string {
  return text.charAt(0).toUpperCase() + text.slice(1);
}

export function removeDuplicates<T>(arr: T[]): T[] {
  const seen: { [key: string]: boolean } = {};
  return arr.filter((obj) => {
    const serialized = JSON.stringify(obj);
    return seen.hasOwnProperty(serialized) ? false : (seen[serialized] = true);
  });
}

export function handleCriterionTypeLabel(value: CriterionType): string {
  switch (value) {
    case CriterionType.WAVE_HEIGHT:
      return 'Altura da onda';

    case CriterionType.WAVE_DIRECTION:
      return 'Direção da onda';

    case CriterionType.WAVE_PERIOD:
      return 'Período da onda';

    case CriterionType.SWELL_PERIOD:
      return 'Período do swell';

    case CriterionType.SWELL_DIRECTION:
      return 'Direção do swell';

    case CriterionType.SWELL_HEIGHT:
      return 'Altura do swell';

    case CriterionType.WIND_DIRECTION:
      return 'Direção do vento';

    default:
      return '';
  }
}

export function handleRatingTypeLabel(value: RatingType): string {
  switch (value) {
    case RatingType.SURFER:
      return 'Surfista';

    case RatingType.BEACHGOER:
      return 'Banhista';

    default:
      return '';
  }
}
