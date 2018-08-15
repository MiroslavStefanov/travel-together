package org.softuni.traveltogether.domain.models.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceModel {
    private String content;
    private LocalDateTime dateTime;
    private UserServiceModel user;
    private List<CommentServiceModel> replies;

    public CommentServiceModel() {
        this.replies = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public List<CommentServiceModel> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentServiceModel> replies) {
        this.replies = replies;
    }
}
