package cashier.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class SaleDTO {
    private Long id;
    private String cashierUsername;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private String customerPhone;
    private String customerName;
    private List<SaleItemDTO> items;
}
