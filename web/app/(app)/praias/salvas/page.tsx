import BeachList from '@/components/beach-list';
import { Button } from '@/components/ui/button';

import { dynamicBlurDataUrl } from '@/lib/dynamic-blur-data-url';
import { getQuery } from '@/lib/server-queries';
import { Beach } from '@/lib/types';

import { PlusIcon } from 'lucide-react';
import Link from 'next/link';

export default async function BeachesPage() {
  const query = await getQuery();

  const { data } = await query.get<Beach[]>('/beaches');

  const beaches = await Promise.all(
    data.map(async (beach) => ({
      ...beach,
      ...(beach?.image && {
        imageBlurDataUrl: await dynamicBlurDataUrl(beach.image),
      }),
    }))
  );

  return (
    <>
      <Link href='/praias/criar'>
        <Button>
          <PlusIcon className='mr-1 h-5 w-5' /> Criar
        </Button>
      </Link>

      <BeachList beaches={beaches} />
    </>
  );
}
