package cashier.system.mapper;

import cashier.system.dto.CategoryDto;
import cashier.system.dto.ProductDTO;
import cashier.system.entity.Category;
import cashier.system.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) return null;

        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }


    // Convert DTO to Entity
    public Category toEntity(CategoryDto dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        return category;
    }
}
