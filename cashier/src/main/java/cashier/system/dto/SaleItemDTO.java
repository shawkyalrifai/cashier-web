package cashier.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SaleItemDTO {
    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal subtotal;
}
