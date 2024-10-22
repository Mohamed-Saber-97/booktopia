package org.example.booktopia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CartItemId implements java.io.Serializable {
    private static final long serialVersionUID = -1463316507260129299L;
    @NotNull
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartItemId entity = (CartItemId) o;
        return Objects.equals(this.productId, entity.productId) &&
                Objects.equals(this.buyerId, entity.buyerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, buyerId);
    }

}