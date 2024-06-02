'use server';

import { r2 } from '@/lib/cloudflare';
import { env } from '@/lib/env';

import { GetObjectCommand } from '@aws-sdk/client-s3';
import { getSignedUrl } from '@aws-sdk/s3-request-presigner';

export async function getBeachImageUrl(fileName: string) {
  const signedUrl = await getSignedUrl(
    r2,
    new GetObjectCommand({
      Bucket: env.CLOUDFLARE_R2_BUCKET,
      Key: fileName,
    }),
    { expiresIn: env.CLOUDFLARE_R2_SIGNED_URL_EXPIRES }
  );

  return signedUrl;
}
