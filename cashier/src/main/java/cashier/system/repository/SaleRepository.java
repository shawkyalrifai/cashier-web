package cashier.system.repository;

import cashier.system.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long> {

    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT s FROM Sale s JOIN FETCH s.items i JOIN FETCH i.product")
    List<Sale> findAllWithItems();


}
