package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "orders")
@Getter
@EqualsAndHashCode(callSuper = true, exclude = "buyer")
@ToString(exclude = "buyer")
@NoArgsConstructor
public class Order extends BaseEntity<Long> {

    @Column(name = "status", nullable = false)
    @NotNull(message = "Order Status is required")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNSHIPPED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    @Setter
    private Buyer buyer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<OrderProduct> orderProducts = new HashSet<>();

    public Order(Buyer buyer) {
        if (buyer == null) {
            throw new IllegalArgumentException("Buyer cannot be null");
        }
        this.buyer = buyer;
    }

    public void updateStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("New status cannot be null");
        }
        this.status = newStatus;
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