package cashier.system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
    @Data
    public class Supplier {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String phone;

        @OneToMany(mappedBy = "supplier")
        private List<Product> products;
    }
