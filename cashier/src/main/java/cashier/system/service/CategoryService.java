package cashier.system.service;

import cashier.system.dto.CategoryDto;
import cashier.system.dto.ProductDTO;
import cashier.system.entity.Category;
import cashier.system.entity.Product;
import cashier.system.mapper.CategoryMapper;
import cashier.system.repository.CategoryRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class CategoryService {

    private final CategoryRepository  categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toDto(category);
    }
    private Category getProductById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }
    public CategoryDto create(CategoryDto categoryDto) throws IOException {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    public CategoryDto update(Long id, CategoryDto updatedDTO) {
        Category existing = getProductById(id);
        existing.setName(updatedDTO.getName());
        Category saved = categoryRepository.save(existing);
        return categoryMapper.toDto(saved);
    }
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }


    public Optional<CategoryDto> searchCategory(String keyword) {
        Optional<Category> found;
        try {
            Long id = Long.parseLong(keyword);
            found = categoryRepository.findById(id);
        } catch (NumberFormatException e) {
            found = categoryRepository.findByNameIgnoreCase(keyword);
        }
        return found.map(categoryMapper::toDto);
    }

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

}
