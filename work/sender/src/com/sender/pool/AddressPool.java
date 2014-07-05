package com.sender.pool;

import com.sender.db.ConnectionMgr;
import com.sender.send.webRun;
import com.sender.taskbean.MessageBean;
import com.sender.taskbean.configBean;
import com.sender.util.MailAddressValide;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddressPool extends Pool{
    private static Logger logger = Logger.getLogger(AddressPool.class);
    private static int sendLength=configBean.sendLength;
    //地址池为单例，创建后周期性的自维护地址数据
    private static List<String> list_all;//总池 长度3000
    PreparedStatement stmt;
    Connection conn;
    ResultSet rs;
    private static int length = configBean.length;
    private static int loadFirst = 1;   //是否是第一次加载数据
    private static int loadStart = 0;
    private static int query_count = 1;
    private static int load_flag=0;//在加载数据期间不允许做数据发送操作
    private static int File_totalLIne=0;//地址文件的总行数
    private static Long load_start_time;
    private static String addressHDPath= configBean.addressHDPath;
    private AddressPool(){

    }
    private static AddressPool addressPool;
    public static AddressPool getAddressPool() {
        if (addressPool==null) {
            addressPool=new AddressPool();
            return addressPool;
        }
        return addressPool;
    }
    @Override
    public void initPool() {
        list_all=new ArrayList<String>();//总池
    }


    private void LoadData4DB() {
        if (loadFirst==1) {
            loadStart  = ConnectionMgr.getConnectionMgr().getDbinfor().getMail_countstart();
        }
        if (list_all.size() <= 0) {
            load_start_time = System.currentTimeMillis();
            load_flag = 1;
            try {
                conn = ConnectionMgr.getConnectionMgr().getConnection();
                stmt = conn.prepareStatement("select user_id,email from " + ConnectionMgr.getConnectionMgr().getDbinfor().getTable_name() + " limit ?,?");
                stmt.setInt(1, (query_count - 1) * length + loadStart);
                stmt.setInt(2, length);
                rs = stmt.executeQuery();
                while (rs.next()) {
                    String mail_tmp = rs.getString("email");
                    if (mail_tmp != null && !"".equals(mail_tmp.trim())) {
                        if (MailAddressValide.validate(mail_tmp)) {
                            if (list_all.size() % configBean.reportLength == 0) {
                                list_all.add(MessageBean.mailReplyTo);
                            }
                            list_all.add(mail_tmp);
                        }
                    }
                }
            } catch (SQLException e) {
                logger.info(e.getMessage());
            }
            logger.info("加载数据完毕,本次是第" + query_count + "次加载操作,此次从表的第"+(query_count - 1) * length + loadStart+"行开始加载，此次一共加载:" + list_all.size() + "条数据,耗时:" + (System.currentTimeMillis() - load_start_time) + "毫秒!");
            query_count++;
            load_flag = 0;//数据加载完毕，开关复位
            if (list_all.size() == 0) {
                //此时数据库中已经无法加载到数据，应用exit
                if (configBean.isWebMod==1) {
                    webRun.getInstance().stop();
                }else {
                System.exit(0);
                }
            }
            if (loadFirst==1) {
                loadFirst=0;
            }
            System.gc();
        }
    }

    private void LoadData4HD() throws IOException {//磁盘取地址操作
        if (list_all.size()<=0) {
            if (loadFirst==1) {
                loadStart  = ConnectionMgr.getConnectionMgr().getDbinfor().getMail_countstart();
            }
            if (File_totalLIne==0) {  //初始化并缓存文件的总行数
                try {
                    File_totalLIne= getTotalLines(addressHDPath)  ;
                } catch (IOException e) {
                   logger.info(e.getMessage());
                }
            }
            load_start_time = System.currentTimeMillis();
            load_flag = 1;
                 int startLine=loadStart;
                 if (query_count==1) {
                     startLine = loadStart;
                 } else {
                     startLine = startLine+(query_count - 1)*length;
                 }

                 int endLine= (startLine+length)-1;

                 FileReader in = new FileReader(addressHDPath);
                 LineNumberReader reader = new LineNumberReader(in);
                 String s = "";
                 if (startLine < 0 || startLine > getTotalLines(addressHDPath)) {
                     logger.info("目标行不在文件的行数范围(1至总行数)之内。");
                     if (configBean.isWebMod==1) {
                         webRun.getInstance().stop();
                     }else {
                         System.exit(0);
                     }
                 }
                 int lines = 0;
                 while ((s=reader.readLine()) != null) {
                     if ((lines - startLine) >=0) {    //开始取拿到的行
                         if ((lines - startLine)<length) {  //未超出指定范围
                             if (MailAddressValide.validate(s.trim())) {
                                 if (list_all.size()!=0&&list_all.size()%configBean.reportLength==0) {
                                     list_all.add(MessageBean.mailReplyTo);
                                 }
                                 list_all.add(s);
                             }

                         }
                     }
                     lines++;
                 }
                 reader.close();
                 in.close();
            logger.info("加载数据完毕,本次是第" + query_count + "次加载操作,从磁盘文件的第:"+startLine+"行开始加载,此次一共加载:" + list_all.size() + "条数据,耗时:" + (System.currentTimeMillis() - load_start_time) + "毫秒!");
            query_count++;
            load_flag = 0;//数据加载完毕，开关复位
            if (list_all.size() == 0) {
                //此时数据库中已经无法加载到数据，应用exit
                logger.info("已经到达文件末尾,exit.....");
                if (configBean.isWebMod==1) {
                    webRun.getInstance().stop();
                }else {
                    System.exit(0);
                }
            }
            if (loadFirst==1) {
                loadFirst=0;
            }
            System.gc();

        }
    }

    private void load() {
        if ("0".equals(ConnectionMgr.getConnectionMgr().getDbinfor().getLoadhd())) {
            logger.info("数据提供源为DB SERVER。。。");
            LoadData4DB();
        } else if("1".equals(ConnectionMgr.getConnectionMgr().getDbinfor().getLoadhd())) {
            logger.info("数据提供源为磁盘设备。。。");
            try {
                LoadData4HD();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } else {
           // LoadData4DB();
        }
    }

    public String []getSendTarget() {
        if (load_flag==1) {
          logger.info("数据池拉数据中,稍后再发送...");
            return null;
        }
        if (list_all.size() <= 0) {
            load();
        }
        String[] target=null;
        if (list_all.size()>sendLength) {
            target = new String[sendLength];
            for (int x = 0; x < sendLength; x++) {
                target[x] = list_all.get(0);
                list_all.remove(0);
            }
        }else{
            target = new String[list_all.size()];
            for (int x = 0; x < target.length; x++) {
                target[x] = list_all.get(0);
                list_all.remove(0);
            }
        }
        if (target!=null) {
            logger.info("成功获取到目标地址数据,此次一共获取:" + target.length + "条地址,地址池中剩余:" + list_all.size() + "条数据!");
        }

        return target;
    }


    //-工具方法 --------
    //计算一个文件的总行数
    static int getTotalLines(String fliepath) throws IOException {
        load_start_time = System.currentTimeMillis();
        File file=new File(fliepath);
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        logger.info("获取文件的总行数为:"+File_totalLIne+",耗时:"+(System.currentTimeMillis()-load_start_time)+"毫秒!");
        return lines;
    }




    public static  void main(String []args){

    }
    //--------工具方法

}
