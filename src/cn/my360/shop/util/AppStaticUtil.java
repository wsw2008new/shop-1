package cn.my360.shop.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;



/**
 * 无需传入Context的工具类
 * 
 */
public class AppStaticUtil {
	/**
	 * 去除字符串前后的双引号
	 */
	public static String parseString(String id) {
		try {
			int index = id.indexOf("\"");
			return id.substring(index + 1, id.lastIndexOf("\""));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否有网络
	 */
	public static boolean isNetwork(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 判断是否为数字
	 */
	public static boolean isNumeric(String str) {
		if (str == null || str.isEmpty())
			return false;
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否为空
	 */
	public static boolean isBlank(String src) {
		if (src == null)
			return true;
		src = src.trim();
		if (src == null)
			return true;
		if (src.equals(""))
			return true;
		return false;
	}

	/**
	 * 固定ListView的高度
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += (listItem.getMeasuredHeight() + 4);
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		params.height += 10;
		listView.setLayoutParams(params);
	}

	/**
	 * 解析月份，如果月份小于10，前面加0
	 */
	public static String parseMonth(int month) {
		if ((month + 1) < 10) {
			return "0" + (month + 1);
		} else {
			return String.valueOf((month + 1));
		}
	}

	/**
	 * 解析日期，如果日期小于10，前面加0
	 */
	public static String parseDayOfMonth(int day) {
		if (day < 10) {
			return "0" + day;
		} else {
			return String.valueOf(day);
		}
	}

	/**
	 * 传送公共参数用户名、密码及随机码
	 */
	public static JSONObject parm(JSONObject obj){
		
		return obj;
	}

	/**
	 * 获得一个UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
		
		//return (s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)+ s.substring(19, 23) + s.substring(24)).toUpperCase();
		return s;
	}

	/**
	 * 总页数
	 */
//	public static String getPages(String total) {
//		int pages = 1;
//		if (isBlank(total))
//			return "1";
//		pages = Integer.valueOf(total) % Integer.valueOf(Constant.PAGE_SIZE) == 0 ? Integer
//				.valueOf(total) / Integer.valueOf(Constant.PAGE_SIZE)
//				: Integer.valueOf(total) / Integer.valueOf(Constant.PAGE_SIZE)
//						+ 1;
//		return String.valueOf(pages);
//	}

	/**
	 * 验证号码 手机号 固话均可
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		boolean isValid = false;
		String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
}
