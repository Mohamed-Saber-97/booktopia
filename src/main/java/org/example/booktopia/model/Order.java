package org.example.booktopia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.booktopia.base.BaseEntity;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "booktopia")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Order Status is required")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNSHIPPED;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<OrderProduct> orderProducts = new HashSet<>();

    public enum OrderStatus {
        UNSHIPPED, SHIPPED, CANCELLED
    }

}