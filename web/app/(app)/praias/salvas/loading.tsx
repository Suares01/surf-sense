import { Skeleton } from '@/components/ui/skeleton';

export default function LoadingSavedBeaches() {
  return (
    <div>
      <div className='flex max-w-96 flex-col space-y-3'>
        <Skeleton className='h-6 w-72' />
        <div className='relative h-56 w-full'>
          <Skeleton className='h-full w-full rounded-lg' />
        </div>
      </div>
      <div className='flex max-w-96 flex-col space-y-3'>
        <Skeleton className='h-6 w-72' />
        <div className='relative h-56 w-full'>
          <Skeleton className='h-full w-full rounded-lg' />
        </div>
      </div>
      <div className='flex max-w-96 flex-col space-y-3'>
        <Skeleton className='h-6 w-72' />
        <div className='relative h-56 w-full'>
          <Skeleton className='h-full w-full rounded-lg' />
        </div>
      </div>
    </div>
  );
}
