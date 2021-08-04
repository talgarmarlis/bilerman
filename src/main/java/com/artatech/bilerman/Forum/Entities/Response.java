package com.artatech.bilerman.Forum.Entities;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserAudit;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Response extends CreateUserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id", nullable = false, updatable = false)
    private Long id;

    private String message;

    private Boolean is_anonymous;


    @Column(name = "quetion_id")
    private Long question_id;

    @ManyToOne
    @JoinColumn(name="createdBy", nullable=false, insertable = false, updatable = false)
    private User user;

    public Response() {};

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Boolean getIs_anonymous() { return is_anonymous; }

    public void setIs_anonymous(Boolean is_anonymous) { this.is_anonymous = is_anonymous; }

    public Long getQuestion_id() { return question_id; }

    public void setQuestion_id(Long question_id) { this.question_id = question_id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

}
