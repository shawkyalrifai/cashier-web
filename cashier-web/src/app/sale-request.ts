import {SaleItemRequest} from "./sale-item-request";

export interface SaleRequest {

  productId: number;
  quantity: number;
}

export interface SaleRequest {
  cashierId: number;
  items: SaleItemRequest[];
}

