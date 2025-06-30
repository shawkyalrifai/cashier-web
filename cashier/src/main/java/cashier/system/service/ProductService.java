package cashier.system.service;

import cashier.system.dto.ProductDTO;
import cashier.system.entity.Product;
import cashier.system.mapper.ProductMapper;
import cashier.system.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    private final ProductMapper productMapper;


    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }
    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setBarcode(product.getBarcode());
        dto.setPhoto(product.getPhoto());
        return dto;
    }

    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return productMapper.toDto(product);
    }
    private Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }
    public ProductDTO create(ProductDTO productDTO, MultipartFile multipartFile) throws IOException {


        if (productRepository.existsByName(productDTO.getName())) {
            throw new IllegalArgumentException("Product name must be unique.");
        }
        if (productRepository.existsByBarcode(productDTO.getBarcode())) {
            throw new IllegalArgumentException("Product barcode must be unique.");
        }
        Product product = productMapper.toEntity(productDTO);
        if (multipartFile != null) {
            product.setPhoto(multipartFile.getBytes());
        }
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDTO update(Long id, ProductDTO updatedDTO) {
        Product existing = getProductById(id);
        existing.setName(updatedDTO.getName());
        existing.setPrice(updatedDTO.getPrice());
        existing.setStock(updatedDTO.getStock());
        existing.setBarcode(updatedDTO.getBarcode());
        Product saved = productRepository.save(existing);
        return productMapper.toDto(saved);
    }
    public void delete(Long id) {
        productRepository.deleteById(id);
    }


        public Optional<ProductDTO> searchProduct(String keyword) {
            Optional<Product> found;
            try {
                Long id = Long.parseLong(keyword);
                found = productRepository.findById(id);
            } catch (NumberFormatException e) {
                found = productRepository.findByNameIgnoreCase(keyword)
                        .or(() -> productRepository.findByBarcode(keyword));
            }
            return found.map(productMapper::toDto);
        }
}

