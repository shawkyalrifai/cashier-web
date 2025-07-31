package cashier.system.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class SaleResponseDTO {
    private Long id;
    private String cashierUsername;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private String customerPhone;
    private String customerName;
    private List<SaleItemDTO> items;

    @Data
    public static class SaleItemDTO {
        private String productName;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
    }
}
