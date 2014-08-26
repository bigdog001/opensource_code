package com.bigdog.test;

import com.bigdog.hadoop.hdfs.HDFS_Test;
import com.bigdog.hadoop.mapreduce.combine.WordCountCombineApp;
import com.bigdog.hbase.HBaseTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.junit.Test;

public class BigDataTest {

    @Test
    public void test() throws IOException, InterruptedException, Exception {

      HBaseTest h_test = new HBaseTest();
      
      String tablename = "scores";  
            String[] familys = {"grade", "course"};  
            h_test.creatTable(tablename, familys);  
    }
}
