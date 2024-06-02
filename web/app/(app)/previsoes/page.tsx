import DisplayWeatherData from '@/components/display-weather-data';

import type { Forecast, WeatherData } from '@/lib/client-queries/get-forecasts';
import { dynamicBlurDataUrl } from '@/lib/dynamic-blur-data-url';
import { getQuery } from '@/lib/server-queries';

export default async function ForecastsPage() {
  const query = await getQuery();

  const { data } = await query.get<WeatherData[]>('/forecasts');

  const dataWithBlurUrl = data.length
    ? await Promise.all(
        data.map<Promise<WeatherData>>(async ({ date, forecasts }) => ({
          date,
          forecasts: await Promise.all(
            forecasts.map<Promise<Forecast>>(async ({ beach, ...rest }) => ({
              ...rest,
              beach: {
                ...beach,
                ...(beach?.image && {
                  imageBlurDataUrl: await dynamicBlurDataUrl(beach.image),
                }),
              },
            }))
          ),
        }))
      )
    : ([] as WeatherData[]);

  return data.length ? (
    <DisplayWeatherData data={dataWithBlurUrl} />
  ) : (
    <div>Nenhuma praia cadastrada.</div>
  );
}
