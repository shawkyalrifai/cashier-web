package cashier.system.mapper;

import cashier.system.dto.CategoryDto;
import cashier.system.dto.SupplierDto;
import cashier.system.entity.Category;
import cashier.system.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierDto toDto(Supplier supplier) {
        if (supplier == null) return null;

        SupplierDto dto = new SupplierDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setPhone(supplier.getPhone());
        return dto;
    }

    public Supplier toEntity(SupplierDto dto) {
        if (dto == null) return null;

        Supplier supplier = new Supplier();
        supplier.setId(dto.getId()); // optional
        supplier.setName(dto.getName());
        supplier.setPhone(dto.getPhone());
        return supplier;
    }
}

