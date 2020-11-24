package com.artatech.bilerman.Models;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserDateAudit;
import com.artatech.bilerman.Entities.Comment;

public class CommentModel extends CreateUserDateAudit {

    private Long id;

    private String message;

    private Long articleId;

    private User user;

    private Integer repliesCount;

    public CommentModel() {}

    public CommentModel(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.articleId = comment.getArticleId();
        this.user = comment.getUser();
        this.setCreatedAt(comment.getCreatedAt());
        this.setUpdatedAt(comment.getUpdatedAt());
        this.setCreatedBy(comment.getCreatedBy());
        this.repliesCount = comment.getReplies().size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(Integer repliesCount) {
        this.repliesCount = repliesCount;
    }
}
