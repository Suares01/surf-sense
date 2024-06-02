import { withMiddlewareAuthRequired } from '@auth0/nextjs-auth0/edge';

export const middleware = withMiddlewareAuthRequired({
  returnTo: '/',
});

export const config = {
  matcher: ['/previsoes', '/perfil'],
};
