package  com.bigdog.carfinder.android.util.net.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import com.bigdog.carfinder.android.api.httpRequest.ApiProvider;
import com.bigdog.carfinder.android.application.CarFinderApplication;
import com.bigdog.carfinder.android.util.SystemUtil;
import com.bigdog.carfinder.android.util.json.JsonObject;
import com.bigdog.carfinder.android.util.json.JsonParser;
import com.bigdog.carfinder.android.util.json.JsonValue;
import com.bigdog.carfinder.android.util.net.INetProvider;
import com.bigdog.carfinder.android.util.net.INetRequest;
import com.bigdog.carfinder.android.util.net.INetResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import com.bigdog.carfinder.android.conf.Variables;


public class HttpProviderWrapper implements INetProvider {
	private static INetProvider instance = new HttpProviderWrapper();
	private static Object lock = new Object();

	private HttpProviderWrapper() {
		System.setProperty("http.keepAlive", "true");
	}

	public static INetProvider getInstance() {
		return instance;
	}

	private Vector<INetRequest> reqTxtVec = new Vector<INetRequest>();
	private Vector<INetRequest> reqImgVec = new Vector<INetRequest>();
	private Vector<INetRequest> reqPostImgVec = new Vector<INetRequest>();
	private Vector<INetRequest> reqEnquireVec = new Vector<INetRequest>();
	private HttpThread[] txtThreads = null;
	private HttpThread[] imgThreads = null;
	private HttpThread postImgThreads = null;
	private HttpThread enquireThreads = null;

	// private static int a;

	private void checkThreads() {

		if (txtThreads == null) {
			txtThreads = new HttpThread[Variables.Settings.TEXT_THREAD_NUM];
			for (int i = 0; i < txtThreads.length; i++) {
				txtThreads[i] = new HttpThread(reqTxtVec);
				txtThreads[i].start();
			}
		}
		if (null == imgThreads) {
			imgThreads = new HttpThread[Variables.Settings.IMG_THREAD_NUM];
			for (int i = 0; i < imgThreads.length; i++) {
				imgThreads[i] = new HttpThread(reqImgVec);
				imgThreads[i].setPriority(Thread.NORM_PRIORITY);
				imgThreads[i].start();
			}
		}
		if (null == postImgThreads) {
			postImgThreads = new HttpThread(reqPostImgVec);
			postImgThreads.start();
		}

		if (null == enquireThreads) {
			enquireThreads = new HttpThread(reqEnquireVec);
			enquireThreads.start();
		}
	}

	public void addRequest(INetRequest req, int priority) {
		synchronized (HttpProviderWrapper.this) {
			checkThreads();
//		}		
		if (INetRequest.TYPE_HTTP_GET_IMG == req.getType() || INetRequest.TYPE_HTTP_GET_EMONTICONS == req.getType()) {
			synchronized (reqImgVec) {
				switch (priority) {
				case INetRequest.PRIORITY_HIGH_PRIORITY:
					reqImgVec.insertElementAt(req, 0);
					reqImgVec.notify();					
					break;
				case INetRequest.PRIORITY_LOW_PRIORITY:
					reqImgVec.addElement(req);					
					reqImgVec.notify();
					break;
				}
			}
		} else if (INetRequest.TYPE_HTTP_POST_IMG == req.getType() || INetRequest.TYPE_HTTP_SYNC_CONTACT == req.getType()) {
			synchronized (reqPostImgVec) {
				reqPostImgVec.addElement(req);
				reqPostImgVec.notify();
			}

		} else if (INetRequest.TYPE_HTTP_CHAT == req.getType()) {
			synchronized (reqEnquireVec) {
				reqEnquireVec.addElement(req);
				reqEnquireVec.notify();
			}
		} else {
			synchronized (reqTxtVec) {
				switch (priority) {
				case INetRequest.PRIORITY_HIGH_PRIORITY:
					reqTxtVec.insertElementAt(req, 0);
					reqTxtVec.notify();					
					break;
				case INetRequest.PRIORITY_LOW_PRIORITY:
					reqTxtVec.addElement(req);					
					reqTxtVec.notify();
					break;
				}
			}
			}
		}
	}

