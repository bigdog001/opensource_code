package com.ok.service;

import com.ok.bean.IndexImg;
import com.ok.bean.User;
import com.ok.dao.AdminDao;
import org.apache.commons.mail.EmailException;

import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-12
 * Time: 21:50:45
 */
public class AdminService {
    private AdminDao ad;

    public void setAd(AdminDao ad) {
        this.ad = ad;
    }

    public List<IndexImg> getIndexImg(int page,int size) {
        return ad.getIndexImg(page,size);
    }

    public void deleteIndexImg(int imgid) {
        ad.deleteIndexImg(imgid);

    }

    public void addIndexImg(IndexImg img) {
        ad.addIndexImg(img);
    }

    public void editeImdeImg(IndexImg img) {
        ad.editeImdeImg(img);
    }
     public void forgetPwd(String email){
         ad.forgetPwd(email);
     }
      public void sendEmailInvent(String sendto, String tpl_path, User user) throws EmailException {
          ad.sendEmailInvent(sendto,tpl_path,user);
      }
}
