package com.artatech.bilerman.Models;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserAudit;
import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Clap;
import com.artatech.bilerman.Entities.Tag;

import java.util.Set;
import java.util.stream.Collectors;

public class ArticleDetails extends CreateUserAudit {

    private Long id;

    private String title;

    private String subtitle;

    private String body;

    private Long imageId;

    private Boolean isCoverVisible;

    private Integer views;

    private Integer comments;

    private Integer claps;

    private User user;

    private Set<Tag> tags;

    public ArticleDetails() {
    }

    public ArticleDetails(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.subtitle = article.getSubtitle();
        this.body = article.getBody();
        this.imageId = article.getImageId();
        this.isCoverVisible = article.getIsCoverVisible();
        this.views = article.getViews();
        this.comments = article.getComments().size();
        this.claps = article.getClaps().stream().collect(Collectors.summingInt(Clap::getCount));
        this.user = article.getUser();
        this.tags = article.getTags();
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Boolean getIsCoverVisible() {
        return isCoverVisible;
    }

    public void setIsCoverVisible(Boolean coverVisible) {
        isCoverVisible = coverVisible;
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
