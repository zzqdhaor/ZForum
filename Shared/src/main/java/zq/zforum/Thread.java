package zq.zforum;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Thread {

    private Integer id;

    private String title;
    private String text;
    private String pics;
    private LocalDateTime postDate;
    private LocalDateTime lastReplyDate;
    private Integer userId;
    private String username;
    private Integer replyNum;

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public LocalDateTime getLastReplyDate() {
        return lastReplyDate;
    }

    public void setLastReplyDate(LocalDateTime lastReplyDate) {
        this.lastReplyDate = lastReplyDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
