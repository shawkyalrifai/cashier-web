import {Product} from "./product";
import {SaleRequest} from "./sale-request";

export interface SaleItemRequest {
  id?: number;             // Optional if auto-generated
  product: Product;
  sale?: SaleRequest;             // Optional if only set server-side
  quantity: number;
  subtotal: number;
}
