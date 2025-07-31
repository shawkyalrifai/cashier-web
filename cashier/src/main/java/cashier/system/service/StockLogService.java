package cashier.system.service;

import cashier.system.dto.CategoryDto;
import cashier.system.dto.StockLogDto;
import cashier.system.entity.Category;
import cashier.system.entity.Product;
import cashier.system.entity.StockLog;
import cashier.system.mapper.CategoryMapper;
import cashier.system.mapper.StockLogMapper;
import cashier.system.repository.CategoryRepository;
import cashier.system.repository.ProductRepository;
import cashier.system.repository.StockLogRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Data
public class StockLogService {

    private final StockLogRepository stockLogRepository;
    private final ProductRepository productRepository;

    private final StockLogMapper stockLogMapper;

    public StockLogDto getById(Long id) {
        StockLog stockLog = stockLogRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        return stockLogMapper.toDto(stockLog);
    }
    private StockLog getProductById(Long id) {
        return stockLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }
    public StockLogDto create(StockLogDto stockLogDto) throws IOException {
        // 1. Fetch product
        Product product = productRepository.findById(stockLogDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 2. Update stock
        int newStock = product.getStock() + stockLogDto.getQuantityChanged();
        product.setStock(newStock);
        productRepository.save(product);

        // 3. Save stock log
        StockLog stockLog = new StockLog();
        stockLog.setProduct(product);
        stockLog.setQuantityChanged(stockLogDto.getQuantityChanged());
        stockLog.setReason(stockLogDto.getReason());
        stockLog.setChangeDate(stockLogDto.getChangeDate());

        StockLog savedLog = stockLogRepository.save(stockLog);

        // 4. Return DTO
        StockLogDto dto = new StockLogDto();
        dto.setId(savedLog.getId());
        dto.setProductId(product.getId());
        dto.setProductName(product.getName());
        dto.setQuantityChanged(savedLog.getQuantityChanged());
        dto.setReason(savedLog.getReason());
        dto.setChangeDate(savedLog.getChangeDate());

        return dto;
    }

    public StockLogDto update(Long id, StockLogDto updatedDTO) {
        StockLog existing = stockLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StockLog not found with id: " + id));

        // Fetch the product by productId from the DTO
        Product product = productRepository.findById(updatedDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + updatedDTO.getProductId()));

        // Update fields
        existing.setProduct(product);
        existing.setQuantityChanged(updatedDTO.getQuantityChanged());
        existing.setReason(updatedDTO.getReason());
        existing.setChangeDate(updatedDTO.getChangeDate());

        // Save
        StockLog saved = stockLogRepository.save(existing);

        // Manually map to DTO
        StockLogDto dto = new StockLogDto();
        dto.setId(saved.getId());
        dto.setProductId(saved.getProduct().getId());
        dto.setProductName(saved.getProduct().getName());
        dto.setQuantityChanged(saved.getQuantityChanged());
        dto.setReason(saved.getReason());
        dto.setChangeDate(saved.getChangeDate());

        return dto;
    }

    public void delete(Long id) {
        stockLogRepository.deleteById(id);
    }


    public Optional<StockLogDto> searchCategory(String keyword) {
        Optional<StockLog> found;
        try {
            Long id = Long.parseLong(keyword);
            found = stockLogRepository.findById(id);
        } catch (NumberFormatException e) {
            found = stockLogRepository.findByNameIgnoreCase(keyword);
        }
        return found.map(stockLogMapper::toDto);
    }
}
