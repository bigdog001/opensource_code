package com.bigdog.server.web.crimedoc.bean;

import java.io.File;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/2/13
 * Time: 3:02 AM
 */
public class Wsbean {
    private int w = 1;//页面导航的高亮显示 1首页
    private int tab;


    //网页文章字段========================================
    private int id;
    private String title;
    private String author;
    private String content;
    private String imgname;
    private int cnt;//浏览量
    private Date createTime;//创建日期
    private String catagory;//文章分类
    private int catagory_order = 1;//文章在同一分类中的排序问题
    private String description ;
    //========================================网页文章字段

    //文章表的总页数===========================================
    private int articleCounter;
    //===========================================文章表的总页数


    //文件上传==================================================
    // 封装上传文件域的属性
    private File upload;
    // 封装上传文件类型的属性
    private String uploadContentType;
    // 封装上传文件名的属性
    private String uploadFileName;
    private String storageFileName;
    //==================================================文件上传


    //分页显示=========================================
    private int startRecord;
    private int ajaxTrue;
    private int recordSize;
    // =========================================分页显示

    //web在线编辑================================
    private String filepath;
    private String filecontent;
    //================================web在线编辑


    //管理员登录=================================

    private String username;
    private String passwd;
    //=================================管理员登录


    public int getArticleCounter() {
        return articleCounter;
    }

    public void setArticleCounter(int articleCounter) {
        this.articleCounter = articleCounter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public int getAjaxTrue() {
        return ajaxTrue;
    }

    public void setAjaxTrue(int ajaxTrue) {
        this.ajaxTrue = ajaxTrue;
    }

    public int getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(int startRecord) {
        this.startRecord = startRecord;
    }

    public int getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public int getCatagory_order() {
        return catagory_order;
    }

    public void setCatagory_order(int catagory_order) {
        this.catagory_order = catagory_order;
    }

    public String getFilecontent() {
        return filecontent;
    }

    public void setFilecontent(String filecontent) {
        this.filecontent = filecontent;
    }
}