	// 读取本地图片缓存
	// private boolean loadImageCache(final INetRequest currentRequest) {
	//
	// String url_str = currentRequest.getUrl();
	// if (INetRequest.TYPE_HTTP_GET_IMG == currentRequest.getType()) {
	// JsonObject resp = new JsonObject();
	// byte[] img = null;
	// try {
	// img = Data.getImageData(url_str);
	// if (img != null) {
	// resp.put(INetResponse.IMG_DATA, img);
	// INetResponse currentResponse = currentRequest.getResponse();
	// currentResponse.response(currentRequest, resp);
	// return true;
	// }
	// } catch (FileNotFoundException e) {
	// Data.delUnrelatedImgData(url_str);
	// return false;
	// }
	// }
	// return false;
	// }

	/**
	 * 增加一个请求，默认的文本请求优先级最高，会插入到队列顶部
	 */
	public void addRequest(INetRequest req) {
//		Log.v("ff","=================================add req");
		// if(loadImageCache(req)){
		// return;
		// }
		synchronized (HttpProviderWrapper.this) {
			checkThreads();
		}	
//		Log.v("ff","=================================add req 2");
		if (INetRequest.TYPE_HTTP_GET_IMG == req.getType() || INetRequest.TYPE_HTTP_GET_EMONTICONS == req.getType()) {
			synchronized (reqImgVec) {
				reqImgVec.addElement(req);
				reqImgVec.notify();
			}
		} else if (INetRequest.TYPE_HTTP_POST_IMG == req.getType() || INetRequest.TYPE_HTTP_SYNC_CONTACT == req.getType()) {
			synchronized (reqPostImgVec) {
				reqPostImgVec.addElement(req);
				reqPostImgVec.notify();
			}
		} else if (INetRequest.TYPE_HTTP_CHAT == req.getType()) {
			synchronized (reqEnquireVec) {
				reqEnquireVec.addElement(req);
				reqEnquireVec.notify();
			}
		} else {
			synchronized (reqTxtVec) {
				reqTxtVec.insertElementAt(req, 0);
//				Log.v("wt","================================reqTex:"+reqTxtVec.size());
				// reqTxtVec.addElement(req);
				reqTxtVec.notify();
			}
		}
	}

	public void cancel() {
		synchronized (reqImgVec) {
			reqImgVec.clear();
		}
	}

	public void stop() {
		synchronized (HttpProviderWrapper.this) {
			if (null != txtThreads) {
				synchronized (reqTxtVec) {
					for (int i = 0; i < txtThreads.length; i++) {
						if (null != txtThreads[i]) {
//							synchronized (txtThreads[i].reqVec) {
								txtThreads[i].running = false;
//								txtThreads[i].reqVec.clear();
//								txtThreads[i].reqVec.notify();
//							}
						}
					}
					reqTxtVec.clear();
					reqTxtVec.notifyAll();
					txtThreads = null;
				}
			}
			
			// 为了保证退出后也能同步通讯录头像
			if (null != imgThreads) {
				synchronized (reqImgVec) {
					for (int i = 0; i < imgThreads.length; i++) {
						if (null != imgThreads[i]) {
//							synchronized (imgThreads[i].reqVec) {
								imgThreads[i].running = false;
								imgThreads[i].burnning = false;
//								txtThreads[i].reqVec.clear();
//								txtThreads[i].reqVec.notify();
//							}
						}
					}
					reqImgVec.clear();
					reqImgVec.notifyAll();
					imgThreads = null;
				}
			}

			if (null != postImgThreads) {
				synchronized (postImgThreads.reqVec) {
					postImgThreads.running = false;
					postImgThreads.burnning = true;
					postImgThreads.reqVec.notify();
				}
				postImgThreads = null;
			}

			if (null != enquireThreads) {
				synchronized (enquireThreads.reqVec) {
					enquireThreads.running = false;
					enquireThreads.burnning = false;
					enquireThreads.reqVec.notify();
				}
				enquireThreads = null;
			}
		}
	}

	static class HttpThread extends Thread {
		private Vector<INetRequest> reqVec = null;
		private JsonObject error = new JsonObject();

		public HttpThread(Vector<INetRequest> reqVec) {
			this.reqVec = reqVec;
			setPriority(Thread.NORM_PRIORITY);
			error.put("error_code", -99);
			error.put("error_msg", "连接服务器错误，请稍后再试！");
		}

		protected boolean running = true;

		/**
		 * 等所有请求都发送完成后关闭
		 */
		protected boolean burnning = false;

