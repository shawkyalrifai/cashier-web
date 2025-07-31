package cashier.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class StockLogDto {
    private Long id;
    private Long productId;
    private String productName;
    private int quantityChanged;
    private String reason;
    private LocalDateTime changeDate;
}
