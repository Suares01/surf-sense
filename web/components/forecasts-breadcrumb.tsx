'use client';

import { capitalizeFirstLetter } from '@/lib/utils';

import Link from 'next/link';
import { usePathname } from 'next/navigation';
import { Fragment } from 'react';

import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from './ui/breadcrumb';

export default function ForecatsBreadcrumb() {
  const pathname = usePathname();

  const paths = pathname.split('/').filter(Boolean);

  function buildPath(index: number) {
    return `/${paths.slice(0, index + 1).join('/')}`;
  }

  function displayPath(path: string) {
    return capitalizeFirstLetter(decodeURIComponent(path));
  }

  return (
    <Breadcrumb className='m-2'>
      <BreadcrumbList>
        {paths.map((path, index) => (
          <Fragment key={path}>
            <BreadcrumbItem>
              {buildPath(index) === pathname ? (
                <BreadcrumbPage>{displayPath(path)}</BreadcrumbPage>
              ) : (
                <>
                  <BreadcrumbLink asChild>
                    <Link href={buildPath(index)}>{displayPath(path)}</Link>
                  </BreadcrumbLink>
                  <BreadcrumbSeparator />
                </>
              )}
            </BreadcrumbItem>
          </Fragment>
        ))}
      </BreadcrumbList>
    </Breadcrumb>
  );
}
