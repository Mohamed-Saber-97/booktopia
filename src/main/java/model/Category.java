package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category", indexes = {@Index(name = "idx_category_name", columnList = "name")})
@Setter
@Getter
public class Category extends BaseEntity<Long> {

    @Column(name = "name", unique = true)
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();
}
