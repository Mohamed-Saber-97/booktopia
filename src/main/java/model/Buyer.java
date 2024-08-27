package model;

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
@Table(name = "buyer")
@Getter
@Setter
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private Account account;

    @Column(name = "credit_limit")
    @DecimalMin(value = "0.0")
    private BigDecimal creditLimit;

    @ElementCollection
    @CollectionTable(name = "buyer_interest", catalog = "booktopia", joinColumns = @JoinColumn(name = "buyer_id"))
    @Column(name = "interest")
    private Set<String> interests = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buyer_wishlist", catalog = "booktopia",
            joinColumns = @JoinColumn(name = "buyer_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "product_id", nullable = false))
    private Set<Product> wishlist = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "buyer_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> cart = new HashMap<>();

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();
}