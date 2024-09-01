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
    @NotNull
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    @Column(name = "quantity",nullable = false)
    @Min(value = 0, message = "Quantity must be at least 0")
    @ColumnDefault(value = "0")
    @NotNull(message = "Quantity is required")
    private Integer quantity;

    @Column(name = "price")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @ColumnDefault(value = "0.0")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Transient
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalPrice;

    @Column(name = "is_deleted",nullable = false)
    @NotNull
    @ColumnDefault("false")
    private Boolean isDeleted;

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
