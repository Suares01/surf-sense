'use client';

import {
  QueryClient,
  QueryClientProvider as TanstackQueryClientProvider,
} from '@tanstack/react-query';
import { PropsWithChildren, useState } from 'react';

export default function QueryClientProvider({ children }: PropsWithChildren) {
  const [client] = useState(new QueryClient());

  return (
    <TanstackQueryClientProvider client={client}>
      {children}
    </TanstackQueryClientProvider>
  );
}
