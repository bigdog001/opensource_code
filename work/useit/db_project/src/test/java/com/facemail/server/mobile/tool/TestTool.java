package com.facemail.server.mobile.tool;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/2/13
 * Time: 11:52 AM
 */
public class TestTool {
    Logger logger = Logger.getLogger(this.getClass());

    public static String TEST_URL="http://localhost:8080/api.action";
    public  void postMethod(String url,List<TestParameter> testParameters) throws Exception {
        //POST的URL
        HttpPost httppost=new HttpPost(url);
        //建立HttpPost对象
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        //建立一个NameValuePair数组，用于存储欲传送的参数
        for (TestParameter tp:testParameters) {
//            logger.info("tp key and value:"+tp.getKey()+",value:"+tp.getValue());
            params.add(new BasicNameValuePair(tp.getKey(),tp.getValue()));
        }
        //添加参数
        httppost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
        //设置编码
        HttpResponse response=new DefaultHttpClient().execute(httppost);
        int statusCode = response.getStatusLine().getStatusCode();
        logger.info("http statusCode:"+statusCode);
        if(statusCode==200){//如果状态码为200,就是正常返回
            String result=EntityUtils.toString(response.getEntity());
            //得到返回的字符串
            logger.info(result);
        }
    }
}
