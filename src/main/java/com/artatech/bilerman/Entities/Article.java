package com.artatech.bilerman.Entities;

import com.artatech.bilerman.Models.Audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
public class Article extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id")
    private Integer id;

    @Size(max = 500)
    private String title;

    @Size(max = 500)
    private String subtitle;

    @Size(max = 500)
    private String description;

    private String body;

    @Column(name = "image_id")
    private Integer imageId;

    Article() {}

    public Article(String title, String subtitle, String description, String body){
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }
}
