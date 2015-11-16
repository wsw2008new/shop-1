package cn.my360.shop.http;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.FileBody;
import com.lidroid.xutils.http.client.multipart.content.StringBody;

public class HttpUtil {

	public synchronized String postDataMethod(String serviceURI,
			MultipartEntity mpEntity) {
		String responseStr = "";
		try {
			HttpPost httpRequest = new HttpPost(serviceURI);
			HttpParams params = new BasicHttpParams();
			ConnManagerParams.setTimeout(params, 6000); // 从连接池中获取连接的超时时间
			HttpConnectionParams.setConnectionTimeout(params, 15000);// 通过网络与服务器建立连接的超时时间
			HttpConnectionParams.setSoTimeout(params, 30000);// 读响应数据的超时时间
			httpRequest.setParams(params);
			// 下面开始跟服务器传递数据，使用BasicNameValuePair

			httpRequest.setEntity(mpEntity);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			final int ret = httpResponse.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				responseStr = EntityUtils.toString(httpResponse.getEntity(),
						HTTP.UTF_8);
				// responseStr = DesEncrypt.getDesString(responseStr);
			} else {
				responseStr = "";
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("reslog", responseStr);

		return responseStr;

	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @param serviceURI
	 * @param tokens
	 * @return
	 */
	public synchronized String postFileMethod(File file, String serviceURI,
			String loginkey, String id, String num) {
		String responseStr = "";
		try {
			HttpPost httpRequest = new HttpPost(serviceURI);
			HttpParams params = new BasicHttpParams();
			ConnManagerParams.setTimeout(params, 6000); // 从连接池中获取连接的超时时间
			HttpConnectionParams.setConnectionTimeout(params, 15000);// 通过网络与服务器建立连接的超时时间
			HttpConnectionParams.setSoTimeout(params, 30000);// 读响应数据的超时时间
			httpRequest.setParams(params);
			// 下面开始跟服务器传递数据，使用BasicNameValuePair

			MultipartEntity mpEntity = new MultipartEntity();

			mpEntity.addPart("orderID", new StringBody(id));
			mpEntity.addPart("uuid", new StringBody(loginkey));
			mpEntity.addPart("uploadImage", new FileBody(file));
			mpEntity.addPart("orderNum", new StringBody(num));

			httpRequest.setEntity(mpEntity);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			final int ret = httpResponse.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				responseStr = EntityUtils.toString(httpResponse.getEntity(),
						HTTP.UTF_8);
			} else {
				responseStr = "";
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("reslog", responseStr);
		return responseStr;
	}

	/**
	 * @param url
	 *            请求URL
	 * @return 请求结果
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public static String getRequest(String url) throws ClientProtocolException,
			IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(get);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(httpResponse.getEntity(),
					"utf-8");
			return result;
		}
		return null;
	}

	public synchronized String postDataMethod(String serviceURI, String user,
			String pwd) {
		String responseStr = "";
		try {
			HttpPost httpRequest = new HttpPost(serviceURI);
			HttpParams params = new BasicHttpParams();
			ConnManagerParams.setTimeout(params, 6000); // 从连接池中获取连接的超时时间
			HttpConnectionParams.setConnectionTimeout(params, 15000);// 通过网络与服务器建立连接的超时时间
			HttpConnectionParams.setSoTimeout(params, 30000);// 读响应数据的超时时间
			httpRequest.setParams(params);
			// 下面开始跟服务器传递数据，使用BasicNameValuePair

			MultipartEntity mpEntity = new MultipartEntity();

			mpEntity.addPart("e.username", new StringBody(user));
			mpEntity.addPart("e.password", new StringBody(pwd));

			httpRequest.setEntity(mpEntity);

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			final int ret = httpResponse.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				responseStr = EntityUtils.toString(httpResponse.getEntity(),
						HTTP.UTF_8);
			} else {
				responseStr = "";
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("reslog", responseStr);
		return responseStr;
	}

	public static String postRequest(String url, List<NameValuePair> params) {
		// 创建httpRequest对象
		HttpPost httpRequest = new HttpPost(url);
		try {
			HttpParams httpParams = new BasicHttpParams();
			ConnManagerParams.setTimeout(httpParams, 10000); // 从连接池中获取连接的超时时间
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);// 通过网络与服务器建立连接的超时时间
			HttpConnectionParams.setSoTimeout(httpParams, 30000);// 读响应数据的超时时间
			httpRequest.setParams(httpParams);
			// 设置字符集
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
			// 请求httpRequest
			httpRequest.setEntity(httpentity);
			// 取得默认的HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// 取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// HttpStatus.SC_OK表示连接成功
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 取得返回的字符串
				String strResult = EntityUtils.toString(httpResponse
						.getEntity());
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
