package com.artatech.bilerman.Forum.Entities;

import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserAudit;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import javax.validation.constraints.Size;

public class Question extends CreateUserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false, updatable = false)
    private Long id;

    @Size(max = 500)
    private String title;

    private String description;

    @OneToOne(mappedBy = "question", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Response elected;

    private Boolean is_anonymous;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Boolean getIs_anonymous() { return is_anonymous; }

    public void setIs_anonymous(Boolean is_anonymous) { this.is_anonymous = is_anonymous; }


}
