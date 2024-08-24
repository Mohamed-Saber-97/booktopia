package model;


import jakarta.persistence.Column;
import lombok.Data;

@Data
public class Address {
    @Column(name = "street")
    private String street;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "country")
    private String country;
}
