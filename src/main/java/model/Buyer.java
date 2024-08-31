package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "buyer", indexes = {@Index(name = "idx_buyer_category", columnList = "category_id"), @Index(name = "idx_buyer_product", columnList = "product_id")})
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, exclude = {"interests", "wishlist", "cart", "orders"})
@ToString(exclude = {"interests", "wishlist", "cart", "orders"})
public class Buyer extends BaseEntity<Long> {

    @Embedded
    @Valid
    @Setter
    private Account account;

    @Column(name = "credit_limit")
    @DecimalMin(value = "0.0")
    private BigDecimal creditLimit;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "buyer_interest", joinColumns = @JoinColumn(name = "buyer_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> interests = HashSet.newHashSet(16);

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

    public void addInterest(Category category) {
        this.interests.add(category);
    }

    public void removeInterest(Category category) {
        this.interests.remove(category);
    }

    public Set<Category> getInterests() {
        return Collections.unmodifiableSet(this.interests);
    }

    public void addToWishlist(Product product) {
        this.wishlist.add(product);
    }

    public void removeFromWishlist(Product product) {
        this.wishlist.remove(product);
    }

    public Set<Product> getWishlist() {
        return Collections.unmodifiableSet(this.wishlist);
    }

    public void addToCart(Product product, int quantity) {
        this.cart.merge(product, quantity, Integer::sum);
    }

    public void removeFromCart(Product product) {
        this.cart.remove(product);
    }

    public Map<Product, Integer> getCart() {
        return Collections.unmodifiableMap(this.cart);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void removeOrder(Order order) {
        this.orders.remove(order);
    }

    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(this.orders);
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        if (creditLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Credit limit cannot be negative");
        }
        this.creditLimit = creditLimit;
    }
}