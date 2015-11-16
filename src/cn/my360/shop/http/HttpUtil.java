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
			ConnManagerParams.setTimeout(params, 6000); // �����ӳ��л�ȡ���ӵĳ�ʱʱ��
			HttpConnectionParams.setConnectionTimeout(params, 15000);// ͨ��������������������ӵĳ�ʱʱ��
			HttpConnectionParams.setSoTimeout(params, 30000);// ����Ӧ���ݵĳ�ʱʱ��
			httpRequest.setParams(params);
			// ���濪ʼ���������������ݣ�ʹ��BasicNameValuePair

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
	 * �ϴ��ļ�
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
			ConnManagerParams.setTimeout(params, 6000); // �����ӳ��л�ȡ���ӵĳ�ʱʱ��
			HttpConnectionParams.setConnectionTimeout(params, 15000);// ͨ��������������������ӵĳ�ʱʱ��
			HttpConnectionParams.setSoTimeout(params, 30000);// ����Ӧ���ݵĳ�ʱʱ��
			httpRequest.setParams(params);
			// ���濪ʼ���������������ݣ�ʹ��BasicNameValuePair

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
	 *            ����URL
	 * @return ������
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
			ConnManagerParams.setTimeout(params, 6000); // �����ӳ��л�ȡ���ӵĳ�ʱʱ��
			HttpConnectionParams.setConnectionTimeout(params, 15000);// ͨ��������������������ӵĳ�ʱʱ��
			HttpConnectionParams.setSoTimeout(params, 30000);// ����Ӧ���ݵĳ�ʱʱ��
			httpRequest.setParams(params);
			// ���濪ʼ���������������ݣ�ʹ��BasicNameValuePair

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
		// ����httpRequest����
		HttpPost httpRequest = new HttpPost(url);
		try {
			HttpParams httpParams = new BasicHttpParams();
			ConnManagerParams.setTimeout(httpParams, 10000); // �����ӳ��л�ȡ���ӵĳ�ʱʱ��
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);// ͨ��������������������ӵĳ�ʱʱ��
			HttpConnectionParams.setSoTimeout(httpParams, 30000);// ����Ӧ���ݵĳ�ʱʱ��
			httpRequest.setParams(httpParams);
			// �����ַ���
			HttpEntity httpentity = new UrlEncodedFormEntity(params, "utf-8");
			// ����httpRequest
			httpRequest.setEntity(httpentity);
			// ȡ��Ĭ�ϵ�HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// ȡ��HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			// HttpStatus.SC_OK��ʾ���ӳɹ�
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// ȡ�÷��ص��ַ���
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
