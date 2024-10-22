package org.example.booktopia.base;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    @NotNull
    private Instant createdDate;

    @CreatedBy
    @Column(name = "created_by", length = 100)
    private String createdBy = null;

    @LastModifiedDate
    @Column(name = "last_updated_on", nullable = false)
    @NotNull
    private Instant lastModifiedDate;

    @LastModifiedBy
    @Column(name = "modified_by", length = 100)
    private String lastModifiedBy = null;

    @Column(name = "is_deleted", nullable = false)
    @NotNull
    private Boolean isDeleted = false;


}