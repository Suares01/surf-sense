import { query } from './client-axios-instance';

interface SignedUrl {
  signedUrl: string;
}

interface GetBeachImgSignedUrlParams {
  fileName: string;
}

export async function getBeachImgSignedUrl({
  fileName,
}: GetBeachImgSignedUrlParams) {
  const { data } = await query.post<SignedUrl>(
    '/api/beach/image/get-signed-url',
    {
      fileName,
    }
  );

  return data;
}
