package com.bigdog.server.web.crimedoc.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/1/13
 * Time: 12:55 AM
 */

@NamedQueries({
        @NamedQuery(name="getArticleByTitle", query= "FROM Article WHERE title=?1")
})
@Entity
@Table(name = "article")
public class Article implements Serializable {
    private int id;     //1
    private String title;  //2
    private String author; //3
    private String content; //4
    private String imgname; //5
    private int cnt;//浏览量   //6
    private Date createTime;//创建日期    //7
    private String catagory;//文章分类    //8
    private int catagory_order;//文章在同一分类中的排序问题 //9
    private String description;//10


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column
    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Column
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column
    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    @Column
    public int getCatagory_order() {
        return catagory_order;
    }

    public void setCatagory_order(int catagory_order) {
        this.catagory_order = catagory_order;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
