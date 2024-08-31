package model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
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
    @Column(name = "street")
    @NotBlank(message = "Street is required")
    private String street;
    @Column(name = "city")
    @NotBlank(message = "City is required")
    private String city;
    @Column(name = "zipcode")
    @NotBlank(message = "Zipcode is required")
    private String zipcode;
    @Column(name = "country")
    @NotBlank(message = "Country is required")
    private String country;
}
