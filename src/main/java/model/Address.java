package model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class Address {
    @Column(name = "street")
    @NotNull
    private String street;
    @Column(name = "city")
    @NotNull
    private String city;
    @Column(name = "zipcode")
    @NotNull
    private String zipcode;
    @Column(name = "country")
    @NotNull
    private String country;
}
