package com.artatech.bilerman.Forum.Entities;

import com.artatech.bilerman.AccountManager.Entities.User;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.validation.constraints.Max;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ResponseVote implements Serializable {

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("response_id")
    @JoinColumn(name = "response_id")
    private Response response;

    @ManyToOne
    @MapsId("question_id")
    @JoinColumn(name = "question_id")
    private Question question;

    @Max(100)
    private Integer count;
    @Id
    private Long id;

    public ResponseVote() {}

    public ResponseVote(Response response, User user) {
        this.response = response;
        this.user = user;
        this.count = 1;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Response getResponse() { return response; }

    public void setResponse(Response response) { this.response = response; }

    public Question getQuestion() { return question; }

    public void setQuestion(Question question) { this.question = question; }

    public Integer getCount() { return count; }

    public void setCount(Integer count) { this.count = count; }

    public void setId(Long id) { this.id = id; }

    public Long getId() { return id; }
}
