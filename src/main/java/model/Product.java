package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"category"}, callSuper = false)
@ToString(exclude = {"category"})
public class Product extends BaseEntity<Long> {

    @Column(name = "name")
    @NotBlank(message = "name is required")
    private String name;
    @Column(name = "author")
    @NotBlank(message = "author is required")
    private String author;
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "releaseDate", nullable = false)
    @NotNull(message = "Release Date is required")
    private LocalDate releaseDate;
    @Column(name = "isbn",unique = true)
    @NotBlank(message = "ISBN is required")
    private String isbn;
    @Column(name = "description")
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "price")
    @DecimalMin(value = "0.0", inclusive = false)
    @NotNull(message = "price is required")
    private BigDecimal price;

    @Min(value = 0)
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @NotBlank(message = "Description shouldn't be empty or null")
    @Column(name = "image_path")
    private String imagePath;
}
