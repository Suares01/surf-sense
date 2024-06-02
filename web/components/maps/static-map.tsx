'use client';

import { Marker, Popup, useMap } from 'react-leaflet';

import type { Coordinate } from './coordinate';
import mapMakerIcon from './map-maker-icon';
import MapProvider from './map-provider';

import 'leaflet/dist/leaflet.css';

export interface StaticMapProps {
  position: Coordinate;
  beachName: string;
  whenReady?: () => void;
}

type MapMakerProps = Pick<StaticMapProps, 'position' | 'beachName'>;

function MapMaker({ position, beachName }: MapMakerProps) {
  const map = useMap();

  map.dragging.disable();
  map.touchZoom.disable();
  map.doubleClickZoom.disable();
  map.scrollWheelZoom.disable();
  map.boxZoom.disable();
  map.keyboard.disable();
  if (map.tap) map.tap.disable();

  return (
    <Marker icon={mapMakerIcon} position={position}>
      <Popup>{beachName}</Popup>
    </Marker>
  );
}

export default function StaticMap({
  whenReady,
  beachName,
  position,
}: StaticMapProps) {
  return (
    <MapProvider
      whenReady={whenReady}
      center={position}
      zoom={15}
      zoomControl={false}
      className='h-full w-full cursor-default'
    >
      <MapMaker beachName={beachName} position={position} />
    </MapProvider>
  );
}
