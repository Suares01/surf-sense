import { cn } from '@/lib/utils';

import type { DetailedHTMLProps, HTMLAttributes } from 'react';

export interface H3Props
  extends DetailedHTMLProps<
    HTMLAttributes<HTMLHeadingElement>,
    HTMLHeadingElement
  > {}

export default function H3({ children, className, ...rest }: H3Props) {
  return (
    <h3
      className={cn(
        'scroll-m-20 text-2xl font-semibold tracking-tight',
        className
      )}
      {...rest}
    >
      {children}
    </h3>
  );
}
