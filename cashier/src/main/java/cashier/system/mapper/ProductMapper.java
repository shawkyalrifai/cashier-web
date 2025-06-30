package cashier.system.mapper;

import cashier.system.dto.ProductDTO;
import cashier.system.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setBarcode(product.getBarcode());
        dto.setPhoto(product.getPhoto()); // this automatically sets photoBase64
        return dto;
    }


    // Convert DTO to Entity
    public Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setBarcode(dto.getBarcode());
        product.setPhoto(dto.getPhoto());
        return product;
    }
}
