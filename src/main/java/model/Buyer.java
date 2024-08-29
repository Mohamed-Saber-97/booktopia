package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "buyer", indexes = {@Index(name = "idx_buyer_category", columnList = "category_id"), @Index(name = "idx_buyer_product", columnList = "product_id")})
@Getter
@Setter
public class Buyer extends BaseEntity<Long> {

    @Embedded
    private Account account;

    @Column(name = "credit_limit")
    @DecimalMin(value = "0.0")
    private BigDecimal creditLimit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buyer_interest", joinColumns = @JoinColumn(name = "buyer_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> interests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buyer_wishlist", catalog = "booktopia", joinColumns = @JoinColumn(name = "buyer_id", nullable = false), inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false))
    private Set<Product> wishlist = HashSet.newHashSet(16);

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "buyer_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> cart = HashMap.newHashMap(16);

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    private Set<Order> orders = HashSet.newHashSet(16);
}