		@Override
		public void run() {
			INetRequest currentRequest = null;
			boolean reconnect = false;
			while (running || burnning) {
//				Thread.yield();
				if (!reconnect) {
					synchronized (reqVec) {
						if (reqVec.size() > 0) {
							currentRequest = reqVec.firstElement();
							reqVec.removeElementAt(0);
						} else {
							if (burnning) {
								burnning = false;
								return;
							}
							try {
								reqVec.wait();
								continue;
							} catch (InterruptedException e) {
							}
						}
					}
				}
				
				HttpClient httpClient = null;
				if (currentRequest != null) {
					String url_str = currentRequest.getUrl();
					// data 为null 表示get方式获取图片
					INetResponse currentResponse = currentRequest.getResponse();
					
//					if (currentRequest.getType() == INetRequest.TYPE_HTTP_CHAT||currentRequest.getType() == INetRequest.TYPE_HTTP_SEND_CHAT) {
//						Log.v("wt","======================"+currentRequest.getParamsString());
//					}


					try {

						HttpRequestBase httpRequest = null;
						if (INetRequest.TYPE_HTTP_GET_IMG == currentRequest.getType() || INetRequest.TYPE_HTTP_GET_EMONTICONS == currentRequest.getType()) {// 获取图片
							httpRequest = new HttpGet(url_str);
							httpRequest.addHeader("Referer", ApiProvider.api_finder_Host);
							httpRequest.addHeader("Accept", "*/*");
							JsonObject temp = new JsonObject();
							temp.put("error_code", -90);
							temp.put("error_msg", "获取图片失败");
							error = temp;
						} else if (INetRequest.TYPE_HTTP_GET_HTML == currentRequest.getType()) {
							httpRequest = new HttpGet(url_str);
							httpRequest.addHeader("Accept", "*/*");
							JsonObject temp = new JsonObject();
							temp.put("error_code", -91);
							temp.put("error_msg", "页面访问失败");
							error = temp;
						} else {
							httpRequest = new HttpPost(url_str);
							JsonObject temp = new JsonObject();
							temp.put("error_code", -99);
							temp.put("error_msg", "无法连接网络，请检查您的手机网络设置...");
							error = temp;
							if (INetRequest.TYPE_HTTP_POST_IMG == currentRequest.getType()) {
								httpRequest.addHeader("Content-Type", "multipart/form-data; charset=UTF-8; boundary=FlPm4LpSXsE");// 发送图像数据
							} else {
								httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");// 发送属性
							}

							// httpRequest.addHeader("Connection",
							// "Keep-Alive");
							httpRequest.addHeader("Connection", "keep-alive");
							httpRequest.addHeader("Accept", "*/*");
							byte[] requestBytes = currentRequest.serialize();
							HttpEntity entity = new ByteArrayEntity(requestBytes);
                            SystemUtil.log("content lenght:"+entity.getContentLength());
                            SystemUtil.log("request parameter value:"+currentRequest.getParamsString());
							((HttpPost) httpRequest).setEntity(entity);
						}

						
						BasicHttpParams httpParameters = new BasicHttpParams();

						
						if (INetRequest.TYPE_HTTP_POST_IMG == currentRequest.getType()) {
							httpParameters.setIntParameter(HttpConnectionParams.SO_TIMEOUT, 90000); // 超时设置
							httpParameters.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 90000);
							httpParameters.setIntParameter(HttpConnectionParams.SOCKET_BUFFER_SIZE, 8192 * 4);
						} else {
							httpParameters.setIntParameter(HttpConnectionParams.SO_TIMEOUT, 45000); // 超时设置
							httpParameters.setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 45000);
							httpParameters.setIntParameter(HttpConnectionParams.SOCKET_BUFFER_SIZE, 8192);
						}

						// if(INetRequest.TYPE_HTTP_POST_IMG ==
						// currentRequest.getType()){
						// HttpConnectionParams.setSocketBufferSize(httpParameters,
						// 8192*4);
						// HttpConnectionParams.setConnectionTimeout(httpParameters,
						// 60000);
						// HttpConnectionParams.setSoTimeout(httpParameters,
						// 120000);
						// }else{
						// HttpConnectionParams.setConnectionTimeout(httpParameters,
						// 20000);
						// HttpConnectionParams.setSoTimeout(httpParameters,
						// 50000);
						// HttpConnectionParams.setSocketBufferSize(httpParameters,
						// 8192);
						// }
						httpClient = new DefaultHttpClient(httpParameters);
						HttpHost host = HttpProxy.getProxyHttpHost(CarFinderApplication.getAppContext());
						if (host != null) {
							httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
						}
						httpRequest.removeHeaders(HTTP.EXPECT_DIRECTIVE);


                        //  debug=====================================
                      /*  SystemUtil.log("request method:"+httpRequest.getMethod()+",ProtocolVersion:"+httpRequest.getProtocolVersion()
                                       +",Url:"+httpRequest.getURI()+",RequestLine:"+httpRequest.getRequestLine()
                                      +",parammeter_entity_content:"+((HttpPost)httpRequest).getEntity().getContent().toString());

                        InputStream inputStream = ((HttpPost)httpRequest).getEntity().getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line = "";
                        SystemUtil.log("----------------------");
                        while((line = reader.readLine())!=null){
                          SystemUtil.log(line);
                        }
                        SystemUtil.log("----------------------");*/

                        //  =====================================debug

						HttpResponse httpResponse = httpClient.execute(httpRequest);

						if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

							String headerField = null;
							try {
								headerField = httpResponse.getFirstHeader("Content-Type").getValue();
							} catch (Exception e) {
							}
							if (headerField != null) {
								if (headerField.startsWith("text/vnd.wap.wml") || headerField.startsWith("application/vnd.wap.wmlc")) {
									if (reconnect) {
										if (null != currentResponse) {
											currentResponse.response(currentRequest, error);
										}
										reconnect = false;
									} else {
										reconnect = true;
										continue;
									}
								}
							}

							InputStream is = httpResponse.getEntity().getContent();
							if (currentRequest.useGzip()) {
								is = new GZIPInputStream(is);
							}
							byte[] buf = SystemUtil.toByteArray(is);
							JsonValue resp = null;
							if (INetRequest.TYPE_HTTP_GET_HTML == currentRequest.getType()) {
								String s = new String(buf, "UTF-8");
								s = s.replace("\\r", "");
								JsonObject respObj = new JsonObject();
								respObj.put(INetResponse.HTML_DATA, s);
								resp = respObj;
							} else if (INetRequest.TYPE_HTTP_GET_IMG == currentRequest.getType() || INetRequest.TYPE_HTTP_GET_EMONTICONS == currentRequest.getType()) {// 获取图片
								JsonObject respObj = new JsonObject();
								respObj.put(INetResponse.IMG_DATA, buf);
								resp = respObj;
								if (INetRequest.TYPE_HTTP_GET_IMG == currentRequest.getType()) {
                                    //下载图片    buf为在下到的图片本身
									/*if (Data.saveImage(buf, url_str)) {
										NewImagePool.addImageCache(url_str);
									}*/
								}
							} else {// 请求接口
								resp = deserialize(buf, currentRequest);
							}

							if (null != currentResponse) {
								if (running) {
									currentResponse.response(currentRequest, resp);
								} else {
									// 为了在stop后还能够下载图片
									if (INetRequest.TYPE_HTTP_REST != currentRequest.getType()) {
										currentResponse.response(currentRequest, resp);
									}
								}
							}
							reconnect = false;
						} else {
							if (reconnect) {
								if (null != currentResponse) {
									currentResponse.response(currentRequest, error);
								}
								reconnect = false;
							} else {
								reconnect = true;
								continue;
							}
						}
						// 下面的处理过程不完整，没有给response返回，所以在response中关闭等待框的逻辑得不到处理
					} catch (UnknownHostException unKnownHost) {
						JsonObject ret = new JsonObject();
						ret.put("error_code", -97);
						ret.put("error_msg", "无法连接网络，请检查您的手机网络设置...");
						if (null != currentResponse) {
							currentResponse.response(currentRequest, ret);
						}
						continue;
					}

					catch (Exception e) {// 其他异常情况						
						e.printStackTrace();
						if (reconnect) {
							if (null != currentResponse) {
								if (currentRequest.getPriority() != INetRequest.PRIORITY_LOW_PRIORITY) { // 对于低优先级的请求错误不予提示
									currentResponse.response(currentRequest, error);
								}
							}
							reconnect = false;
						} else {
							reconnect = true;
							continue;
						}
					} finally {
						try {
							if (httpClient != null) {
								httpClient.getConnectionManager().shutdown();
							}
						} catch (Exception e) {

						}
					}
				}
			}
		}
	}

	private static JsonValue deserialize(byte[] data, INetRequest currentRequest) {
		try {
			if (data == null) {
				return null;
			}
			String s = new String(data, "UTF-8");
			s = s.replace("\\r", "");
			if (INetRequest.TYPE_HTTP_CHAT == currentRequest.getType()||INetRequest.TYPE_HTTP_SEND_CHAT == currentRequest.getType()) {
				JsonObject jo = new JsonObject();
				jo.put("message", s);
				return jo;
			} else {
				return JsonParser.parse(s);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
