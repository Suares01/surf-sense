import { query } from './client-axios-instance';

interface SignedUrl {
  signedUrl: string;
}

interface PutBeachImgSignedUrlParams {
  fileName: string;
  fileType: string;
}

export async function putBeachImgSignedUrl({
  fileName,
  fileType,
}: PutBeachImgSignedUrlParams) {
  const { data } = await query.post<SignedUrl>(
    '/api/beach/image/put-signed-url',
    {
      fileName,
      fileType,
    }
  );

  return data;
}
