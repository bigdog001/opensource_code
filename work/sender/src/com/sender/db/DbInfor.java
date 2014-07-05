package com.sender.db;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/18/12
 * Time: 3:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class DbInfor {
    public String getLoadhd() {
        return loadhd;
    }

    public void setLoadhd(String loadhd) {
        this.loadhd = loadhd;
    }

    private String host_ip;
    private String db_username;
    private String db_pwd;
    private String db_name;
    private String table_name;
    private int mail_countstart=0;
    private String loadhd="0";

    public String toString(){
     return "host_ip:"+host_ip+",db_username:"+db_username+",db_name:"+db_name+",table_name:"+table_name+",mail_countstart:"+mail_countstart+",loadhd:"+loadhd;
    }
    public int getMail_countstart() {
        return mail_countstart;
    }

    public void setMail_countstart(int mail_countstart) {
        this.mail_countstart = mail_countstart;
    }

    public DbInfor(String loadhd,String host_ip,String db_username,String db_pwd, String db_name, String table_name,int mail_countstart){
        this.host_ip=host_ip;
        this.db_username=db_username;
        this.db_pwd=db_pwd;
        this.db_name=db_name;
        this.table_name=table_name;
        this.mail_countstart=mail_countstart;
        this.loadhd=loadhd;
    }
    public String getHost_ip() {
        return host_ip;
    }

    public void setHost_ip(String host_ip) {
        this.host_ip = host_ip;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_pwd() {
        return db_pwd;
    }

    public void setDb_pwd(String db_pwd) {
        this.db_pwd = db_pwd;
    }

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

}
