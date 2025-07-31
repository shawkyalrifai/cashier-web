package cashier.system.controller;

import cashier.system.dto.StockLogDto;
import cashier.system.service.StockLogService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
@AllArgsConstructor
public class StockController {

    private final StockLogService stockLogService;


    @GetMapping("/{id}")
    public ResponseEntity<StockLogDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(stockLogService.getById(id));
    }


    @PostMapping
    public ResponseEntity<StockLogDto> create(@RequestBody StockLogDto dto) throws IOException {
        StockLogDto created = stockLogService.create(dto);
        return ResponseEntity.ok(created);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StockLogDto> update(@PathVariable Long id, @RequestBody StockLogDto dto) {
        StockLogDto updated = stockLogService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        stockLogService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<StockLogDto> search(@RequestParam String keyword) {
        Optional<StockLogDto> result = stockLogService.searchCategory(keyword);
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
