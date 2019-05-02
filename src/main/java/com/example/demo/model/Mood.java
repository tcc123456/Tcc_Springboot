package com.example.demo.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


//微信说说实体
@Entity
@Table(name="Mood")
public class Mood implements Serializable {

    @Id//主键
    private  String id;
    private String content;//说说内容
    private String user_id;//用户id
    private Integer praise_num;//点赞数量
    private Date publish_time;//发表数量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(Integer praise_num) {
        this.praise_num = praise_num;
    }

    public Date getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Date publish_time) {
        this.publish_time = publish_time;
    }

    public Mood(String id, String content, String user_id, Integer praise_num, Date publish_time) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
        this.praise_num = praise_num;
        this.publish_time = publish_time;
    }

    public Mood() {
    }

    @Override
    public String toString() {
        return "Mood{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", user_id='" + user_id + '\'' +
                ", praise_num=" + praise_num +
                ", publish_time=" + publish_time +
                '}';
    }
}
