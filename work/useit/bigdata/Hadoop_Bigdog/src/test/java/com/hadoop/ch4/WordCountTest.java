package com.hadoop.ch4;
import com.bigdog.hadoop.hdfs.HDFS_Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.junit.Test;
 

public class WordCountTest { 
	 

	@Test
	public void test() throws IOException, InterruptedException {
	HDFS_Test hdfs = new HDFS_Test();
        hdfs.writeText();
		 
		 
	}
}
