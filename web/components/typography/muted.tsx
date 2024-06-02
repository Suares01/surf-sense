import { cn } from '@/lib/utils';

import type { DetailedHTMLProps, HTMLAttributes } from 'react';

export interface MutedProps
  extends DetailedHTMLProps<
    HTMLAttributes<HTMLParagraphElement>,
    HTMLParagraphElement
  > {}

export default function Muted({ children, className, ...rest }: MutedProps) {
  return (
    <p className={cn('text-sm text-muted-foreground', className)} {...rest}>
      {children}
    </p>
  );
}
