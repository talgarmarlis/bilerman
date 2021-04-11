package com.artatech.bilerman.AccountManager.Entities;

import com.artatech.bilerman.AccountManager.Models.Audit.DateAudit;
import com.artatech.bilerman.Entities.Article;
import com.artatech.bilerman.Entities.Clap;
import com.artatech.bilerman.Entities.SavedArticle;
import com.artatech.bilerman.Entities.Tag;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 250)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @Size(max = 1000)
    private String bio;

    private Long avatar;

    private Boolean enabled;

    @NaturalId
    @Column(name = "facebook_id")
    private String facebookId;

    @NaturalId
    @Column(name = "google_id")
    private String googleId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<SavedArticle> savedArticles = new HashSet<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Clap> claps = new HashSet<>();

    public User(){
        this.enabled = false;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
        this.enabled = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getAvatar() {
        return avatar;
    }

    public void setAvatar(Long avatar) {
        this.avatar = avatar;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<SavedArticle> getSavedArticles() {
        return savedArticles;
    }

    public void setSavedArticles(Set<SavedArticle> savedArticles) {
        this.savedArticles = savedArticles;
    }

    public Set<Clap> getClaps() {
        return claps;
    }

    public void setClaps(Set<Clap> claps) {
        this.claps = claps;
    }
}
