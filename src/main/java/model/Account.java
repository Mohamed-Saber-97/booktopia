package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @Column(name = "name", length = 100, nullable = false)
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be up to 100 characters")
    private String name;

    @Column(name = "birthday", nullable = false)
    @NotNull(message = "Birthday is required")
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;

    @Column(name = "password", length = 72, nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @EqualsAndHashCode.Exclude
    private String password;

    @Column(name = "job", length = 100)
    @Size(max = 100, message = "Job title must be up to 100 characters")
    private String job;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "phone_number", unique = true, length = 15, nullable = false)
    @Pattern(regexp = "^01[0-25]\\d{8}$", message = "Invalid phone number format")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @Embedded
    @Valid
    private Address address;
}