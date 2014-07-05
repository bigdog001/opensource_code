package com.ok.daoimpl;

import com.ok.bean.IndexImg;
import com.ok.bean.User;
import com.ok.dao.AdminDao;
import com.ok.dao.OkBase;
import com.ok.tool.MailSender;
import com.ok.tool.Md5;
import org.apache.commons.mail.EmailException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-6-12
 * Time: 21:48:41
 */
public class AdminDaoImpl extends OkBase implements AdminDao {

    @Override
    public List<IndexImg> getIndexImg(final int pages, final int size) {

        final int start = pages <= 0 ? 0 : (pages - 1) * size;
        final String hql = "from IndexImg   order by oedds";
        List<IndexImg> user_tmp = hibernateTemplate.executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                query.setFirstResult(start);
                query.setMaxResults(size);
                List list = query.list();
                return list;
            }
        });
        return user_tmp;
//        return hibernateTemplate.find("from IndexImg");
    }

    @Override
    public void deleteIndexImg(int imgid) {
        jdbctemplate.execute("delete from indeximg where imgid=" + imgid);

    }

    @Override
    public void addIndexImg(IndexImg img) {
        hibernateTemplate.save(img);
    }

    @Override
    public void editeImdeImg(IndexImg img) {
        hibernateTemplate.update(img);
    }

    @Override
    public IndexImg getImdeImg(int imgid) {
        List<IndexImg> lists=hibernateTemplate.find("from IndexImg where imgid="+imgid)   ;
        if(lists==null){
          return null;
        } if(lists.size()<1){
          return null;
        }
        return lists.get(0);
    }

    @Override
    public void forgetPwd(String email) {
        String new_pwd= Md5.getMD5((System.currentTimeMillis()+"").getBytes()).substring(0,6);
        String new_pwd_md5= Md5.getMD5(new_pwd.getBytes());
        //入库
         jdbctemplate.execute("update user set password='"+new_pwd_md5+"' where email='" + email+"'");
        //邮件通知
        try {
            MailSender.sendMail_forgetpwd(email,new_pwd);
        } catch (EmailException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void sendEmailInvent(String sendto, String tpl_path, User user) throws EmailException {
        MailSender.sendMailInvent(sendto,tpl_path,user.getEmail());
    }

}
