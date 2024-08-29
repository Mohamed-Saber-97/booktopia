package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;


@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends BaseEntity<Long> {

    @Column(name = "status",nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNSHIPPED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    public enum OrderStatus {
        UNSHIPPED, SHIPPED, CANCELLED
    }
}