package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Embeddable
public class Account {
    @Column(name = "name",nullable = false)
    @NotNull
    private String name;
    @Column(name = "birthday",nullable = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Column(name = "password",nullable = false)
    @NotNull
    private String password;
    @Column(name = "job")
    private String job;
    @Column(name = "email", unique = true,nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @Column(name = "phone_number",nullable = false,unique = true)
    @Pattern(regexp = "^01[0-25]\\d{8}$")
    @NotNull
    private String phoneNumber;
    @Embedded
    private Address address;
}