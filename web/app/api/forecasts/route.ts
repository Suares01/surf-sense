import { apiErrorHandler } from '@/lib/api-error-handler';
import type { WeatherData } from '@/lib/client-queries/get-forecasts';
import { dynamicBlurDataUrl } from '@/lib/dynamic-blur-data-url';
import { getQuery } from '@/lib/server-queries';

import { withApiAuthRequired } from '@auth0/nextjs-auth0';
import { NextResponse } from 'next/server';

export const GET = withApiAuthRequired(
  apiErrorHandler(async () => {
    const query = await getQuery();

    const { data } = await query.get<WeatherData[]>('/forecasts');

    const weatherData = await Promise.all(
      data.map<Promise<WeatherData>>(async ({ beach, forecasts }) => ({
        beach: {
          ...beach,
          ...(beach?.image && {
            imageBlurDataUrl: await dynamicBlurDataUrl(beach.image),
          }),
        },
        forecasts,
      }))
    );

    return NextResponse.json(weatherData);
  })
);
