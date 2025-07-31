package cashier.system.controller;

import cashier.system.dto.CategoryDto;
import cashier.system.dto.ProductDTO;
import cashier.system.repository.CategoryRepository;
import cashier.system.service.CategoryService;
import cashier.system.service.StockLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;


    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
           return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.getAll();
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) throws IOException {
        CategoryDto created = categoryService.create(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        CategoryDto updated = categoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<CategoryDto> search(@RequestParam String keyword) {
        Optional<CategoryDto> result = categoryService.searchCategory(keyword);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
