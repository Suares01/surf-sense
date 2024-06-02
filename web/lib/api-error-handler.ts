import type { AppRouteHandlerFn } from '@auth0/nextjs-auth0';
import axios from 'axios';
import { NextResponse } from 'next/server';

export function apiErrorHandler(handler: AppRouteHandlerFn): AppRouteHandlerFn {
  return (request, response) => {
    try {
      return handler(request, response);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        return new NextResponse(error.message, {
          status: error.status,
        });
      }

      return new NextResponse('Internal Error', {
        status: 500,
      });
    }
  };
}
