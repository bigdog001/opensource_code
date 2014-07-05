package com.ok.tags;

import com.ok.bean.MmfooHd;
import com.ok.bean.User;
import com.ok.bean.UserHd;
import com.ok.dao.UserDao;
import com.ok.daoimpl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by wangjiunian
 * User: Administrator
 * Date: 2011-5-23
 * Time: 0:19:52
 */
public class TimeDistance   extends TagSupport {
      private static Logger logger = Logger.getLogger(TimeDistance.class);
    private Timestamp start;

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if(start==null){
                start=new Timestamp(System.currentTimeMillis());
            }
            out.print(getTimeDifference(start));
        } catch (IOException e) {
            logger.error(e.getStackTrace());
        }
        return SKIP_PAGE; //忽略对标签体的处理
    }
	
	
public  String getTimeDifference( Timestamp formatTime2) {
        SimpleDateFormat timeformat = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        long t1 = 0L;
//        long t1 = System.currentTimeMillis();
        long t2 = 0L;
//        try {
            t1 = System.currentTimeMillis();
//        } catch (ParseException e) {
            // TODO Auto-generated catch block
//           logger.error(e.getStackTrace());
//        }
        try {
            t2 = timeformat.parse(getTimeStampNumberFormat(formatTime2)).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
           logger.error(e.getStackTrace());
        }
        //因为t1-t2得到的是毫秒级,所以要初3600000得出小时.算天数或秒同理
       int hh=(int) ((t1 - t2)/3600000);
    
        int day=(int) ((t1 - t2)/3600000/24);
        int hours=(int) ((t1 - t2)/3600000-day*24);
        int minutes=(int) (((t1 - t2)/1000-hours*3600-day*24*3600)/60);
        int second=(int) ((t1 - t2)/1000-hours*3600-minutes*60-day*24*3600);
        return (day==0?"":day+"天")+(hours==0?"":hours+"小时")+(minutes==0?"":minutes+"分")+second+"秒 前";
    }
  public  String getTimeStampNumberFormat(Timestamp formatTime) {
        SimpleDateFormat m_format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss", new Locale("zh", "cn"));
        return m_format.format(formatTime);
    }
}
