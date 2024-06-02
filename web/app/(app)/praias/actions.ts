'use server';

import { env } from '@/lib/env';
import { supabase } from '@/lib/supabase';

export async function uploadBeachImage(data: FormData) {
  const storage = supabase.storage.from(env.SUPABASE_BUCKET);

  const fileName = data.get('fileName')?.toString();
  const file = data.get('file');

  if (!file || !fileName) return undefined;

  const { error } = await storage.upload(fileName, file, { upsert: true });

  if (error) {
    throw new Error(error.message);
  }

  const {
    data: { publicUrl },
  } = storage.getPublicUrl(fileName);

  return publicUrl;
}
