package zq.zforum.forumservice.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "thread")
public class ElasticThread {

    @Id
    private String id;
    @Field(store = true, analyzer = "ik_max_word", type = FieldType.Text)
    private String title;
    @Field(store = true, analyzer = "ik_max_word", type = FieldType.Text)
    private String text;
    @Field
    private String pics;
    @Field
    private Long postDate;
    @Field
    private Long lastReplyDate;
    @Field
    private Integer userId;
    @Field
    private String username;
    @Field
    private Integer replyNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getPostDate() {
        return postDate;
    }

    public void setPostDate(Long postDate) {
        this.postDate = postDate;
    }

    public Long getLastReplyDate() {
        return lastReplyDate;
    }

    public void setLastReplyDate(Long lastReplyDate) {
        this.lastReplyDate = lastReplyDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }
}
