package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product extends BaseEntity<Long> {

    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "author")
    @NotBlank
    private String author;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "releaseDate", nullable = false)
    @NotNull
    private LocalDate releaseDate;
    @Column(name = "isbn")
    @NotBlank
    private String isbn;
    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "price")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @Min(value = 0)
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @NotBlank
    @Column(name = "image_path")
    private String imagePath;
}
