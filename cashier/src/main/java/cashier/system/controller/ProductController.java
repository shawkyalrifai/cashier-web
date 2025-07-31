package cashier.system.controller;

import cashier.system.dto.ProductDTO;
import cashier.system.entity.Category;
import cashier.system.entity.Product;
import cashier.system.mapper.ProductMapper;
import cashier.system.repository.CategoryRepository;
import cashier.system.repository.ProductRepository;
import cashier.system.service.CategoryService;
import cashier.system.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

   private final ProductService productService;
   private final CategoryRepository categoryRepository;
   private final ProductRepository productRepository;
   private final ProductMapper productMapper;
//    @Autowired
//    private ProductService productService;


    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id)
    {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
    }
    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAll();
    }

    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @ModelAttribute ProductDTO productDTO,
            @RequestPart("multipartFile") MultipartFile multipartFile) throws IOException{

        return new  ResponseEntity<>(productService.create(productDTO,multipartFile),HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>( productService.update(id, productDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchProduct(@RequestParam("keyword") String keyword) {
        Optional<ProductDTO> product = productService.searchProduct(keyword);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Product not found with ID or Name: " + keyword);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @GetMapping("/search/category")
    public ResponseEntity<List<ProductDTO>> searchProductsByCategory(@RequestParam String name) {
        Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        List<Product> products = productRepository.findAllByCategoryId(category.getId());
        return ResponseEntity.ok(
                products.stream().map(productMapper::toDto).toList()
        );
    }
}

