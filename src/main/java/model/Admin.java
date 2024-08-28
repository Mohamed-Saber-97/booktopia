package model;

import base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin")
@Data
public class Admin extends BaseEntity<Long> {
    @Embedded
    private Account account;
}