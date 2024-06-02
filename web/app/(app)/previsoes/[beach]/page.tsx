'use client';

import H3 from '@/components/typography/h3';
import Muted from '@/components/typography/muted';
import P from '@/components/typography/p';

import { WeatherData } from '@/lib/client-queries/get-forecasts';

import { useQueryClient } from '@tanstack/react-query';
import { WindIcon } from 'lucide-react';
import dynamic from 'next/dynamic';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

const StaticMap = dynamic(() => import('@/components/maps/static-map'), {
  ssr: false,
});

interface PageParams {
  beach: string;
}

interface PageSearchParams {
  date: string;
}

interface BeachForecastProps {
  params: PageParams;
  searchParams: PageSearchParams;
}

export default function BeachForecast({
  params,
  searchParams,
}: BeachForecastProps) {
  const router = useRouter();

  const beachName = decodeURIComponent(params.beach);
  const date = searchParams.date;

  const queryClient = useQueryClient();

  const queryData = queryClient.getQueryData<WeatherData[]>(['forecasts']);

  useEffect(() => {
    if (!queryData) router.push('/previsoes');
  }, [queryData, router]);

  const weatherData = queryData?.find((item) => item.date === date);
  const forecast = weatherData?.forecasts.find(
    (item) => item.beach.name === beachName
  );
  const daily = forecast?.daily;
  const beach = forecast?.beach;

  return (
    <main className='p-2'>
      <section>
        <H3>{beach?.name}</H3>
        <Muted>Rio de Janeiro, BR</Muted>
      </section>

      <section className='mt-2'>
        <P className='flex'>
          <WindIcon /> <span>9 km/h</span>
        </P>
      </section>

      <section className='h-48 w-full'>
        {beach && (
          <StaticMap
            beachName={beach.name}
            position={{ lat: beach.lat, lng: beach.lng }}
          />
        )}
      </section>
    </main>
  );
}
