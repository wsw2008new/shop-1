package cn.my360.shop.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import cn.my360.shop.entity.UserInfo;



public class App extends Application  {
	static String TAG = App.class.getSimpleName();
	static final boolean DEBUG = true;
	static Toast mToast = null;
	public AlertDialog updateDialog;
	public final static String CONF_APP_UNIQUEID = "CONF_APP_UNIQUEID";
	// public static Version mVersion = new Version();



	// httpclient
	private static HttpClient mHttpClient = null;
	private static final String CHARSET = HTTP.UTF_8;

	public static Context appContext;
	public static String USERINFO = "USERINFO";
	public static String username = "";
	public static String userID = "";
	public static String uuid = "";
	public static String userphone = "";
	public static String userphoto = "";
	public static String usertype = "";
	public static String province = "";
	public static String city = "";
	public static String area = "";
	public static String authentication = "";

	public static UserInfo mUserInfo;

	static String db_path = "";



	// Common var
	public static String __app_name = "";
	public static String __app_region_name = "";
	// 平台模式
	public static String __platform = "pub";

	// public static BitmapUtils mImageLoader;
	public static final String SYNC_VER = "APP.1.02";

	
	// Server server;
	// WebAppContext context;

	static App mApp;




	
	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this; // 获得App实例
		appContext = this;// 初始化ApplicationContext

		

		// 创建http通讯对象实例
		mHttpClient = createHttpClient();

	
	}

	

	/**
	 * service timer
	 */
	
	public static App get() {
		return mApp;
	}

	/**
	 * 测试网络是否异常
	 * 
	 * @author Lee
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String checkRouter(String url) throws ClientProtocolException,
			IOException {
		HttpClient httpClient = App.getHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(get);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(httpResponse.getEntity(),
					"utf-8");
			return result;
		}
		return null;
	}

	
	

	

	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) appContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	// public int getNetType() {
	// ConnectivityManager cm = (ConnectivityManager)
	// appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
	// NetworkInfo ni = cm.getActiveNetworkInfo();
	// if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
	// return NetType.WIFI_CONNECTED;
	// } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
	// return NetType.MOBILE_CONNECTED;
	// }
	// return NetType.NONE_CONNECTED;
	// }

	/**
	 * 获取App安装包信息
	 * 
	 * @return
	 */
	public static PackageInfo getPackageInfo() {
		PackageInfo info = null;
		try {
			info = appContext.getPackageManager().getPackageInfo(
					appContext.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
		if (info == null)
			info = new PackageInfo();
		return info;
	}

	public boolean hasFlashLight() {
		boolean hasFeature = false;
		FeatureInfo[] features = getPackageManager()
				.getSystemAvailableFeatures();
		for (FeatureInfo f : features) {
			if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) {
				hasFeature = true;
			}
		}
		return hasFeature;
	}

	// 获取手机序列号
	public String getIMEI() {
		TelephonyManager tm = (TelephonyManager) appContext
				.getSystemService(TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	// 初始化sqlite数据

	// 数据库文件 从assets文件夹 移动到SD卡
	public static File getAssetFileToDBDir(Context context, String fileName) {
		try {
			File f = new File(db_path + "/" + fileName);

			if (!f.exists()) {
				f.createNewFile();
			} else {
				if (f.delete()) {
					App.showLog("delete " + f.getName() + " ok....");
				} else {
					App.showLog("delete " + f.getName() + " error....");
				}
			}
			String databaseDir = f.getAbsolutePath();
			App.showLog(databaseDir);
			InputStream is = context.getAssets().open(fileName);
			File file = new File(databaseDir);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];

			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String loadAssetsString(Context c, String resPath) {
		InputStream isread = null;
		byte[] luaByte = new byte[1];
		try { // 就是这里了，我们把lua 都放到asset目录下，这样系统就 //不会找不到文件路径了，哼~

			isread = c.getAssets().open(resPath);
			int len = isread.available();
			luaByte = new byte[len];
			isread.read(luaByte);
		} catch (IOException e1) {

		} finally {
			if (isread != null) {
				try {
					isread.close();
				} catch (IOException e) {
				}
			}
		}
		return EncodingUtils.getString(luaByte, "UTF-8");
	}

	// MD5加密，32位
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static String doException(String errorCode) {

		return "";
	}

	public static void showLog(String tag, String msg) {
		if (DEBUG) {
			Log.d("--TAG--" + tag, msg);
		}
	}

	public static void showLog(String msg) {
		if (DEBUG) {
			Log.e("sdydbj", msg);
		}
	}

	public static void showToast(String msg) {
		if (DEBUG) {
			if (mToast == null) {
				mToast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
			} else {
				mToast.setText(msg);
				mToast.setDuration(Toast.LENGTH_SHORT);
			}
			mToast.show();
		}
	}

	// 检查apk是否安装
	public static boolean appIsInstalled(Context context, String packageName) {
		try {
			context.getPackageManager().getPackageInfo(packageName, 0);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	

	// 创建目录
	public static String createDir(String dirStr) {
		File dir = new File(dirStr);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir.getPath();
	}

	

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		this.shutdownHttpClient();
	
		
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		// this.shutdownHttpClient();
	}

	/**
	 * 创建HttpClient实例
	 * 
	 * @return
	 */
	private HttpClient createHttpClient() {

		HttpParams params = new BasicHttpParams();
		// 设置基本参数
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		// 超时设置
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(params, 6000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(params, 30000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(params, 6000);
		// 设置HttpClient支持HTTp和HTTPS两种模式
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schReg.register(new Scheme("https",
				SSLSocketFactory.getSocketFactory(), 443));
		// 使用线程安全的连接管理来创建HttpClient
		ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
				params, schReg);
		HttpClient client = new DefaultHttpClient(conMgr, params);
		return client;
	}

	private void shutdownHttpClient() {
		if (mHttpClient != null && mHttpClient.getConnectionManager() != null) {
			mHttpClient.getConnectionManager().shutdown();
		}
	}

	public synchronized static HttpClient getHttpClient() {
		return mHttpClient;
	}

	

	
	public static UserInfo getUserInfo() {
		return mUserInfo;
	}

	public static void setUserInfo(UserInfo userInfo) {
		mUserInfo = userInfo;
	}

	/**
	 * 获取时间
	 */
	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}
}
