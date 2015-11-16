package cn.my360.shop.activity;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import cn.my360.shop.util.App;
import cn.my360.shop.util.AppManager;
import cn.my360.shop.util.Constant;
import cn.my360.shop.weight.LoadingDialog;

public class BaseActivity extends FragmentActivity {

	// public static final String PRINTER_FILE_NAME = App.PRINTER_FILE_NAME;

	public int FROM_FORM = 22;
	public int FROM_LIST = 33;
	private LoadingDialog dialog = null;
	public Toast mToast = null;
	public Editor editor;
	public SharedPreferences sp;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		AppManager.getAppManager().addActivity(this);
		
		sp = getSharedPreferences(App.USERINFO, MODE_PRIVATE);
		editor = sp.edit();
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

	}

	//
	public void showLog(String tag, String msg) {
		if (Constant.IS_DEBUG)
			Log.i(tag, msg);
	}

	//
	public void showToast(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(getApplicationContext(), msg,
					Toast.LENGTH_SHORT);
		} else {

			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public void loadFail() {
		showToast("加载失败");
	}

	// 环形加载进度条
	public void showDialog() {
		dialog = new LoadingDialog(this);
		dialog.show();
	}

	public void showDialog(String msg) {
		dialog = new LoadingDialog(this, msg);
		dialog.show();
	}

	public void disDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	//
	public void back(View v) {
		onBackPressed();
	}

	public void logout() {
		editor.clear();
		editor.commit();
//		 AppManager.getAppManager().AppExit(getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}

	
	

	

	public void dialog(String message, final String phone) {

		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(message);
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ phone));
				startActivity(intent);

			}
		});
		builder.setNegativeButton("取消", null);
		builder.create().show();

	}
	 /**
	  * 隐藏输入框
	  */
	public void hideInput(){
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken()
		,InputMethodManager.HIDE_NOT_ALWAYS);

	}
}
