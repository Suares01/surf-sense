'use client';

import { HourlyForecast } from '@/lib/client-queries/get-forecasts';

import dayjs from 'dayjs';
import { SunIcon } from 'lucide-react';

import Small from './typography/small';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from './ui/card';

export interface HourlyWeatherCardProps {
  hourly: HourlyForecast;
}

export default function HourlyWeatherCard({ hourly }: HourlyWeatherCardProps) {
  return (
    <Card className='max-w-96'>
      <CardHeader>
        <CardTitle>
          Destaques das {dayjs(hourly.time).format('HH:mm')}
        </CardTitle>
      </CardHeader>

      <CardContent className='flex items-center justify-between'>
        <div className='flex-1'>
          <div className='text-3xl font-bold text-primary dark:text-primary-foreground'>
            {hourly.temperature}&deg;C
          </div>
          <div className='text-xs text-muted-foreground dark:text-muted'>
            Sol
          </div>
        </div>
        <div className='flex w-24 justify-center'>
          <SunIcon className='h-12 w-12 text-yellow-500' />
        </div>
      </CardContent>

      <CardFooter className='flex flex-row justify-between'>
        <div className='flex flex-col items-center'>
          <Small>Altura da onda</Small>
          <div className='text-sm text-gray-500'>{hourly.waveHeight}m</div>
        </div>
      </CardFooter>
    </Card>
  );
}
