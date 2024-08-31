package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class Account implements Serializable {
    @Column(name = "name")
    @NotBlank(message = "Name is required")
    private String name;
    @Column(name = "birthday")
    @NotBlank(message = "Birthday is required")
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Column(name = "password")
    @NotBlank(message = "Password is required")
    private String password;
    @Column(name = "job")
    private String job;
    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = "^01[0-25]\\d{8}$", message = "Invalid phone number format")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @Embedded
    @Valid
    private Address address;
}