package cashier.system.mapper;

import cashier.system.dto.CategoryDto;
import cashier.system.dto.StockLogDto;
import cashier.system.entity.Category;
import cashier.system.entity.Product;
import cashier.system.entity.StockLog;
import org.springframework.stereotype.Component;

@Component
public class StockLogMapper {

    public  StockLogDto toDto(StockLog log) {
        if (log == null) return null;

        StockLogDto dto = new StockLogDto();
        dto.setId(log.getId());
        dto.setProductId(log.getProduct() != null ? log.getProduct().getId() : null);
        dto.setProductName(log.getProduct() != null ? log.getProduct().getName() : null);
        dto.setQuantityChanged(log.getQuantityChanged());
        dto.setReason(log.getReason());
        dto.setChangeDate(log.getChangeDate());
        return dto;
    }

    // DTO to Entity (product will be set manually later in service)
    public static StockLog toEntity(StockLogDto dto) {
        if (dto == null) return null;

        StockLog log = new StockLog();
        log.setId(dto.getId());
        log.setQuantityChanged(dto.getQuantityChanged());
        log.setReason(dto.getReason());
        log.setChangeDate(dto.getChangeDate());

        // Optional: set a Product stub with only ID (recommended to set the full object in service)
        if (dto.getProductId() != null) {
            Product product = new Product();
            product.setId(dto.getProductId());
            log.setProduct(product);
        }

        return log;
    }
}
