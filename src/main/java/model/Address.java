package model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {
    @Column(name = "street",nullable = false)
    private String street;
    @Column(name = "city",nullable = false)
    private String city;
    @Column(name = "zipcode", nullable = false)
    private String zipcode;
    @Column(name = "country", nullable = false)
    private String country;
}
