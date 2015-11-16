package cn.my360.shop.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.my360.shop.R;
import cn.my360.shop.util.App;
import cn.my360.shop.util.AppManager;



/**
 * 启动Act
 * 
 */
public class StartActivity extends BaseActivity {
	private static String userName;
	private static String password;
	private Handler mHandler;
	private static String newUrl;
	private static String apkName;
	public final static String UPDATE_VERSIONS_URL = "";

	ImageView loadingImg;
	String target;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		App.appContext = StartActivity.this;
		
		load();
	}

	public void load() {
		final View view = View.inflate(this, R.layout.activity_start, null);
		setContentView(view);
		loadingImg=(ImageView)view.findViewById(R.id.imageView2);
		
		// 渐变大 展示启动屏
		Animation aa = AnimationUtils.loadAnimation(this,R.anim.entrance);
		aa.setDuration(2000);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				startActivity(new Intent(StartActivity.this, ActivityMain.class));
				StartActivity.this.finish();
				
				// 检查更新
//				Api.get().sendPost(UrlEntry.CHECK_VERSION_URL, new RequestParams(), mHandler, 2);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {

			}
		});
		loadingImg.startAnimation(aa);
	}

	//
	

	
	

//	private void loginSuccess(String result) {
//		try {
//			JSONObject o=new JSONObject(result);
//			if (o.getInt("result")==0) {
//				JSONObject p=o.getJSONObject("paras");
//				editor.putString("levelname", p.getString("LEVELSNAME"));
//				editor.putString("REALNAME", p.getString("REALNAME"));
//				editor.putString("WORKUNIT", p.getString("WORKUNIT"));
//				
//				editor.putString("username", p.getString("USERNAME"));
//				editor.putString("password", password);
//				editor.putString("msg", o.getString("msg"));
//				editor.putString("paras", p.toString());
//				editor.putString("result", o.getString("result"));
//				editor.putString("tokens", o.getString("tokens"));
//				editor.putString("version", App.SYNC_VER);//请求版本号
//				editor.commit();
//				
//				Constant.userName = userName;
//				Constant.MSG = o.getString("msg");
//				startActivity(new Intent(ActivityLead.this, ActivityMain.class));
//				Log.e("pwd", sp.getString("password",""));
//				finish();
//			} else {
//				loginFail();
//			}
//		} catch (Exception e) {
//			loginFail();
//		}
//	}

	//
//	private void loginFail() {
////		showToast("登录失败");
//		startActivity(new Intent(ActivityLead.this, ActivityGuide.class));
//		ActivityLead.this.finish();
//	}


	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		AppManager.getAppManager().AppExit(getApplicationContext());
	}
}
