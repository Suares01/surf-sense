'use client';

import { useUser } from '@auth0/nextjs-auth0/client';
import {
  AtSignIcon,
  CloudSunIcon,
  LockKeyholeIcon,
  MenuIcon,
  PalmtreeIcon,
  Settings,
  StarIcon,
  UserRoundIcon,
  LogOutIcon,
} from 'lucide-react';
import Link from 'next/link';
import type { PropsWithChildren } from 'react';

import Logo from './logo';
import { Button } from './ui/button';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from './ui/dropdown-menu';
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from './ui/sheet';
import UserAvatar from './user-avatar';

export interface AppHeaderProps extends PropsWithChildren {}

const NAV_PATHS = [
  {
    href: '/previsoes',
    label: 'Previsões',
    icon: CloudSunIcon,
  },
  {
    href: '/praias/salvas',
    label: 'Praias',
    icon: PalmtreeIcon,
  },
  {
    href: '/avaliacoes',
    label: 'Avaliações',
    icon: StarIcon,
  },
];

const HELP_PATHS = [
  {
    href: '/ajuda#criando-uma-praia',
    label: 'Criando uma praia',
  },
  {
    href: '/ajuda#criando-uma-avaliacao',
    label: 'Criando uma avaliação',
  },
  {
    href: '/ajuda#previsoes',
    label: 'Previsões',
  },
];

const ACCOUNT_PATHS = [
  {
    href: '/conf/perfil',
    label: 'Seu Perfil',
    icon: UserRoundIcon,
  },
  {
    href: '/conf/account',
    label: 'Sua Conta',
    icon: AtSignIcon,
  },
  {
    href: '/conf/seguranca',
    label: 'Segurança',
    icon: LockKeyholeIcon,
  },
  {
    href: '/conf/custom',
    label: 'Configurações',
    icon: Settings,
  },
];

export default function AppHeader({ children }: AppHeaderProps) {
  const { user } = useUser();

  return (
    <div className='relative h-full pt-16 antialiased'>
      <div className='fixed left-0 top-0 z-10 h-16 w-full border-b border-gray-200 bg-white px-4 py-2.5 dark:border-gray-700 dark:bg-gray-800'>
        <div className='flex flex-wrap items-center justify-between'>
          <Sheet>
            <SheetTrigger asChild>
              <Button variant='outline' size='icon'>
                <MenuIcon className='h-4 w-4' />
              </Button>
            </SheetTrigger>

            <SheetContent side='left'>
              <SheetHeader className='mb-8'>
                <SheetTitle className='flex items-center gap-1 text-teal-600 dark:text-teal-300'>
                  <Logo className='h-10 w-10' />
                  <span className='text-xl'>Surf Sense</span>
                </SheetTitle>
              </SheetHeader>

              <div className='table min-w-full'>
                <nav className='mb-8 flex flex-col space-y-3'>
                  {NAV_PATHS.map(({ href, label, icon: Icon }) => (
                    <Link
                      key={href}
                      href={href}
                      className='flex items-center gap-1'
                    >
                      <Icon className='h-4 w-4' /> <span>{label}</span>
                    </Link>
                  ))}
                </nav>

                <div className='flex flex-col space-y-3'>
                  <h4 className='font-medium'>Ajuda</h4>
                  {HELP_PATHS.map(({ href, label }) => (
                    <Link
                      key={href}
                      href={href}
                      className='flex items-center gap-1'
                    >
                      <span>{label}</span>
                    </Link>
                  ))}
                </div>
              </div>
            </SheetContent>
          </Sheet>

          <DropdownMenu>
            <DropdownMenuTrigger>
              <UserAvatar />
            </DropdownMenuTrigger>

            <DropdownMenuContent>
              <DropdownMenuLabel>
                <strong className='block max-w-52 truncate font-medium'>
                  {user?.nickname}
                </strong>

                <span className='inline-block max-w-52 truncate'>
                  {user?.name}
                </span>
              </DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuGroup>
                {ACCOUNT_PATHS.map(({ href, label, icon: Icon }) => (
                  <DropdownMenuItem key={href}>
                    <Link href={href} className='flex items-center gap-1'>
                      <Icon className='h-4 w-4' />
                      <span>{label}</span>
                    </Link>
                  </DropdownMenuItem>
                ))}
              </DropdownMenuGroup>
              <DropdownMenuSeparator />
              <DropdownMenuItem>
                <a href='/api/auth/logout' className='flex items-center gap-1'>
                  <LogOutIcon className='h-4 w-4' />
                  Sair
                </a>
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </div>
      </div>
      {children}
    </div>
  );
}
