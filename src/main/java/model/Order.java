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
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNSHIPPED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    public Order(Buyer buyer) {
        this.buyer = buyer;
    }

    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
    }

    public enum OrderStatus {
        UNSHIPPED, SHIPPED, CANCELLED
    }
}