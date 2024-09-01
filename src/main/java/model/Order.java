package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
    private Buyer buyer;

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

    public enum OrderStatus {
        UNSHIPPED, SHIPPED, CANCELLED
    }
}