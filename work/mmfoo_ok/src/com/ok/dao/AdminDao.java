package com.ok.dao;

import com.ok.bean.IndexImg;
import com.ok.bean.User;
import org.apache.commons.mail.EmailException;

import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-12
 * Time: 21:48:18
 */
public interface AdminDao {
    public List<IndexImg> getIndexImg(final int page,final int size); //取所有的首页焦点图

    public void deleteIndexImg(int imgid); //删除焦点图

    public void addIndexImg(IndexImg img);// 增加焦点图

    public void editeImdeImg(IndexImg img);//修改焦点图

    public IndexImg getImdeImg(int imgid);//取焦点图对象

    public void forgetPwd(String email);//忘记密码

    public void sendEmailInvent(String sendto,String tpl_path, User user/*发送者对象*/) throws EmailException;    //邮件邀请
}
