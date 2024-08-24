package model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Account {
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Column(name = "password")
    private String password;
    @Column(name = "job")
    private String job;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Embedded
    private Address address;
}