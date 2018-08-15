package org.softuni.traveltogether.domain.models.view;

import java.time.LocalDateTime;
import java.util.List;

public class CommentViewModel {
    private String content;
    private LocalDateTime dateTime;
    private UserLinkViewModel user;
    private List<CommentViewModel> replies;

    public CommentViewModel() {
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

    public UserLinkViewModel getUser() {
        return user;
    }

    public void setUser(UserLinkViewModel user) {
        this.user = user;
    }

    public List<CommentViewModel> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentViewModel> replies) {
        this.replies = replies;
    }
}
