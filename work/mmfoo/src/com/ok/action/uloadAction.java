package com.ok.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class uloadAction extends ActionSupport
{
  private  static Logger logger = Logger.getLogger(uloadAction.class);

  private static final long serialVersionUID = 1L;
  private File file;
  private String fileFileName;
  private String fileContentType;

  public File getFile()
  {
    return this.file;
  }

  public void setFile(File file)
  {
    this.file = file;
  }

  public String getFileFileName()
  {
    return this.fileFileName;
  }

  public void setFileFileName(String fileFileName)
  {
    this.fileFileName = fileFileName;
  }

  public String getFileContentType()
  {
    return this.fileContentType;
  }

  public void setFileContentType(String fileContentType)
  {
    this.fileContentType = fileContentType;
  }

  public String execute()
    throws Exception
  {
    try
    {
      String myFileDir =File.separator+ "54zzb";

      File dir = new File(ServletActionContext.getRequest().getRealPath("/images/upload") + myFileDir);
      if (!dir.mkdir())
        logger.error(myFileDir + "目录存在");
      else {
        dir.mkdir();
      }
      InputStream is = new FileInputStream(this.file);

      String root = ServletActionContext.getRequest().getRealPath("/images/upload") + myFileDir;

      String newFileName = String.valueOf(System.currentTimeMillis() + getFileFileName());
        List <String>pic_tmp= (List<String>) ActionContext.getContext().getSession().get("pictrues");
        if(pic_tmp==null){
          pic_tmp = new ArrayList<String>();
        }
        pic_tmp.add(newFileName);
        ActionContext.getContext().getSession().put("pictrues",pic_tmp) ;
         logger.error(pic_tmp);
        pic_tmp=null;
         if(ActionContext.getContext().getSession().get("pictrues")==null){

         }
      File deskFile = new File(root, newFileName);
      OutputStream os = new FileOutputStream(deskFile);
      byte[] bytefer = new byte[400];
      int length = 0;
      while ((length = is.read(bytefer)) > 0)
      {
        os.write(bytefer, 0, length);
      }
      logger.error(newFileName + "文件的大小为：" + deskFile.length());
      os.close();
      is.close();
      return "success";
    } catch (Exception e) {
      e.printStackTrace();
    }return "input";
  }
}