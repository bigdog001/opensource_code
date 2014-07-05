package com.sender.pool;

import com.sender.send.webRun;
import com.sender.taskbean.AuthorBean;
import com.sender.taskbean.configBean;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 4:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthorPool {
    private static Logger logger = Logger.getLogger(AuthorPool.class);
    private static List<AuthorBean> listpool=new ArrayList<AuthorBean>();
    private static AuthorPool authorPool;
    private static Long load_start=0L;
    private static String AuthorPath= configBean.AuthorPath;
    private static int author_count=0;
    private AuthorPool(){
        initAuthor();
    }
    public static AuthorPool getAuthorPool() {
        if (authorPool==null) {
            authorPool=new AuthorPool();
        }
        return authorPool;
    }
    private void initAuthor() {
        if (listpool.size()<1) {
            load_start=System.currentTimeMillis();
            logger.info("开始从文件中加载身份数据...");
            BufferedReader br = null;
            try {
                br = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(
                                        AuthorPath)));
            } catch (FileNotFoundException e) {
                logger.info(e.getMessage()+"身份配置文件找不到,exit...");
                if (configBean.isWebMod==1) {
                    webRun.getInstance().stop();
                }else {
                    System.exit(0);
                }
            }
            String data;
            try {
                while ((data = br.readLine()) != null && !"".equals(data)) {
                    data=data.trim();
                    String[] auth_tmp=data.split(",");
                    if (auth_tmp.length==3) {
                        AuthorBean authorBean= new AuthorBean();
                        authorBean.setSmtpHost(auth_tmp[0]);
                        authorBean.setUsername(auth_tmp[1]);
                        authorBean.setPassword(auth_tmp[2]);
                        authorBean.setPort("25");
                        listpool.add(authorBean);
                    }
                }
                br.close();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }

            if (listpool.size()==0) {
            logger.info("身份数据加载失败,应用退出！");
                if (configBean.isWebMod==1) {
                    webRun.getInstance().stop();
                }else {
                    System.exit(0);
                }
            }
            logger.info("成功加载身份数据,一共加载:"+listpool.size()+"条身份数据,耗时:"+(System.currentTimeMillis()-load_start)+"毫秒!");
        }
    }
    public AuthorBean getAuthorBean() {
        if (listpool==null) {
            initAuthor();
        }
        if (author_count==listpool.size()-1) {
            author_count=0;
        }
        author_count++;;
        return  listpool.get(author_count);
    }

}
