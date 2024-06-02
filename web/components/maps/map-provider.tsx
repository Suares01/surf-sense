'use client';

import type { PropsWithChildren } from 'react';
import { MapContainer, type MapContainerProps, TileLayer } from 'react-leaflet';

export interface MapProviderProps
  extends PropsWithChildren,
    MapContainerProps {}

export default function MapProvider({ children, ...props }: MapProviderProps) {
  return (
    <MapContainer {...props}>
      <TileLayer
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        url='https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png'
      />
      {children}
    </MapContainer>
  );
}
