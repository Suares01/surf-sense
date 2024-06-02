import { cn } from '@/lib/utils';

import type { DetailedHTMLProps, HTMLAttributes } from 'react';

export interface H4Props
  extends DetailedHTMLProps<
    HTMLAttributes<HTMLHeadingElement>,
    HTMLHeadingElement
  > {}

export default function H4({ children, className, ...rest }: H4Props) {
  return (
    <h3
      className={cn(
        'scroll-m-20 text-xl font-semibold tracking-tight',
        className
      )}
      {...rest}
    >
      {children}
    </h3>
  );
}
