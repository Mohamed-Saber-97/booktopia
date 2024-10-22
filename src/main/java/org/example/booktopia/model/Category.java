package org.example.booktopia.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.booktopia.base.BaseEntity;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "category", schema = "booktopia")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    @JoinTable(name = "buyer_interest",
               joinColumns = @JoinColumn(name = "category_id"),
               inverseJoinColumns = @JoinColumn(name = "buyer_id"))
    private Set<Buyer> buyers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new LinkedHashSet<>();
}