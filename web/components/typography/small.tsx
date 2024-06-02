import { cn } from '@/lib/utils';

import type { DetailedHTMLProps, HTMLAttributes } from 'react';

export interface SmallProps
  extends DetailedHTMLProps<HTMLAttributes<HTMLElement>, HTMLElement> {}

export default function Small({ children, className, ...rest }: SmallProps) {
  return (
    <small
      className={cn('text-sm font-medium leading-none', className)}
      {...rest}
    >
      {children}
    </small>
  );
}
