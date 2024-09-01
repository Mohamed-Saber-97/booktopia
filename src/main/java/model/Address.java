package model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = false)
@Embeddable
public class Address implements Serializable {
    @Column(name = "street", nullable = false)
    @NotBlank(message = "Street is required")
    @Size(max = 255, message = "Street name must be up to 255 characters")
    private String street;
    @Column(name = "city", length = 100, nullable = false)
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City name must be up to 100 characters")
    private String city;
    @Column(name = "zipcode", length = 15, nullable = false)
    @NotBlank(message = "Zipcode is required")
    @Size(max = 15, message = "Zipcode must be up to 15 characters")
    private String zipcode;
    @Column(name = "country", length = 100, nullable = false)
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country name must be up to 100 characters")
    private String country;
}
