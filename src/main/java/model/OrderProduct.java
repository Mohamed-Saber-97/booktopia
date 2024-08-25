package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_product")
@IdClass(OrderProductId.class)
@Getter
@Setter
public class OrderProduct {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")

    private Product product;
    @Column(name = "quantity")
    @Min(value = 0)
    private Integer quantity;

    @Column(name = "price")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
}
