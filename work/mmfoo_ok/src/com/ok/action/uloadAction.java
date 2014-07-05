package com.ok.action;

import com.ok.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class uloadAction extends ActionSupport
{
  private  static Logger logger = Logger.getLogger(uloadAction.class);

  private static final long serialVersionUID = 1L;
  //文件上传----------------------

    private File file;
    private String fileFileName;
    private String fileContentType;
     private UserService us;
      private int setid;

    public void setSetid(int setid) {
        this.setid = setid;
    }
    public void setUs(UserService us) {
        this.us = us;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return this.fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return this.fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
      java.util.List<String> pic_tmp;
    public String FileUpload() throws Exception {
        System.out.println("----进入新的文件上传程序--");

        try {
            String myFileDir = File.separator + "54zzb";

            File dir = new File(ServletActionContext.getRequest().getRealPath("/images/upload") + myFileDir);
            if (!dir.mkdir())
                logger.error(myFileDir + "目录存在");
            else {
                dir.mkdir();
            }
            InputStream is = new FileInputStream(this.file);

            String root = ServletActionContext.getRequest().getRealPath("/images/upload") + myFileDir;

            String newFileName = String.valueOf(System.currentTimeMillis() + getFileFileName());
            Object ttt = ActionContext.getContext().getSession().get("pictruesupload");

            if (ttt == null) {
                ttt = new ArrayList<String>();
            }
            pic_tmp = (java.util.List<String>) ttt;
//                ActionContext.getContext().getSession().put("userbean", us.getUser(email));
            pic_tmp.add(newFileName);
            ActionContext.getContext().getSession().put("pictruesupload", pic_tmp);
            System.out.println(((java.util.List<String>) ActionContext.getContext().getSession().get("pictruesupload")).size() + "------");
            File deskFile = new File(root, newFileName);
            OutputStream os = new FileOutputStream(deskFile);
            byte[] bytefer = new byte[400];
            int length = 0;
            while ((length = is.read(bytefer)) > 0) {
                os.write(bytefer, 0, length);
            }
            logger.error(newFileName + "文件的大小为：" + deskFile.length());
            os.close();
            is.close();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "input";
    }

      public String archiveimg() throws IOException {
        //            从内存中找出已经上传的队列

//           System.out.println(ActionContext.getContext().getSession().get("pictruesupload"));
//           System.out.println(ActionContext.getContext().getSession().get("islogin"));
        // pic_tmp = (java.util.List<String>)  ActionContext.getContext().getSession().get("pictruesupload");
        if (pic_tmp == null) {
            pic_tmp = new ArrayList<String>();
        }

        ActionContext.getContext().getActionInvocation().getProxy().setExecuteResult(false);
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html; utf-8");
        PrintWriter out = response.getWriter();
        String loginstatus = (String) ActionContext.getContext().getSession().get("islogin");
        if ("".equals(loginstatus) || loginstatus == null) {
            //未登录
            out.print("3");
            return null;
        }
        if ("Y".equals(loginstatus)) {


            if (pic_tmp == null) {
                out.print("4");
                return null;
            }
            if (pic_tmp.size() < 1) {
                out.print("4");
                return null;
            }
            //将用户上传的图片进行归档 并为每张图生成缩略图
            String path_base = ServletActionContext.getServletContext().getRealPath("/");
//            System.out.println("做缩略图:"+pic_tmp+","+path_base+",setid"+setid);
            us.archiveImg(setid, pic_tmp, path_base);
            out.print("1");
            return null;
        } else {
            out.print("3");
            return null;
        }
    }
    //----------------------文件上传
}