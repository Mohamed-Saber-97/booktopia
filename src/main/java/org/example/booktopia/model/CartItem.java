package org.example.booktopia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktopia.error.IllegalValueException;

@Getter
@Setter
@Entity
@Table(name = "cart_items", schema = "booktopia")
public class CartItem {
    @EmbeddedId
    private CartItemId id;
    @MapsId("buyerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;
    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(name = "quantity")
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Buyer buyer, Product product, Integer quantity) {
        this(new CartItemId(buyer.getId(), product.getId()), quantity);
    }

    public CartItem(Long buyerId, Long productId, Integer quantity) {
        this(new CartItemId(buyerId, productId), quantity);
    }

    public CartItem(CartItemId id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        if (quantity < 0)
            throw new IllegalValueException("Quantity", quantity.toString());
    }
}