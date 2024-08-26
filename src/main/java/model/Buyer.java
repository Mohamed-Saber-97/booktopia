package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "buyer")
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
    @Column(name = "interest")
    @ElementCollection
    @CollectionTable(
            name = "buyer_interest",
            catalog = "booktopia",
            joinColumns = @JoinColumn(name = "buyer_id")
    )
    private Set<String> interests = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "buyer_wishlist",
            catalog = "booktopia",
            joinColumns = {@JoinColumn(name = "buyer_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "product_id", nullable = false)}
    )
    private Set<Product> wishlist = new HashSet<>();
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    private Map<Product, Integer> cart = new HashMap<>(); // Integer value is the quantity
    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();
}