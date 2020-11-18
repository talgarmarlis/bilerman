package com.artatech.bilerman.Entities;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateDateAudit;

import javax.persistence.*;

@Entity
@Table(name = "saved_article")
public class SavedArticle extends CreateDateAudit {

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

    public SavedArticle() {}

    public SavedArticle(Article article, User user) {
        this.article = article;
        this.user = user;
        this.id = new UserArticleKey(user.getId(), article.getId());
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
}
