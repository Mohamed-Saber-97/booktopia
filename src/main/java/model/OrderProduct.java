package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "order_product")
@IdClass(OrderProductId.class)
@Getter
@EqualsAndHashCode(exclude = {"order", "product"})
@ToString(exclude = {"order", "product"})
@NoArgsConstructor
public class OrderProduct {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    @NotNull
    private final Instant createdDate = Instant.now();
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_updated_on")
    @NotNull
    private final Instant lastUpdatedOn = Instant.now();
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
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
    @Column(name = "is_deleted")
    @NotNull
    @ColumnDefault("false")
    private boolean isDeleted;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "modified_by")
    private String modifiedBy;

    public BigDecimal calculateTotalPrice() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }

    public void updateQuantity(Integer newQuantity) {
        if (newQuantity != null && newQuantity >= 0) {
            this.quantity = newQuantity;
            this.totalPrice = calculateTotalPrice();
        }
    }

    public void markAsDeleted() {
        this.isDeleted = true;
    }

    public BigDecimal getTotalPrice() {
        return calculateTotalPrice();
    }
}
