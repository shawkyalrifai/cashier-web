package cashier.system.service;

import cashier.system.dto.CategoryDto;
import cashier.system.dto.SupplierDto;
import cashier.system.entity.Category;
import cashier.system.entity.Supplier;
import cashier.system.mapper.SupplierMapper;
import cashier.system.repository.SupplierRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Data
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;


    public List<SupplierDto> getAll() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::toDto)
                .collect(Collectors.toList());
    }

    public SupplierDto getById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return supplierMapper.toDto(supplier);
    }
    private Supplier getProductById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + id));
    }
    public SupplierDto create(SupplierDto supplierDto) throws IOException {
        Supplier supplier = supplierMapper.toEntity(supplierDto);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDto(savedSupplier);
    }

    public SupplierDto update(Long id, SupplierDto updatedDTO) {
        Supplier existing = getProductById(id);
        existing.setName(updatedDTO.getName());
        existing.setPhone(updatedDTO.getPhone());
        Supplier saved = supplierRepository.save(existing);
        return supplierMapper.toDto(saved);
    }
    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }


    public Optional<SupplierDto> searchSupplier(String keyword) {
        Optional<Supplier> found;
        try {
            Long id = Long.parseLong(keyword);
            found = supplierRepository.findById(id);
        } catch (NumberFormatException e) {
            found =  supplierRepository.findByNameIgnoreCase(keyword);
        }
        return found.map(supplierMapper::toDto);
    }
}
