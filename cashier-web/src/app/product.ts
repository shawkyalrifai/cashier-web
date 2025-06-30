export interface Product {
  id?: number;
  name: string;
  barcode: string;
  available: boolean;
  price: number;
  stock: number;
  photo?: Uint8Array;
  photoBase64?: string;
}
