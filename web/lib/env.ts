import { z } from 'zod';

const envScheme = z.object({
  AUTH0_SECRET: z.string(),
  AUTH0_BASE_URL: z.string().url(),
  AUTH0_ISSUER_BASE_URL: z.string().url(),
  AUTH0_CLIENT_ID: z.string(),
  AUTH0_CLIENT_SECRET: z.string(),
  AUTH0_AUDIENCE: z.string().url(),
  AUTH0_RETURN_TO: z.string(),
  CLOUDFLARE_ENDPOINT: z.string().url(),
  CLOUDFLARE_ACCESS_KEY_ID: z.string(),
  CLOUDFLARE_SECRET_ACCESS_KEY: z.string(),
  CLOUDFLARE_R2_BUCKET: z.string(),
  CLOUDFLARE_R2_SIGNED_URL_EXPIRES: z.string().transform(Number),
  API_URL: z.string().url(),
  SUPABASE_URL: z.string().url(),
  SUPABASE_ANON_KEY: z.string(),
  SUPABASE_BUCKET: z.string(),
});

export const env = envScheme.parse(process.env);
