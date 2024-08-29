package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_product")
@IdClass(OrderProductId.class)
@Getter
@Setter
public class OrderProduct {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    @NotNull
    private final Instant createdDate = Instant.now();
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_on", nullable = false)
    @NotNull
    private final Instant lastUpdatedOn = Instant.now();
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "quantity")
    @Min(value = 0)
    private Integer quantity;
    @Column(name = "price")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
    @Transient
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalPrice;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;
}
