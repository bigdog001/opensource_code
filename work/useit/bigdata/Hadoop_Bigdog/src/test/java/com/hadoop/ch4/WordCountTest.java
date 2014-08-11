package com.hadoop.ch4;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.reduce.IntSumReducer;
import org.junit.Test;
import org.mockito.Mockito;

public class WordCountTest { 
	/*** Mapper.Context类. */
	private org.apache.hadoop.mapreduce.Mapper.Context mapContext;
	/*** Reducer.Context类. */
	private org.apache.hadoop.mapreduce.Reducer.Context reduceContext = Mockito
			.mock(org.apache.hadoop.mapreduce.Reducer.Context.class);
	private IntSumReducer intSumReducer = new IntSumReducer();

	@Test
	public void testReduce() throws IOException, InterruptedException {
		// 准备测试数据
		Text key = new Text("good");
		List list = new ArrayList();
		list.add(new IntWritable(2));
		list.add(new IntWritable(3));
		intSumReducer.reduce(key, list, reduceContext);
		// 验证结果
		Mockito.verify(reduceContext).write(new Text("good"),
				new IntWritable(5));
	}
}
