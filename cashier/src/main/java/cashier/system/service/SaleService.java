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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;

    private  final UserRepository userRepository;

    private final ProductRepository productRepository;

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


    public BigDecimal getSalesTotalBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDateTime, endDateTime);
        return sales.stream()
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
