package org.example.booktopia.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.booktopia.base.BaseEntity;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "buyer", schema = "booktopia")
@NoArgsConstructor
@AllArgsConstructor
public class Buyer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    @Valid
    @Setter
    private Account account;

    @NotNull
    @ColumnDefault("0.00")
    @Column(name = "credit_limit", nullable = false, precision = 65, scale = 2)
    @NotNull(message = "Credit limit is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Credit limit must be greater than 0")
    private BigDecimal creditLimit;

    @ManyToMany
    @JoinTable(name = "buyer_interest",
               joinColumns = @JoinColumn(name = "buyer_id"),
               inverseJoinColumns = @JoinColumn(name = "category_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Category> interests = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "buyer_wishlist",
               joinColumns = @JoinColumn(name = "buyer_id"),
               inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Product> products = new LinkedHashSet<>();

    @OneToMany(mappedBy = "buyer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "buyer")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Order> orders = new LinkedHashSet<>();

}