package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Embeddable
public class Account {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "birthday", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "job")
    private String job;
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    @Pattern(regexp = "^01[0-25]\\d{8}$")
    private String phoneNumber;
    @Embedded
    private Address address;
}