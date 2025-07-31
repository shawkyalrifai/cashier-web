import { SaleItemRequest } from "./sale-item-request";


export interface SaleRequest {

 customerPhone: string;
 customerName: String;
  items: SaleItemRequest[];
}


