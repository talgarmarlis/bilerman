package com.artatech.bilerman.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicationModel {

    private Long draftId;

    private String title;

    private String subtitle;

    private String preview;

    private Long languageId;

    private String[] tags;

    @JsonProperty("isCoverVisible")
    private Boolean isCoverVisible;

    @JsonProperty("isPublic")
    private Boolean isPublic;

    @JsonProperty("isListed")
    private Boolean isListed;

    public Long getDraftId() {
        return draftId;
    }

    public void setDraftId(Long draftId) {
        this.draftId = draftId;
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

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Boolean getCoverVisible() {
        return isCoverVisible;
    }

    public void setCoverVisible(Boolean coverVisible) {
        isCoverVisible = coverVisible;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public Boolean getListed() {
        return isListed;
    }

    public void setListed(Boolean listed) {
        isListed = listed;
    }
}
