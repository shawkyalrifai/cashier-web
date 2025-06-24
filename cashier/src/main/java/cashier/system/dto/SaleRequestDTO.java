package cashier.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class SaleRequestDTO {
    public Long cashierId;
    public List<Item> items;
    public static class Item {
        public Long productId;

        public int quantity;
    }
}
