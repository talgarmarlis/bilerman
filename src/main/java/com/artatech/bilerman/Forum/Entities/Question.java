package com.artatech.bilerman.Forum.Entities;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserAudit;
import com.artatech.bilerman.Entities.Tag;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Question extends CreateUserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false, updatable = false)
    private Long id;

    @Size(max = 500)
    private String title;

    private String description;

    @Column(name = "language_id")
    private Long languageId;

    private Integer response_id;

    @ManyToOne
    @JoinColumn(name="createdBy", nullable=false, insertable = false, updatable = false)
    private User user;

    public Question() {};

    public Question(String title, String description){
        this.title = title;
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "question_tag", joinColumns = { @JoinColumn(name = "question_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private Set<Tag> tags = new HashSet<>();

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
