/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: 'https',
        hostname: 'dummyimage.com',
      },
      {
        protocol: 'https',
        hostname: 'oerflhwvnyqxofshetof.supabase.co',
      },
      {
        protocol: 'https',
        hostname:
          'surf-sense.a7f437214aa21e3c51b81c6c04cf81ba.r2.cloudflarestorage.com',
      },
    ],
  },
};

export default nextConfig;
