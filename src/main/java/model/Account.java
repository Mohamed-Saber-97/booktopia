package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Embeddable
public class Account {
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "birthday")
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "job")
    private String job;
    @Email
    @Column(name = "email", unique = true)
    @NotNull
    private String email;
    @Column(name = "phone_number")
    @Pattern(regexp = "^01[0-25]\\d{8}$")
    @NotNull
    private String phoneNumber;
    @Embedded
    private Address address;
}