package cashier.system.repository;

import cashier.system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Optional<Product> findById(Long id);
    Optional<Product> findByNameIgnoreCase(String name);
    Optional<Product> findByBarcode(String barcode);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) = LOWER(:keyword) OR CAST(p.id AS string) = :keyword OR p.barcode = :keyword")
    Optional<Product> searchByKeyword(@Param("keyword") String keyword);
}
