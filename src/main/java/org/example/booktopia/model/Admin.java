package org.example.booktopia.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.booktopia.base.BaseEntity;


@Entity
@Table(name = "admin", schema = "booktopia")
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Embedded
    @Valid
    private Account account;

}