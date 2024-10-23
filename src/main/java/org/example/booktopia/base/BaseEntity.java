package org.example.booktopia.base;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = true)
    private Instant createdDate = Instant.now();

    @CreatedBy
    @Column(name = "created_by", length = 100)
    private String createdBy = null;

    @LastModifiedDate
    @Column(name = "last_updated_on", nullable = true)
    private Instant lastModifiedDate;

    @LastModifiedBy
    @Column(name = "modified_by", length = 100, nullable = true)
    private String lastModifiedBy = null;

    @Column(name = "is_deleted", nullable = false)
    @NotNull
    @ColumnDefault("false")
    private Boolean isDeleted = false;


}