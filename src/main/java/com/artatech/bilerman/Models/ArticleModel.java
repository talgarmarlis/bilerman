package com.artatech.bilerman.Models;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserAudit;
import com.artatech.bilerman.Entities.Article;

public class ArticleModel extends CreateUserAudit {

    private Long id;

    private String title;

    private String subtitle;

    private Long imageId;

    private Integer views;

    private Integer comments;

    private Integer claps;

    private User user;

    public ArticleModel() {
    }

    public ArticleModel(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.subtitle = article.getSubtitle();
        this.imageId = article.getImageId();
        this.views = article.getViews();
        this.comments = 0;
        this.claps = 0;
        this.user = article.getUser();
        this.setCreatedAt(article.getCreatedAt());
        this.setCreatedBy(article.getCreatedBy());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getClaps() {
        return claps;
    }

    public void setClaps(Integer claps) {
        this.claps = claps;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
