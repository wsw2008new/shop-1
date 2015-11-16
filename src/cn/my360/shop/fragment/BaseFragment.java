package cn.my360.shop.fragment;




import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import cn.my360.shop.activity.LoginActivity;
import cn.my360.shop.util.App;
import cn.my360.shop.util.Constant;
import cn.my360.shop.weight.LoadingDialog;

public class BaseFragment extends Fragment {
	public Toast mToast = null;
	private LoadingDialog dialog=null;
	public SharedPreferences.Editor editor;
	public SharedPreferences sp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		sp = getActivity().getSharedPreferences(Constant.spName, Activity.MODE_PRIVATE);
//		editor = sp.edit();
		if (VERSION.SDK_INT >= VERSION_CODES.KITKAT) {
			getActivity().getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getActivity().getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
	
	//
	public void showLog(String tag,String msg){
		if (Constant.IS_DEBUG)
			Log.e(tag,msg);
	}
	
	//
	public void showToast(String msg) {
		if(getActivity()==null){return;}
		if (mToast == null) {
			mToast = Toast.makeText(getActivity(), msg,Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	public void loadFail(){
		showToast("加载失败");
	}
	// 环形加载进度条
	public void showDialog() {
		dialog = new LoadingDialog(getActivity());
		dialog.show();
	}
	public void disDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	public void relogin(Context context) {
		editor.putString("loginkey", "");
		editor.commit();
		App.uuid = "";
		Intent intent = new Intent(context, LoginActivity.class);
		startActivity(intent);

	}

	public void successType(String successtype, String msg) {
		if (successtype.equals("0")) {
			showToast("用户信息错误,请重新登录！");
			relogin(getActivity());
		} else if (successtype.equals("3")) {
			showToast(msg);
		}
	}
	public void back(View v){
		getActivity().onBackPressed();
		onDestroyView();
	}
	public boolean isLogin(Context context)
	{
		boolean falg = false;
		SharedPreferences sp = context.getSharedPreferences("USERINFO", 1);
		if(TextUtils.isEmpty(sp.getString("loginkey", "")))
		{
			falg = true;
			Intent intent =new Intent(context,LoginActivity.class);
			startActivityForResult(intent, 22);
		}else
		{
			App.userID = sp.getString("id", "");
			App.username = sp.getString("username", "");
			App.uuid = sp.getString("loginkey", "");
			App.userphone = sp.getString("phone", "");
			App.userphoto = sp.getString("PHOTO", "");
			App.usertype = sp.getString("TYPE", "");
		}
		return falg;
	}

	

	
}
