import dynamic from 'next/dynamic';

const InteractiveMap = dynamic(
  () => import('@/components/maps/interactive-map'),
  { ssr: false }
);

export default function CreateBeachPage() {
  return <InteractiveMap />;
}
