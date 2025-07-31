package cashier.system.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SaleRequestDTO {
    public List<Item> items;
    public String customerPhone;
    public String customerName;

    @Data
    public static class Item {
        public Long productId;
        public int quantity;
    }
}
