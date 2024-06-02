'use client';

import { getBeaches } from '@/lib/client-queries';
import { Beach } from '@/lib/types';

import { useUser } from '@auth0/nextjs-auth0/client';
import { useQuery } from '@tanstack/react-query';

import BeachCard from './beach-card';

export interface BeachListProps {
  beaches: Beach[];
}

export default function BeachList({ beaches }: BeachListProps) {
  const { user } = useUser();

  const { data } = useQuery<Beach[]>({
    queryKey: [user?.sub, 'beaches'],
    async queryFn() {
      const beaches = await getBeaches();

      return beaches;
    },
    initialData: beaches,
  });

  return data.map((beach) => <BeachCard key={beach.id} beach={beach} />);
}
