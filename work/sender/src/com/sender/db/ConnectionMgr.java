package com.sender.db;

import com.sender.taskbean.configBean;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionMgr {
    private static Logger logger = Logger.getLogger(ConnectionMgr.class);
    private static Connection connection;
    private static ConnectionMgr connectionMgr;
    private static DbInfor dbinfor=null;
    private static String dbinfPath= configBean.dbinfPath;
    private ConnectionMgr(){
        logger.info("开始创建数据库链接对象.....");
    }
     public static ConnectionMgr getConnectionMgr() {
         if (connectionMgr==null) {
             connectionMgr=new ConnectionMgr();
         }
         return connectionMgr;
     }
    public void iniTConnection() throws SQLException {
        if(connection==null||connection.isClosed()){
            initConnectionbyjdbc();
        }
    }
     public Connection getConnection() throws SQLException {
         iniTConnection();
         return connection;
     }

    static public DbInfor initDB() {
        if (dbinfor==null) {
            logger.info("开始从配置文件中初始化数据库链接信息!");
            Properties properties = new Properties();
            try {
                InputStream fis = new FileInputStream(dbinfPath);
                properties.load(fis);
            } catch (FileNotFoundException e) {
                logger.info("无法找到数据库链接配置文件 " + e.getMessage());
            } catch (IOException e) {
                logger.info("配置文件读取错误 ：" + e.getMessage());
            }
            String db_name = properties.getProperty("db_name");
            String host_ip = properties.getProperty("host_ip");
            String db_username = properties.getProperty("db_username");
            String db_pwd = properties.getProperty("db_pwd");
            String table_name = properties.getProperty("table_name");
            String loadhd=  properties.getProperty("loadhd");
            int mail_countstart = Integer.valueOf(properties.getProperty("mail_countstart").trim());
            if (db_name != null && host_ip != null && db_username != null && db_pwd != null && table_name != null) {
                dbinfor = new DbInfor(loadhd,host_ip.trim(), db_username.trim(), db_pwd.trim(), db_name.trim(), table_name.trim(),mail_countstart);
                logger.info("成功初始化数据库链接信息!" + dbinfor.getHost_ip().trim() + "," + dbinfor.getDb_username().trim() + "," + dbinfor.getDb_pwd().trim() + "," + dbinfor.getDb_name().trim() + "," + dbinfor.getTable_name().trim());

                return dbinfor;
            } else {
                logger.info("初始化数据库信息时出错!");
                return null;
            }
        }
        logger.info("成功取得数据库参数:"+dbinfor);
        return dbinfor;
    }

    public void initConnectionbyjdbc() {
        DbInfor dbInfors=initDB();
        try {
            //装载驱动包类
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.info("装载驱动包出现异常!请查正！"+e.getMessage());
            e.printStackTrace();
        }
        try {
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + dbInfors.getHost_ip() + ":3306/" + dbInfors.getDb_name()+"?useUnicode=true&characterEncoding=UTF8", dbInfors.getDb_username(), dbInfors.getDb_pwd());
            logger.info("成功链接到数据库!");
        } catch (SQLException e) {
            logger.info("链接数据库发生异常！" + e.getMessage());
        }
    }

    public DbInfor getDbinfor() {
        if (dbinfor==null) {
            return  initDB();
        }
        return dbinfor;
    }

}
