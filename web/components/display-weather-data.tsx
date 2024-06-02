'use client';

import {
  HourlyForecast,
  WeatherData,
} from '@/lib/client-queries/get-forecasts';
import { Beach } from '@/lib/types';
import { capitalizeFirstLetter, removeDuplicates } from '@/lib/utils';

import clsx from 'clsx';
import dayjs from 'dayjs';
import { ClockIcon, SunIcon } from 'lucide-react';
import { memo, useMemo, useState } from 'react';

import DailyWeatherCard from './daily-weather-card';
import HourlyWeatherCard from './hourly-weather-card';
import Small from './typography/small';
import { Card, CardContent } from './ui/card';
import { ScrollArea, ScrollBar } from './ui/scroll-area';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from './ui/select';

import '@/lib/dayjs-locale';

export interface DisplayWeatherDataProps {
  data: WeatherData[];
}

export default memo(function DisplayWeatherData({
  data,
}: DisplayWeatherDataProps) {
  const dates = useMemo(
    () =>
      data.map(({ date }) => ({
        label: capitalizeFirstLetter(dayjs(date).format('dddd DD/MM/YY')),
        value: date,
      })),
    [data]
  );

  const beaches = useMemo(
    () =>
      removeDuplicates<Beach>(
        data.flatMap(({ forecasts }) => forecasts.map(({ beach }) => beach))
      ),
    [data]
  );

  const [selectedDate, setSelectedDate] = useState(dates[0]);
  const [selectedBeach, setSelectedBeach] = useState(beaches[0]);
  const [selectedHourly, setSelectedHourly] = useState<HourlyForecast>();

  function resertHourly() {
    setSelectedHourly(undefined);
  }

  function onSelectDate(dateValue: string) {
    const date = dates.find(({ value }) => value === dateValue);

    if (date) {
      setSelectedDate(date);
      resertHourly();
    }
  }

  function onSelectBeach(beachId: string) {
    const beach = beaches.find(({ id }) => id === beachId);

    if (beach) {
      setSelectedBeach(beach);
      resertHourly();
    }
  }

  const selectedForecast = useMemo(() => {
    const filteredByDate = data.find(
      (item) => item.date === selectedDate.value
    );

    if (filteredByDate) {
      const filteredByBeach = filteredByDate.forecasts.find(
        ({ beach }) => beach.id === selectedBeach.id
      );

      return filteredByBeach;
    }
  }, [data, selectedDate.value, selectedBeach.id]);

  const currentHourly = useMemo(() => {
    const now = dayjs().hour();
    const hourly = selectedForecast?.hourly.find(
      (item) => dayjs(item.time).hour() === now
    );

    return hourly;
  }, [selectedForecast?.hourly]);

  const hourly = useMemo(
    () => selectedHourly || currentHourly,
    [currentHourly, selectedHourly]
  );

  return (
    <section className='h-full w-full'>
      <div className='m-2 flex gap-2'>
        <Select defaultValue={selectedDate.value} onValueChange={onSelectDate}>
          <SelectTrigger>
            <SelectValue placeholder='Selecione uma data' />
          </SelectTrigger>

          <SelectContent>
            {dates.map(({ label, value }) => (
              <SelectItem key={value} value={value}>
                {label}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>

        <Select defaultValue={selectedBeach.id} onValueChange={onSelectBeach}>
          <SelectTrigger>
            <SelectValue placeholder='Selecione uma praia' />
          </SelectTrigger>

          <SelectContent>
            {beaches.map(({ id, name }) => (
              <SelectItem key={id} value={id}>
                {name}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>

      {selectedForecast && (
        <div className='m-2 flex flex-col justify-center gap-2'>
          <DailyWeatherCard forecast={selectedForecast} />

          <div>
            <Small className='flex items-center gap-1 text-primary'>
              <ClockIcon className='size-4' /> Previsões horárias
            </Small>

            <ScrollArea className='mt-2 h-fit w-full'>
              <div className='flex w-max gap-2 pb-3'>
                {selectedForecast.hourly.map((hourlyValue) => (
                  <Card
                    key={hourlyValue.time}
                    onClick={() => setSelectedHourly(hourlyValue)}
                  >
                    <CardContent
                      className={clsx(
                        'flex flex-col items-center justify-between gap-1 p-4',
                        {
                          'bg-muted': hourlyValue.time === hourly?.time,
                        }
                      )}
                    >
                      <SunIcon className='size-6 text-yellow-500' />
                      <span>{dayjs(hourlyValue.time).format('HH:mm')}</span>
                      <span>{hourlyValue.temperature}º</span>
                    </CardContent>
                  </Card>
                ))}
              </div>

              <ScrollBar orientation='horizontal' />
            </ScrollArea>
          </div>

          {hourly && <HourlyWeatherCard hourly={hourly} />}
        </div>
      )}
    </section>
  );
});
