package model;

import base.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "admin")
@Data
@EqualsAndHashCode(callSuper = false)
public class Admin extends BaseEntity<Long> {
    @Embedded
    @Valid
    private Account account;
}