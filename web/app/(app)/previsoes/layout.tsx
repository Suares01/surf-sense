// import ForecatsBreadcrumb from '@/components/forecasts-breadcrumb';

import type { PropsWithChildren } from 'react';

export default function ForecastsLayout({ children }: PropsWithChildren) {
  return (
    <>
      {/* <ForecatsBreadcrumb /> */}
      {children}
    </>
  );
}
