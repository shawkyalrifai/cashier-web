import {Product} from "./product";
import {SaleRequest} from "./sale-request";

export interface SaleItemRequest {
    productId: number;
  quantity: number;
  subtotal :number;
}
