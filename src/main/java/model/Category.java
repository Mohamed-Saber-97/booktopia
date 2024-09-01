package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", indexes = {@Index(name = "idx_category_name", columnList = "name")})
@Getter
@EqualsAndHashCode(callSuper = false, exclude = "products")
@ToString(exclude = "products")
@NoArgsConstructor
public class Category extends BaseEntity<Long> {

    @Column(name = "name", unique = true, nullable = false,length = 100)
    @NotBlank(message = "name is required")
    @Setter
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (this.getId() == null) {
            throw new IllegalStateException("Cannot add products to a new Category. The category must be persisted first.");
        }
        products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        products.remove(product);
        product.setCategory(null);
    }
}
