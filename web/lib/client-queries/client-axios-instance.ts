import axios from 'axios';

import { getUrl } from '../utils';

const BASE_URL = getUrl();

export const query = axios.create({
  baseURL: BASE_URL,
});
