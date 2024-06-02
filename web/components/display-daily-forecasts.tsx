'use client';

import { type CarouselApi } from '@/components/ui/carousel';

import {
  getForecasts,
  type WeatherData,
} from '@/lib/client-queries/get-forecasts';
import { Beach } from '@/lib/types';
import { capitalizeFirstLetter } from '@/lib/utils';

import { useQuery } from '@tanstack/react-query';
import dayjs from 'dayjs';
import { memo, useCallback, useEffect, useMemo, useState } from 'react';

import BeachCard from './beach-card';
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from './ui/carousel';
import { ScrollArea, ScrollBar } from './ui/scroll-area';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from './ui/select';
import { Tabs, TabsContent, TabsList, TabsTrigger } from './ui/tabs';
import '@/lib/dayjs-locale';

export interface DisplayDailyForecastsProps {
  weatherData: WeatherData[];
}

interface ForecastDetails {}

function ForecastDetails() {}

export default memo(function DisplayDailyForecasts({
  weatherData,
}: DisplayDailyForecastsProps) {
  const [api, setApi] = useState<CarouselApi>();
  const [selectedBeach, setSelectedBeach] = useState<Beach>(
    weatherData[0].beach
  );
  const data = weatherData;
  // const { data } = useQuery({
  //   queryKey: ['forecasts'],
  //   async queryFn() {
  //     const { data } = await getForecasts();

  //     return data;
  //   },
  //   initialData: weatherData,
  // });

  const beaches = useMemo(() => data.map(({ beach }) => beach), [data]);
  const selectedForecast = useMemo(
    () => data.find((item) => item.beach.id === selectedBeach),
    [data, selectedBeach]
  );
  const weekDays = useMemo(
    () =>
      selectedForecast?.forecasts.map(({ date }) => ({
        label: capitalizeFirstLetter(dayjs(date).format('dddd')),
        value: date,
      })),
    [selectedForecast]
  );

  const selectedHourly = () => {};

  const handleSelectBeach = useCallback(() => {
    if (!api) return;
    const index = api.selectedScrollSnap();
    setSelectedBeach(beaches[index].id);
  }, [api, beaches]);

  // useEffect(() => {
  //   if (!api) return;

  //   api.on('select', handleSelectBeach);

  //   return () => {
  //     api.off('select', handleSelectBeach);
  //   };
  // }, [api, handleSelectBeach]);

  // function formatDayOfWeek(date: string) {
  //   return capitalizeFirstLetter(dayjs(date).format('dddd'));
  // }

  // function formatDate(date: string) {
  //   return dayjs(date).format('DD/MM/YYYY');
  // }

  return (
    // <ScrollArea className='h-full w-full'>
    //   <div className='flex h-full w-full flex-col items-center'>
    //     <Carousel className='w-full max-w-sm p-2' setApi={setApi}>
    //       <CarouselContent>
    //         {beaches.map((beach) => (
    //           <CarouselItem key={beach.id}>
    //             <BeachCard beach={beach} />
    //           </CarouselItem>
    //         ))}
    //       </CarouselContent>
    //       <CarouselPrevious className='hidden md:flex' />
    //       <CarouselNext className='hidden md:flex' />
    //     </Carousel>

    //     <Tabs className='w-full max-w-sm p-2'>
    //       <ScrollArea className='w-full rounded-lg'>
    //         <TabsList>
    //           {weekDays?.map(({ label, value }) => (
    //             <TabsTrigger key={value} value={value}>
    //               {label}
    //             </TabsTrigger>
    //           ))}
    //         </TabsList>
    //         <ScrollBar orientation='horizontal' />
    //       </ScrollArea>

    //       {selectedForecast?.forecasts.map(({ date, hourly }) => (
    //         <TabsContent key={date} value={date}>
    //           <Tabs>
    //             <ScrollArea className='w-full rounded-lg'>
    //               <TabsList>
    //                 {hourly.map(({ time }) => (
    //                   <TabsTrigger key={time} value={time}>
    //                     {dayjs(time).format('HH:mm')}
    //                   </TabsTrigger>
    //                 ))}
    //               </TabsList>
    //               <ScrollBar orientation='horizontal' />
    //             </ScrollArea>

    //             {selectedForecast?.forecasts.flatMap(({ hourly }) =>
    //               hourly.map(({ time }) => (
    //                 <TabsContent key={time} value={time}>
    //                   {time}
    //                 </TabsContent>
    //               ))
    //             )}
    //           </Tabs>
    //         </TabsContent>
    //       ))}
    //     </Tabs>
    //   </div>
    // </ScrollArea>
    <section className='h-full w-full'>
      <Select>
        <SelectTrigger className='m-2 w-48'>
          <SelectValue defaultValue={selectedBeach.id} />
        </SelectTrigger>

        <SelectContent>
          {beaches.map(({ id, name }) => (
            <SelectItem key={id} value={id}>
              {name}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>

      <ScrollArea className='h-full w-full'></ScrollArea>
    </section>
  );
});
