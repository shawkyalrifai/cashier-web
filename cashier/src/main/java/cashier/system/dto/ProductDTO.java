package cashier.system.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class ProductDTO {
    public Long id;
    public String name;
    public BigDecimal price;
    public int stock;
    private String barcode;
}
