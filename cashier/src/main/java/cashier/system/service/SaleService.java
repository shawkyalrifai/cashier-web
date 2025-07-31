package cashier.system.service;

import cashier.system.dto.SaleDTO;
import cashier.system.dto.SaleItemDTO;
import cashier.system.dto.SaleRequestDTO;
import cashier.system.dto.SaleResponseDTO;
import cashier.system.entity.Product;
import cashier.system.entity.Sale;
import cashier.system.entity.SaleItem;
import cashier.system.entity.User;
import cashier.system.exception.ResourceNotFoundException;
import cashier.system.repository.ProductRepository;
import cashier.system.repository.SaleRepository;
import cashier.system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public SaleDTO createSale(SaleRequestDTO dto, String username) {
        User cashier = userRepository.findByUsername(username)
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
                product.setAvailable(false);
            }

            productRepository.save(product);
        }

        Sale sale = new Sale();
        sale.setCashier(cashier);
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotalAmount(total);
        sale.setItems(saleItems);
        sale.setCustomerPhone(dto.getCustomerPhone());
        sale.setCustomerName(dto.getCustomerName());

        saleItems.forEach(i -> i.setSale(sale));
        Sale saved = saleRepository.save(sale);

        return convertToDTO(saved);
    }

    public SaleDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
        return convertToDTO(sale);
    }


    public BigDecimal getSalesTotalBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDateTime, endDateTime);
        return sales.stream()
                .map(Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private SaleDTO convertToDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setId(sale.getId());
        dto.setCashierUsername(sale.getCashier().getUsername());
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setCustomerPhone(sale.getCustomerPhone());
        dto.setCustomerName(sale.getCustomerName());

        List<SaleItemDTO> itemDTOs = sale.getItems().stream().map(item -> {
            SaleItemDTO itemDTO = new SaleItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductName(item.getProduct().getName());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setSubtotal(item.getSubtotal());
            return itemDTO;
        }).collect(Collectors.toList());

        dto.setItems(itemDTOs);
        return dto;
    }
    public List<SaleResponseDTO> getAllSales() {
        List<Sale> sales = saleRepository.findAllWithItems();

        return sales.stream().map(sale -> {
            SaleResponseDTO dto = new SaleResponseDTO();
            dto.setId(sale.getId());
            dto.setCashierUsername(sale.getCashier().getUsername());
            dto.setSaleDate(sale.getSaleDate());
            dto.setTotalAmount(sale.getTotalAmount());
            dto.setCustomerPhone(sale.getCustomerPhone());
            dto.setCustomerName(sale.getCustomerName());

            List<SaleResponseDTO.SaleItemDTO> items = sale.getItems().stream().map(item -> {
                SaleResponseDTO.SaleItemDTO itemDTO = new SaleResponseDTO.SaleItemDTO();
                itemDTO.setProductName(item.getProduct().getName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setUnitPrice(item.getProduct().getPrice());
                itemDTO.setSubtotal(item.getSubtotal());
                return itemDTO;
            }).toList();

            dto.setItems(items);
            return dto;
        }).toList();
    }



}