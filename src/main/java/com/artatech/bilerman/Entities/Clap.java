package com.artatech.bilerman.Entities;

import com.artatech.bilerman.AccountManager.Entities.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;

@Entity
public class Clap implements Serializable {

    @EmbeddedId
    UserArticleKey id;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Max(100)
    private Integer count;

    public Clap() {}

    public Clap(Article article, User user) {
        this.article = article;
        this.user = user;
        this.id = new UserArticleKey(user.getId(), article.getId());
        this.count = 1;
    }

    public UserArticleKey getId() {
        return id;
    }

    public void setId(UserArticleKey id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
