/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdog.hadoop.hdfs;

import com.bigdog.hadoop.Constants;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.*;

/**
 *
 * @author jw362j
 */
public class HDFS_Test {

    static {
        Configuration.addDefaultResource("hdfs-site.xml");
        Configuration.addDefaultResource("mapred-site.xml");
    }

    public void writeText() {
        String str = "Hello world,this is my first hdfs app\n";
        Configuration config = new Configuration();

        java.io.OutputStream out;
        try {
            FileSystem fs = FileSystem.get(config);
            out = fs.create(new Path(Constants.output + "helloworld.txt"));
            out.write(str.getBytes());
            out.flush();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(HDFS_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createNewHDFSFile(String toCreateFilePath, String content) throws IOException {
        Configuration config = new Configuration();
        FileSystem hdfs = FileSystem.get(config);

        FSDataOutputStream os = hdfs.create(new Path(toCreateFilePath));
        os.write(content.getBytes("UTF-8"));
        os.close();
        hdfs.close();
    }

    public boolean deleteHDFSFile(String dst) throws IOException {
        Configuration config = new Configuration();
        FileSystem hdfs = FileSystem.get(config);

        Path path = new Path(dst);
        boolean isDeleted = hdfs.delete(path);

        hdfs.close();

        return isDeleted;
    }

    public byte[] readHDFSFile(String dst) throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        // check if the file exists
        Path path = new Path(dst);
        if (fs.exists(path)) {
            FSDataInputStream is = fs.open(path);
            // get the file info to create the buffer
            FileStatus stat = fs.getFileStatus(path);

            // create the buffer
            byte[] buffer = new byte[Integer.parseInt(String.valueOf(stat.getLen()))];
            is.readFully(0, buffer);

            is.close();
            fs.close();

            return buffer;
        } else {
            throw new Exception("the file is not found .");
        }
    }

    public void mkdir(String dir) throws IOException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);

        fs.mkdirs(new Path(dir));

        fs.close();
    }

    
}
