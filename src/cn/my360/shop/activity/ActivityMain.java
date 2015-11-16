package cn.my360.shop.activity;

import java.io.File;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import cn.my360.shop.R;
import cn.my360.shop.entity.Version;
import cn.my360.shop.fragment.MyFragment;
import cn.my360.shop.fragment.ProductFragment;
import cn.my360.shop.util.App;
import cn.my360.shop.util.AppManager;
import cn.my360.shop.util.BaseHandler;
import cn.my360.shop.util.FileUtil;
import cn.my360.shop.util.UrlEntry;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.umeng.update.UmengUpdateAgent;

/**
 * @version 1.0
 * @since 1.0
 */
public class ActivityMain extends BaseActivity implements
		OnCheckedChangeListener {
	public SharedPreferences app_config;
	ProductFragment mProductFragment;
	MyFragment mMyFragment;
	private static final String PRODUCT = "home";
	private static final String MY = "my";
	FragmentManager fm;
	FragmentTransaction ft;
	RadioGroup radio;
	Dialog dialog;
	RadioButton radiobtn0, radiobtn1, radiobtn2;
	private Handler mHandler;
	private static String newUrl;
	private static String apkName;
	public final static String UPDATE_VERSIONS_URL = "";
	private ImageView loadingImg;
	private String target;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		AppManager.getAppManager().addActivity(ActivityMain.this);
		initHandler();
		// Api.get().sendPost(UrlEntry.CHECK_VERSION_URL, new RequestParams(),
		// mHandler, 2);
		fm = getSupportFragmentManager();
		radio = (RadioGroup) findViewById(R.id.radioGroup1);
		radio.setOnCheckedChangeListener(this);
		// app_config =
		App.userID = sp.getString("id", "");
		App.username = sp.getString("username", "");
		App.uuid = sp.getString("loginkey", "");
		App.userphone = sp.getString("phone", "");
		App.userphoto = sp.getString("PHOTO", "");
		App.usertype = sp.getString("TYPE", "");
		App.authentication = sp.getString("authentication", "");
		ft = fm.beginTransaction();
		replace(ProductFragment.newInstance());
		// isTabHome();
		// ft.commit();
	}

	public void replace(Fragment fragment) {
		ft = fm.beginTransaction();
		ft.replace(R.id.realtabcontent, fragment);
		ft.setCustomAnimations(android.R.animator.fade_in,
				android.R.animator.fade_out);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();//
	}

	private void isTabHome() {
		if (mProductFragment == null) {
			ft.add(R.id.realtabcontent, new ProductFragment(), PRODUCT);
		} else {
			ft.attach(mProductFragment);
		}
	}

	// private void isTabScan() {
	//
	// if (mNewsFragment == null) {
	// ft.add(R.id.realtabcontent, new MyDealFragment(), NEWS);
	// } else {
	// ft.attach(mNewsFragment);
	// }
	//
	// }
	//
	// private void isTabNews() {
	// if (mMessageFragment == null) {
	// ft.add(R.id.realtabcontent, new MyMsgFragment(), MESSAGE);
	// } else {
	// ft.attach(mMessageFragment);
	// }
	// }

	private void isTabMore() {
		if (mMyFragment == null) {
			ft.add(R.id.realtabcontent, new MyFragment(), MY);
		} else {
			ft.attach(mMyFragment);
		}
	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// return mExitHelper.onKeyDown(keyCode, event);
	// }

	private void alert(String msg) {
		dialog = new AlertDialog.Builder(this).setTitle("提示").setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create();
		dialog.show();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// if (checkedId == R.id.radio1) {
		// if (isLogin(getApplicationContext())) {
		// showToast("请先登录！");
		// checkedId = R.id.radio0;
		// group.check(checkedId);
		// }
		// }
		//mProductFragment = (ProductFragment) fm.findFragmentByTag(PRODUCT);
		// mNewsFragment = (MyDealFragment) fm.findFragmentByTag(NEWS);
		// mMessageFragment = (MyMsgFragment) fm.findFragmentByTag(MESSAGE);
		//mMyFragment = (MyFragment) fm.findFragmentByTag(MY);

		//ft = fm.beginTransaction();
		// mMessageFragment.webView = null;
		//if (mProductFragment != null)
		//	ft.detach(mProductFragment);
		// if (mNewsFragment != null)
		// ft.detach(mNewsFragment);
		// if (mMessageFragment != null)
		// ft.detach(mMessageFragment);
		// if (mMyFragment != null)
		// ft.detach(mMyFragment);

		switch (checkedId) {
		case R.id.radio0:
			//isTabHome();
			replace(ProductFragment.newInstance());

			break;
		case R.id.radio1:

			// isTabScan();

			break;
		case R.id.radio2:
			// isTabNews();
			break;
		case R.id.radio3:
			//isTabMore();
			replace(MyFragment.newInstance());

			break;
		}
		//ft.commit();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initHandler() {
		mHandler = new BaseHandler(this) {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					// loginSuccess(msg.obj.toString());
					break;
				case 2:
					System.out.println(msg.obj.toString());
					getUpdate(msg.obj.toString());
					break;
				default:
					break;
				}
			}

		};

	}

	// 检查更新
	private void getUpdate(String result) {

		Version v = Version.parse(result);

		int CurrVersion = App.getPackageInfo().versionCode;
		if (Integer.parseInt(v.versionCode) > CurrVersion) {
			// if (2 > 1) {
			newUrl = UrlEntry.ip + "/" + v.url;
			apkName = "szjy_shop.apk";

			target = FileUtil.SDPATH + apkName;
			System.out.println(target);
			String desc = v.content;
			AlertDialog.Builder builder = new Builder(this);
			builder.setTitle("有新版本：" + "v" + v.versionName);
			builder.setMessage("更新介绍：\r\n" + desc);
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					if (new File(target).exists()) {
						FileUtil.DeleteFile(new File(target));
					}
					// installApk(target.replace(".temp", ".apk"));

					showDownloadDialog();

				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// finish();
				}
			});
			builder.setOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface arg0) {
					// finish();
				}
			});
			builder.create().show();
		} else {

		}
	}

	ProgressBar mProgress;
	TextView mProgressText;
	Dialog downloadDialog;
	HttpHandler handler;

	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("正在下载新版本");

		final LayoutInflater inflater = LayoutInflater.from(this);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// finish();
				handler.cancel();
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				// finish();
				handler.cancel();
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();

		downloadApk(newUrl, target);
	}

	public void installApk(String apkFilePath) {
		File apkfile = new File(apkFilePath);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		startActivity(i);
		finish();
	}

	private void downloadApk(String url, final String target) {
		HttpUtils http = new HttpUtils();
		handler = http.download(url, target, true, true,
				new RequestCallBack<File>() {

					@Override
					public void onStart() {

						super.onStart();
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						mProgressText.setText(current + "/" + total);
						mProgress.setMax(Integer.parseInt(String.valueOf(total)));
						mProgress.setProgress(Integer.parseInt(String
								.valueOf(current)));
					}

					@Override
					public void onCancelled() {
						super.onCancelled();
					}

					@Override
					public void onSuccess(ResponseInfo<File> arg0) {
						arg0.result.renameTo(new File(target.replace(".temp",
								".apk")));
						installApk(target.replace(".temp", ".apk"));
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						showToast("下载失败");
					}
				});
	}

	/**
	 * 再按一次退出系統
	 * */
	private long exitTime = 0;

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// if (mMessageFragment.webView != null) {
			// if (mMessageFragment.webView.canGoBack()) {
			// mMessageFragment.webView.goBack();
			// return true;
			// }
			//
			// }
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), R.string.again_exit,
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
