import { env } from '@/lib/env';

import { handleAuth, handleLogin } from '@auth0/nextjs-auth0';

export const GET = handleAuth({
  login: handleLogin({
    authorizationParams: {
      audience: env.AUTH0_AUDIENCE,
    },
    returnTo: env.AUTH0_RETURN_TO,
  }),
});
