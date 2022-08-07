package com.artatech.bilerman.Entities;

import com.artatech.bilerman.AccountManager.Models.Audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
public class Draft extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "draft_id", nullable = false, updatable = false)
    private Long id;

    @Size(max = 500)
    private String title;

    @Size(max = 500)
    private String preview;

    private Boolean published;

    private String body;

    @OneToOne(mappedBy = "draft", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Article article;

    @Column(name = "image_id")
    private Long imageId;

    public Draft() {}

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

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}
