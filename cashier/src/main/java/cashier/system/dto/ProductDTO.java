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
    @JsonIgnore // prevent raw byte array from showing
    private byte[] photo;

    // This ensures photoBase64 is included in the response JSON
    @JsonProperty("photoBase64")
    public String getPhotoBase64() {
        return (photo != null) ? Base64.getEncoder().encodeToString(photo) : null;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
