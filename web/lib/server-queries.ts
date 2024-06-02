import { getAccessToken } from '@auth0/nextjs-auth0';
import axios from 'axios';

import { env } from './env';

function createAxiosInstance(accessToken?: string) {
  const query = axios.create({
    baseURL: env.API_URL,
  });

  query.interceptors.request.use((config) => {
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  });

  return query;
}

export async function getQuery() {
  const { accessToken } = await getAccessToken();
  // console.log(accessToken);
  return createAxiosInstance(accessToken);
}
