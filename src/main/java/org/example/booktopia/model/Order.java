package org.example.booktopia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.booktopia.base.BaseEntity;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders", schema = "booktopia")
public class Order extends BaseEntity {
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<OrderProduct> orderProducts = new LinkedHashSet<>();
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

    public Order(Buyer buyer) {
        this.buyer = buyer;
    }

    public Order() {
    }
    public void addOrderProduct(OrderProduct orderProduct) {
        if (orderProduct == null) {
            throw new IllegalArgumentException("OrderProduct cannot be null");
        }
        this.orderProducts.add(orderProduct);
    }
    public enum OrderStatus {
        UNSHIPPED, SHIPPED, CANCELLED
    }
}