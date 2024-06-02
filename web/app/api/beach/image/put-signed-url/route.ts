import { r2 } from '@/lib/cloudflare';
import { env } from '@/lib/env';

import { withApiAuthRequired } from '@auth0/nextjs-auth0';
import { PutObjectCommand } from '@aws-sdk/client-s3';
import { getSignedUrl } from '@aws-sdk/s3-request-presigner';
import { NextResponse } from 'next/server';
import { z } from 'zod';

const bodySchema = z.object({
  fileName: z.string().min(1),
  fileType: z.string().min(1),
});

export const POST = withApiAuthRequired(async (request) => {
  try {
    const nonValidatedBody = await request.json();
    const { fileName, fileType } = bodySchema.parse(nonValidatedBody);

    const signedUrl = await getSignedUrl(
      r2,
      new PutObjectCommand({
        Bucket: env.CLOUDFLARE_R2_BUCKET,
        Key: fileName,
        ContentType: fileType,
      }),
      { expiresIn: env.CLOUDFLARE_R2_SIGNED_URL_EXPIRES }
    );

    return NextResponse.json({ signedUrl });
  } catch (error) {
    return new NextResponse(JSON.stringify(error), {
      status: 400,
    });
  }
});
