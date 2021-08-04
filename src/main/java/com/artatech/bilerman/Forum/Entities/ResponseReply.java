package com.artatech.bilerman.Forum.Entities;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserAudit;

import javax.persistence.*;

@Entity
public class ResponseReply extends CreateUserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_reply_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "response_id")
    private Long question_id;

    private String message;

    @ManyToOne
    @JoinColumn(name="createdBy", nullable=false, insertable = false, updatable = false)
    private User user;

    public ResponseReply() {};

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getQuestion_id() { return question_id; }

    public void setQuestion_id(Long question_id) { this.question_id = question_id; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
