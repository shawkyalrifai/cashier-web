package cashier.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Base64;

@Setter
@Getter
public class ProductDTO {
    public Long id;
    public String name;
    public BigDecimal price;
    public int stock;
    private String barcode;
    @JsonIgnore
    private byte[] photo;
    @JsonProperty("photoBase64")
    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    private String supplierName;
    private Long supplierId;
    private String categoryName;
    private Long categoryId;
}
