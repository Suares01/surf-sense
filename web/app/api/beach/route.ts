import { apiErrorHandler } from '@/lib/api-error-handler';
import { dynamicBlurDataUrl } from '@/lib/dynamic-blur-data-url';
import { getQuery } from '@/lib/server-queries';
import { Beach, Position } from '@/lib/types';

import { withApiAuthRequired } from '@auth0/nextjs-auth0';
import { NextResponse } from 'next/server';

interface Body {
  name: string;
  lat: number;
  lng: number;
  position: Position;
  image?: string;
}

export const POST = withApiAuthRequired(
  apiErrorHandler(async (request) => {
    const query = await getQuery();

    const { name, lat, lng, position, image } = (await request.json()) as Body;

    const { data } = await query.post<Beach>('/beaches', {
      name,
      lat,
      lng,
      position,
      image,
    });

    return NextResponse.json(data);
  })
);

export const GET = withApiAuthRequired(
  apiErrorHandler(async () => {
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

    return NextResponse.json(beaches);
  })
);
