package cashier.system.controller;

import cashier.system.dto.SaleRequestDTO;
import cashier.system.entity.Sale;
import cashier.system.service.SaleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class SaleController {


    private  final SaleService saleService;

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody SaleRequestDTO saleRequestDTO) {
        return new ResponseEntity<>(saleService.createSale(saleRequestDTO),HttpStatus.OK);
    }

    @GetMapping
    public List<Sale> getSales() {
        return saleService.getAllSales();
    }

    @GetMapping("/{id}")
    public Sale getSale(@PathVariable Long id) {
        return saleService.getSaleById(id);
    }


    @GetMapping("/total-between")
    public ResponseEntity<BigDecimal> getSalesTotalBetween(
            @RequestParam("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam("endDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {

        BigDecimal total = saleService.getSalesTotalBetween(startDateTime, endDateTime);
        return ResponseEntity.ok(total);
    }

//    @GetMapping("/total-amount/day")
//    public ResponseEntity<BigDecimal> getSalesTotalForDay(@RequestParam("date") String date) {
//        LocalDate localDate = LocalDate.parse(date);
//        BigDecimal totalSales = saleService.getSalesTotalForDay(localDate);
//        return ResponseEntity.ok(totalSales);  // Return the total sales amount
//    }
//
//    @GetMapping("/total-amount/week")
//    public ResponseEntity<BigDecimal> getSalesTotalForWeek(@RequestParam("date") String date) {
//        LocalDate localDate = LocalDate.parse(date); // Parse the date to LocalDate
//        BigDecimal totalSales = saleService.getSalesTotalByWeek(localDate); // Get the total sales for the week
//        return ResponseEntity.ok(totalSales);  // Return the total sales amount
//    }


//    @GetMapping("/total-amount/month")
//    public ResponseEntity<BigDecimal> getSalesTotalForMonth(@RequestParam("year") int year, @RequestParam("month") int month) {
//        BigDecimal totalSales = saleService.getSalesTotalByMonth(year, month); // Get the total sales for the month
//        return ResponseEntity.ok(totalSales);  // Return the total sales amount
//    }
}

