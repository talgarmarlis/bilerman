package com.artatech.bilerman.Entities;

import com.artatech.bilerman.AccountManager.Models.Audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
public class Article extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id", nullable = false, updatable = false)
    private Long id;

    @Size(max = 500)
    private String title;

    @Size(max = 500)
    private String subtitle;

    @Size(max = 500)
    private String description;

    @Column(name = "image_id")
    private Long imageId;

    private Boolean published = false;

    private Integer views = 0;

    private String body;

    public Article() {}

    public Article(String title, String subtitle, String description){
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
