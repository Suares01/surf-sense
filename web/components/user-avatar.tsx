'use client';

import { useUser } from '@auth0/nextjs-auth0/client';

import { Avatar, AvatarFallback, AvatarImage } from './ui/avatar';

export default function UserAvatar() {
  const { user } = useUser();

  return (
    <Avatar>
      <AvatarImage
        src={user?.picture || undefined}
        alt={user?.name || undefined}
      />
      <AvatarFallback>{user?.name?.at(1)?.toLocaleUpperCase()}</AvatarFallback>
    </Avatar>
  );
}
