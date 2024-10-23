package org.example.booktopia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BuyerWishlistId {
    @NotNull
    @Column(name = "buyer_id", nullable = false)
    private Long buyerId;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;
}