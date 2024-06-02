'use client';

import { type LatLng } from 'leaflet';
import { geocoder as geocoderFactory } from 'leaflet-control-geocoder';
import { useEffect, useState } from 'react';
import { Marker, Popup, useMapEvents } from 'react-leaflet';
import Control from 'react-leaflet-custom-control';

import CreateBeachForm from '../create-beach-form';
import { DialogTrigger } from '../ui/dialog';
import type { Coordinate } from './coordinate';
import mapMakerIcon from './map-maker-icon';
import MapProvider from './map-provider';

import 'leaflet/dist/leaflet.css';
import 'leaflet-control-geocoder/dist/Control.Geocoder.css';
import 'leaflet-control-geocoder/dist/Control.Geocoder.js';

export interface OnSearchData extends Coordinate {
  name?: string;
  city: string;
  country: string;
  countryCode: string;
}

export interface InteractiveMapProps {
  onInvalidAddressType?: () => void;
  whenReady?: () => void;
}

type MapMakerProps = Pick<InteractiveMapProps, 'onInvalidAddressType'>;

function MapMaker({ onInvalidAddressType }: MapMakerProps) {
  const [position, setPosition] = useState<LatLng | null>(null);
  const [data, setData] = useState<OnSearchData>();

  const map = useMapEvents({
    locationfound(event) {
      setPosition(event.latlng);
      map.fitBounds(event.bounds);
      map.setZoom(15);
    },
  });

  useEffect(() => {
    map.locate();

    const geocoder = geocoderFactory({
      defaultMarkGeocode: false,
      placeholder: 'Procurar',
    });

    geocoder.on('markgeocode', (event) => {
      const properties = event.geocode.properties;
      const addresstype: string = properties.addresstype;

      if (addresstype === 'beach' || addresstype === 'tourism') {
        const latLng = event.geocode.center;
        const { lat, lng } = latLng;

        const name: string | undefined =
          addresstype === 'beach'
            ? properties.address.natural
            : addresstype === 'tourism'
              ? properties.address.tourism
              : undefined;
        const city: string = properties.address.city;
        const country: string = properties.address.country;
        const countryCode: string = properties.address.country_code;

        setPosition(latLng);
        setData({ lat, lng, name, city, country, countryCode });
        map.fitBounds(event.geocode.bbox);
        map.setZoom(15);
      } else {
        if (onInvalidAddressType) onInvalidAddressType();
      }
    });

    geocoder.addTo(map);

    return () => {
      map.removeControl(geocoder);
    };
  }, [map, onInvalidAddressType]);

  return (
    <>
      {position === null ? null : (
        <Marker icon={mapMakerIcon} position={position}>
          <Popup>{data?.name}</Popup>
        </Marker>
      )}
      <CreateBeachForm data={data}>
        <Control prepend position='bottomleft'>
          <DialogTrigger asChild>
            <button
              className='leaflet-bar block cursor-pointer bg-white px-4 py-2 font-medium'
              type='button'
            >
              Criar
            </button>
          </DialogTrigger>
        </Control>
      </CreateBeachForm>
    </>
  );
}

export default function InteractiveMap({
  whenReady,
  onInvalidAddressType,
}: InteractiveMapProps) {
  return (
    <MapProvider
      whenReady={whenReady}
      center={[0, 0]}
      zoom={15}
      scrollWheelZoom={true}
      className='z-0 h-full w-full'
    >
      <MapMaker onInvalidAddressType={onInvalidAddressType} />
    </MapProvider>
  );
}
