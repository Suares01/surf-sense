import AppHeader from '@/components/app-header';

import { PropsWithChildren } from 'react';

export default function AppLayout({ children }: PropsWithChildren) {
  return <AppHeader>{children}</AppHeader>;
}
