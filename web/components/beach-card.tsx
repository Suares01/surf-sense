'use client';

import type { Beach } from '@/lib/types';

import { ImageIcon } from 'lucide-react';
import Image from 'next/image';
import { memo } from 'react';

import { Card, CardContent, CardHeader, CardTitle } from './ui/card';

export interface BeachCardProps {
  beach: Beach;
}

export default memo(function BeachCard({ beach }: BeachCardProps) {
  const { name, image, imageBlurDataUrl } = beach;

  return (
    <Card className='max-w-96'>
      <CardHeader>
        <CardTitle>{name}</CardTitle>
      </CardHeader>
      <CardContent>
        <div className='relative h-56 w-full'>
          {image && imageBlurDataUrl ? (
            <Image
              src={image}
              alt={name}
              className='rounded-lg'
              placeholder='blur'
              blurDataURL={imageBlurDataUrl}
              fill
            />
          ) : (
            <div className='flex h-full w-full items-center justify-center rounded-lg bg-slate-300'>
              <ImageIcon className='size-12 text-slate-400' />
            </div>
          )}
        </div>
      </CardContent>
    </Card>
  );
});
