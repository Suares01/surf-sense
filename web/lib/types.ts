export enum Position {
  NORTE = 'NORTH',
  SUL = 'SOUTH',
  LESTE = 'EAST',
  OESTE = 'WEST',
}

export interface Beach {
  id: string;
  name: string;
  lat: number;
  lng: number;
  userId: string;
  position: Position;
  image?: string;
  imageBlurDataUrl?: string;
  createdAt: string;
}

export interface ApiError {
  status: number;
  reason: string;
  message: string;
  path: string;
}
