package cashier.system.service;

import cashier.system.dto.ProductDTO;
import cashier.system.entity.Category;
import cashier.system.entity.Product;
import cashier.system.entity.Supplier;
import cashier.system.mapper.ProductMapper;
import cashier.system.repository.CategoryRepository;
import cashier.system.repository.ProductRepository;
import cashier.system.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

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
        if (product.getSupplier() != null) {
            dto.setSupplierId(product.getSupplier().getId());
            dto.setSupplierName(product.getSupplier().getName());
        }


        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
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


        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        Product product = productMapper.toEntity(productDTO);


        product.setSupplier(supplier);
        product.setCategory(category);

        if (multipartFile != null && !multipartFile.isEmpty()) {
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

    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = productRepository.findAllByCategoryId(category.getId());

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
}

