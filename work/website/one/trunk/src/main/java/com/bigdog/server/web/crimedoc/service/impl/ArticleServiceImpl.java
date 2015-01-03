package com.bigdog.server.web.crimedoc.service.impl;

import com.bigdog.server.web.crimedoc.util.Tools;
import com.bigdog.server.web.crimedoc.cfg.WebConfig;
import com.bigdog.server.web.crimedoc.action.admin.base.adminBase;
import com.bigdog.server.web.crimedoc.bean.Article;
import com.bigdog.server.web.crimedoc.dao.ArticleDao;
import com.bigdog.server.web.crimedoc.dao.cache.MyCache;
import com.bigdog.server.web.crimedoc.service.ArticleService;
import com.bigdog.server.web.crimedoc.util.WsUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/18/13
 * Time: 5:10 AM
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    Logger logger = Logger.getLogger(this.getClass());
    private adminBase base;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private MyCache myCache;

    public ArticleDao getArticleDao() {
        return articleDao;
    }

    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    @Override
    public void setAdminBase(adminBase base) {
        this.base = base;
    }

    @Override
    public void add() {
        logger.info("id:" + base.getWsbean().getId());
        logger.info("title:" + base.getWsbean().getTitle());
        logger.info("content:" + base.getWsbean().getContent());
        logger.info("Catagory_order:" + base.getWsbean().getCatagory_order());
        Article article = new Article();
        article.setTitle(base.getWsbean().getTitle());
        article.setCreateTime(new Date());
        article.setDescription(base.getWsbean().getDescription());
        article.setImgname(base.getWsbean().getImgname());
        article.setContent(base.getWsbean().getContent());
        article.setCatagory_order(base.getWsbean().getCatagory_order());
        articleDao.add(article);
        myCache.delItem(base.getWsbean().getId());
        JSONObject obj = new JSONObject();
        obj.put("result_code", 1);
        base.setInputStream(Tools.getInputStream(obj.toString()));
    }

    @Override
    public void del() {
        articleDao.delete(base.getWsbean().getId());
        myCache.delItem(base.getWsbean().getId());
        JSONObject obj = new JSONObject();
        obj.put("result_code", 1);
        base.setInputStream(Tools.getInputStream(obj.toString()));
    }

    @Override
    public Article get() {
        return articleDao.query(base.getWsbean().getId());
    }

    @Override
    public List<Article> gets() {
        if (base.getWsbean().getRecordSize() > 10) {
            base.getWsbean().setRecordSize(10);
        }
        List<Article> articles = articleDao.query(base.getWsbean().getStartRecord(), base.getWsbean().getRecordSize());

        if (base.getWsbean().getAjaxTrue() == 1) {
            JSONObject jsonObj = new JSONObject();
            int counter = 0;
//            jsonObj.put("result_code", 1);
            for (Article a : articles) {
                JSONObject obj_item = new JSONObject();
                obj_item.put("id", a.getId());
                obj_item.put("title", a.getTitle());
                obj_item.put("author", a.getAuthor());
//                obj_item.put("content", a.getContent());
                obj_item.put("cnt", a.getCnt());
                obj_item.put("createTime", a.getCreateTime() == null ? "-" : a.getCreateTime().toString());
                obj_item.put("catagory", a.getCatagory());
                obj_item.put("catagory_order", a.getCatagory_order());
                obj_item.put("description", a.getDescription());
                jsonObj.put("article" + counter++, obj_item);
            }
            base.setInputStream(Tools.getInputStream(jsonObj.toString()));
        }
        return articles;
    }

    @Override
    public void update() {
        logger.info("id:" + base.getWsbean().getId());
        logger.info("title:" + base.getWsbean().getTitle());
        logger.info("content:" + base.getWsbean().getContent());
        logger.info("Catagory_order:" + base.getWsbean().getCatagory_order());
        Article article = articleDao.query(base.getWsbean().getId());
        article.setId(base.getWsbean().getId());
        article.setImgname(base.getWsbean().getImgname());
        article.setTitle(base.getWsbean().getTitle());
        article.setContent(base.getWsbean().getContent());
        article.setCatagory_order(base.getWsbean().getCatagory_order());
        article.setDescription(base.getWsbean().getDescription());
        articleDao.update(article);
        myCache.delItem(base.getWsbean().getId());
        JSONObject obj = new JSONObject();
        obj.put("result_code", 1);
        base.setInputStream(Tools.getInputStream(obj.toString()));
    }

    @Override
    public String getFileContent() {
        String content = "";
        if ("".equals(base.getWsbean().getFilepath()) || base.getWsbean().getFilepath() == null) {
            content = "file path is empty...";
        } else {
            content = WsUtil.readTxtFile(base.getWsbean().getFilepath(), "utf-8");
        }
        return content;
    }

    @Override
    public void saveFile() {
//        logger.info(base.getWsbean().getFilepath());
//        logger.info(base.getWsbean().getFilecontent());
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        try {
            fos = new FileOutputStream(base.getWsbean().getFilepath());
            osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(base.getWsbean().getFilecontent());
            osw.flush();
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            try {
                osw.close();
                fos.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }

        }
        base.setInputStream(Tools.getInputStream("文件更新成功!"));
    }

    @Override
    public void rcvFile() {
        final int BUFFER_SIZE = 16 * 1024;

        base.getWsbean().setStorageFileName(base.getWsbean().getUploadFileName());
//        logger.info("FileName: " + getWsbean().getUploadFileName());
//        logger.info("ContentType: " + getWsbean().getUploadContentType());
//        logger.info("File: " + getWsbean().getUpload());

        String fname = Tools.getMD5(base.getWsbean().getStorageFileName()) + Tools.getSubFix(base.getWsbean().getStorageFileName());
        String upload_folder = ServletActionContext.getServletContext().getRealPath(File.separator +"res"+ File.separator+ WebConfig.upload_path);
        
        File upload_folder_ = new File(upload_folder);
        if(!upload_folder_.exists()){
            upload_folder_.mkdir();
        }
        upload_folder_ = null;
        
        
        File storageFile = new File(upload_folder + File.separator + fname);
        JSONObject obj = new JSONObject();
        obj.put("upload_code", 1);
        obj.put("upload_msg", "upload success!");
        obj.put("upload_path", fname);
        base.setInputStream(Tools.getInputStream(obj.toString() + " <a href='javascript:history.go(-1);'>后退</a>"));


        try {
            logger.info("------>" + storageFile.getAbsolutePath());
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(base.getWsbean().getUpload()),
                        BUFFER_SIZE);
                out = new BufferedOutputStream(new FileOutputStream(storageFile),
                        BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }

    @Override
    public int pageCounte() {
        return articleDao.pages(10);
    }
}
