package model;

import base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @Column(name = "author", length = 100, nullable = false)
    @NotBlank(message = "Author is required")
    private String author;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "release_date", nullable = false)
    @NotNull(message = "Release Date is required")
    private LocalDate releaseDate;

    @Column(name = "isbn", unique = true, length = 13, nullable = false)
    @NotBlank(message = "ISBN is required")
    private String isbn;

    @Column(name = "description", length = 500, nullable = false)
    @NotBlank(message = "Description is required")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @NotNull(message = "Price is required")
    private BigDecimal price;

    @Min(value = 0, message = "Quantity should be greater than zero")
    @Column(name = "quantity")
    @ColumnDefault(value = "0")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotBlank(message = "Description shouldn't be empty or null")
    @Column(name = "image_path", length = 255, nullable = false)
    private String imagePath;
}
