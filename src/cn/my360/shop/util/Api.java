package cn.my360.shop.util;

import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class Api {
	private static Api api;
	private static HttpUtils http;

	private Api() {
		if (http == null) {
			http = new HttpUtils();
			http.configCurrentHttpCacheExpiry(0);
			http.configTimeout(1000 * 5);
			http.configRequestRetryCount(3);
		}
	}

	public static Api get() {
		if (api == null)
			api = new Api();
		return api;
	}

	

	public void sendPost(final String url, final RequestParams params, final Handler hd, final int what) {

		if (!AppStaticUtil.isNetwork(App.appContext)) {
			hd.obtainMessage(-2).sendToTarget();
			return;
		}

		App.showLog("请求参数：" + JSON.toJSONString(params));
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				App.showLog("\n返回结果是" + arg0.result.replace("\\", ""));
				if (hd != null) {
					hd.obtainMessage(what, arg0.result).sendToTarget();
				} else {
					App.showLog("hd is nulllllll");
				}
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				App.showLog(("错误原因：" + arg0.getExceptionCode() + "::" + arg1));
				hd.obtainMessage(-1).sendToTarget();
			}
		});
	}

	/**
	 * get方法
	 */
	public void sendGet(final String getUrl, final Handler hd) {
		if (!AppStaticUtil.isNetwork(App.appContext)) {
			hd.obtainMessage(-2).sendToTarget();
			return;
		}
		http.send(HttpMethod.GET, getUrl, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				App.showLog("\n返回结果是" + arg0.result);
				hd.obtainMessage(1, arg0.result).sendToTarget();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				App.showLog(("错误原因：" + arg0.getExceptionCode() + "::" + arg1));
				hd.obtainMessage(0).sendToTarget();
			}
		});
	}

	

	public String getData(String id) {
		return id.substring((id.indexOf("\"") + 1), id.lastIndexOf("\""));
	}

	

}
