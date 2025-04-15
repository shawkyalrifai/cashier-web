package cashier.system.service;

import cashier.system.dto.SaleRequestDTO;
import cashier.system.entity.Product;
import cashier.system.entity.Sale;
import cashier.system.entity.SaleItem;
import cashier.system.entity.User;
import cashier.system.exception.ResourceNotFoundException;
import cashier.system.repository.ProductRepository;
import cashier.system.repository.SaleRepository;
import cashier.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {
@Autowired
    private SaleRepository saleRepository;
@Autowired
    private  UserRepository userRepository;
@Autowired
    private  ProductRepository productRepository;

    public Sale createSale(SaleRequestDTO dto) {
        User cashier = userRepository.findById(dto.cashierId)
                .orElseThrow(() -> new ResourceNotFoundException("Cashier not found"));

        List<SaleItem> saleItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (SaleRequestDTO.Item item : dto.items) {
            Product product = productRepository.findById(item.productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStock() < item.quantity) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.quantity));
            SaleItem saleItem = new SaleItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(item.quantity);
            saleItem.setSubtotal(subtotal);
            saleItems.add(saleItem);

            total = total.add(subtotal);
            product.setStock(product.getStock() - item.quantity);
            if (product.getStock() == 0) {
                // Optionally, disable the product if stock is zero
                product.setAvailable(false);  // Assuming you have an `available` flag
            }
            productRepository.save(product);
        }

        Sale sale = new Sale();
        sale.setCashier(cashier);
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotalAmount(total);
        sale.setItems(saleItems);
        saleItems.forEach(i -> i.setSale(sale));

        return saleRepository.save(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Sale getSaleById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
    }



    public BigDecimal getSalesTotalForDay(LocalDate date) {
        // Calculate the start and end of the day
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusSeconds(1);

        // Get all sales for the day
        List<Sale> sales = saleRepository.findBySaleDateBetween(startOfDay, endOfDay);

        // Sum up the total sales amount for the day
        return sales.stream()
                .map(Sale::getTotalAmount)  // Get the BigDecimal totalAmount of each sale
                .reduce(BigDecimal.ZERO, BigDecimal::add);  // Sum the BigDecimal values
    }


    public BigDecimal getSalesTotalByWeek(LocalDate date) {
        // Calculate the start and end of the week
        WeekFields weekFields = WeekFields.of(java.util.Locale.getDefault());
        LocalDate startOfWeek = date.with(weekFields.dayOfWeek(), 1); // Monday of the week
        LocalDate endOfWeek = startOfWeek.plusWeeks(1).minusDays(1); // Sunday of the week

        // Convert LocalDate to LocalDateTime for querying the database
        LocalDateTime startOfWeekTime = startOfWeek.atStartOfDay();
        LocalDateTime endOfWeekTime = endOfWeek.atTime(23, 59, 59);

        // Fetch sales data within the week range
        List<Sale> sales = saleRepository.findBySaleDateBetween(startOfWeekTime, endOfWeekTime);

        // Sum up the total sales amount for the week
        BigDecimal totalSales = sales.stream()
                .map(Sale::getTotalAmount) // Get the totalAmount for each sale
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum them up

        return totalSales;
    }



    public BigDecimal getSalesTotalByMonth(int year, int month) {
        // Calculate the start and end of the month
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        // Convert LocalDate to LocalDateTime for querying the database
        LocalDateTime startOfMonthTime = startOfMonth.atStartOfDay();
        LocalDateTime endOfMonthTime = endOfMonth.atTime(23, 59, 59);

        // Fetch sales data within the month range
        List<Sale> sales = saleRepository.findBySaleDateBetween(startOfMonthTime, endOfMonthTime);

        // Sum up the total sales amount for the month
        BigDecimal totalSales = sales.stream()
                .map(Sale::getTotalAmount) // Get the totalAmount for each sale
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum them up

        return totalSales;
    }


}
