package com.bigdog.server.web.crimedoc.action.admin;

import com.bigdog.server.web.crimedoc.util.Tools;
import com.bigdog.server.web.crimedoc.action.admin.base.adminBase;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/19/13
 * Time: 5:37 AM
 */
public class uploadAction extends adminBase {
    @Action(value = "/fileUpload", interceptorRefs = @InterceptorRef(params=
            {"allowedTypes","image/bmp,image/jpeg,image/png,image/gif","maximumSize","2000000"},
            value="fileUpload"),
            results = {@Result(name = "success", type = "stream",
                    params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    public String upload() {
        getArticleService().rcvFile();
        return SUCCESS;
    }

    @Action(value = "/openupload",  results = {
            @Result(name = "upload", location = "/WEB-INF/jsp/web/admin/upload/upload.jsp",type ="dispatcher" )
    })
    public String openUpload() {
        return "upload";
    }

    @Action(value = "/openFileBrowser",  results = {
            @Result(name = "browser", location = "/WEB-INF/jsp/web/admin/filebrowser/Browser.jsp",type ="dispatcher" )
    })
    public String openFileBrowser() {
        return "browser";
    }

    @Action(value = "/openfile",  results = {@Result(name = "openfile", type = "stream",
            params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})
    })
    public String openFile() {
        setInputStream(Tools.getInputStream(getArticleService().getFileContent()));
        return "openfile";
    }


    @Action(value = "/updatefile",  results = {@Result(name = "update_success", type = "stream",
            params = {"contentType", "text/html", "inputName", "inputStream", "contentCharSet", "UTF-8"})})
    public String updateFile() {
        getArticleService().saveFile();
        return "update_success";
    }

}
