package cashier.system.dto;

import java.math.BigDecimal;

public class SaleTotalDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public SaleTotalDTO(Long id, BigDecimal totalAmount) {
        this.id = id;
        this.totalAmount = totalAmount;
    }

    private BigDecimal totalAmount;
}
