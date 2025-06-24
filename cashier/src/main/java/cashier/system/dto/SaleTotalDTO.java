package cashier.system.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Data
public class SaleTotalDTO {
    private Long id;


    private BigDecimal totalAmount;
}
