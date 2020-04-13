package com.artatech.bilerman.AccountManager.Models.Audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy"},
        allowGetters = true
)
public abstract class CreateAudit extends CreateDateAudit {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Long createdBy;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
