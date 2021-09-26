package com.artatech.bilerman.Forum.Models;

import com.artatech.bilerman.AccountManager.Entities.User;
import com.artatech.bilerman.AccountManager.Models.Audit.CreateUserAudit;
import com.artatech.bilerman.Entities.Clap;
import com.artatech.bilerman.Entities.Tag;
import com.artatech.bilerman.Forum.Entities.Question;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ForumQuestionModel extends CreateUserAudit {

    private Long id;

    private String title;

    private String description;

    private Long languageId;

    private Integer response_id;

    private User user;

    private Set<Tag> tags = new HashSet<>();

    private Boolean is_anonymous;

    public ForumQuestionModel() {
    };

    public ForumQuestionModel(Question question) {
        this.title = question.getTitle();
        this.description = question.getDescription();
        this.id = question.getId();
        this.user = question.getUser();
        this.languageId = question.getLanguageId();
        this.response_id = question.getResponseId();
        this.tags = question.getTags();
        this.is_anonymous = question.getIs_anonymous();
        this.setCreatedAt(question.getCreatedAt());
        this.setCreatedBy(question.getCreatedBy());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIs_anonymous() {
        return is_anonymous;
    }

    public void setIs_anonymous(Boolean is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageID) {
        this.languageId = languageID;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
