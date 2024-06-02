import QueryClientProvider from '@/components/query-client-provider';
import { Toaster } from '@/components/ui/sonner';

import { cn } from '@/lib/utils';

import { UserProvider } from '@auth0/nextjs-auth0/client';
import { Inter as FontSans } from 'next/font/google';

import './globals.css';

export const fontSans = FontSans({
  subsets: ['latin'],
  variable: '--font-sans',
});

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang='en' className='h-full scroll-smooth'>
      <UserProvider>
        <QueryClientProvider>
          <body
            className={cn(
              'h-full bg-background font-sans antialiased',
              fontSans.variable
            )}
          >
            {children}
            <Toaster />
          </body>
        </QueryClientProvider>
      </UserProvider>
    </html>
  );
}
