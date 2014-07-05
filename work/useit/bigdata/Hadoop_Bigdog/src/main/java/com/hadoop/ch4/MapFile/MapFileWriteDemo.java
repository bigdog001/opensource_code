package com.hadoop.ch4.MapFile;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapFile;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class MapFileWriteDemo {

	/**
	 * @param args
	 */
	private static final String[] DATA = { "One ,two,woeljl",
			"Three ,two,woeljl", "Two ,two,woeljl", "FIve ,two,woeljl",
			"Seven ,two,woeljl", "Nine ,two,woeljl", "ddd ,two,woeljl",
			"ff ,two,woeljl", "hh ,two,woeljl" };

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);

		IntWritable key = new IntWritable();
		Text value = new Text();
		MapFile.Writer writer = null;
		try {
			writer = new MapFile.Writer(conf, fs, uri, key.getClass(),
					value.getClass());
			writer.setIndexInterval(200);
			for (int i = 0; i < 1024; i++) {
				key.set(i + 1);
				value.set(DATA[i % DATA.length]);
				writer.append(key, value);
			}
		} finally {
			IOUtils.closeStream(writer);
		}
	}
}
