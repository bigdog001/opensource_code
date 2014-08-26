/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdog.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author jw362j
 */
public class HiveApp {

    public void scan() throws Exception {
        Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver");
        Connection con = DriverManager.getConnection("jdbc:hive://hadoop0:10000/default", "", "");
        Statement stmt = con.createStatement();
        String querySQL = "SELECT * FROM default.t1";
        ResultSet res = stmt.executeQuery(querySQL);
        while (res.next()) {
            System.out.println(res.getInt(1));
        }

    }

}
