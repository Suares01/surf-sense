import { cn } from '@/lib/utils';

import type { DetailedHTMLProps, HTMLAttributes } from 'react';

export interface PProps
  extends DetailedHTMLProps<
    HTMLAttributes<HTMLParagraphElement>,
    HTMLParagraphElement
  > {}

export default function P({ children, className, ...rest }: PProps) {
  return (
    <p
      className={cn('leading-7 [&:not(:first-child)]:mt-6', className)}
      {...rest}
    >
      {children}
    </p>
  );
}
