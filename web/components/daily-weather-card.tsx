import type { Forecast } from '@/lib/client-queries/get-forecasts';

import { SunIcon } from 'lucide-react';

import Small from './typography/small';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from './ui/card';

export interface DailyWeatherCardProps {
  forecast: Forecast;
}

export default function DailyWeatherCard({ forecast }: DailyWeatherCardProps) {
  const { beach, daily } = forecast;

  return (
    <Card className='max-w-96'>
      <CardHeader>
        <CardTitle>{beach.name}</CardTitle>
      </CardHeader>

      <CardContent className='flex items-center justify-between'>
        <div className='flex-1'>
          <div className='text-sm text-muted-foreground dark:text-muted'>
            Rio de Janeiro, BR
          </div>
          <div className='text-3xl font-bold text-primary dark:text-primary-foreground'>
            {daily.temperatureMax}{' '}
            <span className='inline-block text-lg font-medium text-muted-foreground dark:text-muted'>
              / {daily.temperatureMin} &deg;C
            </span>
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
          <Small>Chuva</Small>
          <div className='text-sm text-gray-500'>
            {daily.precipitationProbability}%
          </div>
        </div>

        <div className='flex flex-col items-center'>
          <Small>Altura da onda</Small>
          <div className='text-sm text-gray-500'>{daily.waveHeightMax}m</div>
        </div>

        <div className='flex flex-col items-center'>
          <Small>Visibility</Small>
          <div className='text-sm text-gray-500'>10km</div>
        </div>
      </CardFooter>
    </Card>
  );
}
