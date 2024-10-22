package org.example.booktopia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.example.booktopia.base.BaseEntity;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "category", schema = "booktopia")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Getter
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    @NotBlank(message = "name is required")
    @Setter
    private String name;

    @ManyToMany
    @JoinTable(name = "buyer_interest",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "buyer_id"))
    private Set<Buyer> buyers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new LinkedHashSet<>();

}