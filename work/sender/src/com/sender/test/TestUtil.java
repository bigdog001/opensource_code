package com.sender.test;

import com.sender.db.ConnectionMgr;
import com.sender.taskbean.MessageBean;
import com.sender.taskbean.configBean;
import com.sender.util.MailAddressValide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 10/20/12
 * Time: 8:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtil {

    public static void main(String[]sss){
        PreparedStatement stmt;
        Connection conn;
        ResultSet rs;
        try {
            conn= ConnectionMgr.getConnectionMgr().getConnection();
            stmt = conn.prepareStatement("select email from user_dangdang_all_order_big");
            rs = stmt.executeQuery();
            while (rs.next()) {
                 System.out.println( rs.getString("email"));
            }
        } catch (SQLException e) {
        }
    }

}
