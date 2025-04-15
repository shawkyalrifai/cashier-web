package cashier.system.dto;

import java.util.List;

public class SaleRequestDTO {
    public Long cashierId;
    public List<Item> items;
    public static class Item {
        public Long productId;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int quantity;
    }
}
