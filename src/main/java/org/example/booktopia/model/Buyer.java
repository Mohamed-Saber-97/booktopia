package org.example.booktopia.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.booktopia.base.BaseEntity;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "buyer", schema = "booktopia")
@NoArgsConstructor
@AllArgsConstructor
public class Buyer extends BaseEntity implements UserDetails {
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
    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;
    private String twoFactorSecret;
    private boolean isTwoFactorEnabled = false;
    private String signUpMethod;
    public Buyer(Account account, BigDecimal creditLimit, Set<Category> interests) {
        this.account = account;
        this.creditLimit = creditLimit;
        this.interests = interests;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("BUYER"));
    }

    @Override
    public String getPassword() {
        return this.account.getPassword();
    }

    @Override
    public String getUsername() {
        return this.account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getIsDeleted();
    }
}