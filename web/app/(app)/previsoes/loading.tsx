import { Skeleton } from '@/components/ui/skeleton';

export default function LoadingForecasts() {
  return (
    <div className='flex h-full w-full flex-col items-center divide-y'>
      <div className='mb-2 w-full'>
        <div className='m-2 flex items-baseline gap-1'>
          <Skeleton className='h-8 w-32' />
          <Skeleton className='h-5 w-20' />
        </div>

        <div className='m-2 flex flex-col gap-2'>
          <Skeleton className='h-[210px] max-w-96' />
        </div>
      </div>

      <div className='mb-2 w-full'>
        <div className='m-2 flex items-baseline gap-1'>
          <Skeleton className='h-8 w-32' />
          <Skeleton className='h-5 w-20' />
        </div>

        <div className='m-2 flex flex-col gap-2'>
          <Skeleton className='h-[210px] max-w-96' />
        </div>
      </div>

      <div className='mb-2 w-full'>
        <div className='m-2 flex items-baseline gap-1'>
          <Skeleton className='h-8 w-32' />
          <Skeleton className='h-5 w-20' />
        </div>

        <div className='m-2 flex flex-col gap-2'>
          <Skeleton className='h-[210px] max-w-96' />
        </div>
      </div>
    </div>
  );
}
