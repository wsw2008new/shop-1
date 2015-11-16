package cn.my360.shop.fragment;

import java.io.File;
import java.io.IOException;
import org.json.JSONObject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.my360.shop.R;
import cn.my360.shop.activity.LoginActivity;
import cn.my360.shop.activity.MyOrderActivity;
import cn.my360.shop.activity.UserInfoActivity;
import cn.my360.shop.util.App;
import cn.my360.shop.util.FileUtil;
import cn.my360.shop.util.HeadUtil;
import cn.my360.shop.util.UrlEntry;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * @version 1.0
 * @since 1.0
 */
public class MyFragment extends BaseFragment implements OnClickListener,
		Callback {

	private TextView tv_username;
	private ImageView img_head;
	private RelativeLayout userinfo_layout;
	private RelativeLayout vehicle_layout, myorder_layout, mywuliu_layout,
			set_layout, shiminglayout, erweimalayout;
	private Button btn_exit;
	private static final int CHOOSE_PICTURE_IMAGE_FIRST = 11;
	private static final int CAMERAL_PICTURE_IMAGE_FIRST = 01;
	private static final int REFRESH = 24;
	private static final int REFASH_PHOTO = 23;
	private BitmapUtils bitmapUtils;
	private File mCurPhotoFile;
	private String picPath = "";
	private View v;
	private SharedPreferences sp;
	private final static byte LOGIN_RESULT = 0x0a;

	// private String text = "来自猪贸通的分享：";
	// private String imageurl = UrlEntry.ip+"/appDownload/icon.png";
	// private String title = "神州牧易";
	// private String url = UrlEntry.ip+"/appDownload/download.jsp";
	// private String content = "我正在使用猪贸通,可以随时随地查看生猪收购出售信息..";
	// public static final String SHARE_APP_KEY = "a9cafa2614f8";
	// private Button shareButton;

	// private SharePopupWindow share;
	/**
	 * 单例
	 * 
	 * @return
	 */
	public static MyFragment newInstance() {
		MyFragment fragment = new MyFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_set, container, false);
		v.findViewById(R.id.def_head_back).setVisibility(View.GONE);
		((TextView) v.findViewById(R.id.def_head_tv)).setText("个人中心");
		ImageButton share_btn = (ImageButton) v.findViewById(R.id.add_btn);
		share_btn.setVisibility(View.VISIBLE);
		share_btn.setImageResource(R.drawable.share_img);
		share_btn.setOnClickListener(this);
		bitmapUtils = new BitmapUtils(getActivity());
		this.v = v;
		initView(v);

		return v;
	}

	public void initView(View v) {
		userinfo_layout = (RelativeLayout) v.findViewById(R.id.userinfo_layout);
		img_head = (ImageView) v.findViewById(R.id.head_img);
		tv_username = (TextView) v.findViewById(R.id.username);
		btn_exit = (Button) v.findViewById(R.id.btn_exit);
		vehicle_layout = (RelativeLayout) v.findViewById(R.id.vehicle_layout);
		myorder_layout = (RelativeLayout) v.findViewById(R.id.myorder_layout);
		mywuliu_layout = (RelativeLayout) v.findViewById(R.id.mywuliu_layout);
		set_layout = (RelativeLayout) v.findViewById(R.id.set_layout);
		shiminglayout = (RelativeLayout) v.findViewById(R.id.shiminglayout);
		erweimalayout = (RelativeLayout) v.findViewById(R.id.erweimalayout);
		userinfo_layout.setOnClickListener(this);
		myorder_layout.setOnClickListener(this);
		mywuliu_layout.setOnClickListener(this);
		set_layout.setOnClickListener(this);
		shiminglayout.setOnClickListener(this);
		erweimalayout.setOnClickListener(this);
		setViewDate();
	}

	public void setViewDate() {
		sp = getActivity().getSharedPreferences("USERINFO", 1);
		if (!TextUtils.isEmpty(sp.getString("loginkey", ""))) {
			img_head.setOnClickListener(this);
			tv_username.setText(sp.getString("username", "").toString());
			if (sp.getString("PHOTO", "").toString().equals("")) {
				img_head.setImageResource(R.drawable.def);
			} else
				bitmapUtils.configDefaultLoadFailedImage(R.drawable.def);
			bitmapUtils.display(img_head,
					UrlEntry.ip + sp.getString("PHOTO", "").toString());// 下载并显示图片
		} else {
			tv_username.setText("请先登录");
			img_head.setImageResource(R.drawable.def);
		}
		btn_exit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.exit(0);

			}
		});
		// vehicle_layout.setVisibility(View.GONE);
		vehicle_layout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Intent intent = new Intent(getActivity(),
				// VehicleActivity.class);
				// startActivity(intent);

			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.userinfo_layout:
			if (isLogin(getActivity())) {
				showToast("请先登录！");
				// startActivityForResult(new Intent(getActivity(),
				// LoginActivity.class), 22);
			} else {
				Intent intent = new Intent(getActivity(),
						UserInfoActivity.class);
				startActivityForResult(intent, REFASH_PHOTO);
			}
			break;
		case R.id.head_img:
			// final CharSequence[] items = { "相册", "拍照" };
			// AlertDialog dlg = new AlertDialog.Builder(getActivity())
			// .setTitle("选择图片")
			// .setItems(items, new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface dialog, int item) {
			// if (item == 1) {
			// try {
			// doTakePhoto();
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// } else {
			// Intent openAlbumIntent = new Intent(
			// Intent.ACTION_GET_CONTENT);
			// openAlbumIntent.setType("image/*");
			// startActivityForResult(openAlbumIntent,
			// CHOOSE_PICTURE_IMAGE_FIRST);
			// }
			// }
			// }).create();
			// dlg.setCanceledOnTouchOutside(true);
			// dlg.show();
			break;
		case R.id.myorder_layout:
			if (isLogin(getActivity())) {
				showToast("请先登录！");
				startActivityForResult(new Intent(getActivity(),
						LoginActivity.class), 22);
			} else {
				Intent order = new Intent(getActivity(), MyOrderActivity.class);
				startActivity(order);
			}
			break;
		case R.id.mywuliu_layout:
			// if (isLogin(getActivity())) {
			// showToast("请先登录！");
			//
			// } else {
			// Intent v = new Intent(getActivity(), MyVehicleActivity.class);
			// startActivity(v);
			// }
			break;
		case R.id.shiminglayout:// 1为猪场,2为屠宰场,3为经纪人4物流公司
			// if (isLogin(getActivity())) {
			// showToast("请先登录！");
			//
			// } else {
			// if (App.usertype.equals("1")) { // 猪场
			//
			// Intent zcintent = new Intent(getActivity(),
			// ZhuChangRenZhengActivity.class);
			// startActivity(zcintent);
			// }else if(App.usertype.equals("2")){//屠宰场
			// Intent zcintent = new Intent(getActivity(),
			// SlaughterhouseRenZhengActivity.class);
			// startActivity(zcintent);
			// } else if (App.usertype.equals("3")) {//经纪人
			// Intent zcintent = new Intent(getActivity(),
			// AgentRenZhengActivity.class);
			// startActivity(zcintent);
			// }else {
			// showToast("敬请期待！");
			// }
			// }

			break;
		case R.id.set_layout:
			// Intent setintent = new Intent(getActivity(), SetActivity.class);
			// startActivity(setintent);
			break;
		case R.id.add_btn:
			// if (!TextUtils.isEmpty(App.uuid)) {
			// setShare();
			// }else{
			// showToast("请先登录！");
			// }
			break;

		case R.id.erweimalayout:
			// if (!TextUtils.isEmpty(App.uuid)) {
			// setShare();
			// }else{
			// showToast("请先登录！");
			// }

			break;
		}

	}

	// public void setShare() {
	// ShareSDK.initSDK(getActivity());
	// share = new SharePopupWindow(getActivity());
	// share.setPlatformActionListener(this);
	// ShareModel model = new ShareModel();
	// model.setImageUrl(imageurl);
	// model.setText(text);
	// model.setTitle(title);
	// model.setUrl(url+"?u="+App.userID);
	// model.setContent(content);
	//
	// share.initShareParams(model);
	// share.showShareWindow();
	// share.setBackgroundDrawable(new
	// BitmapDrawable());//设置PopupWindow的背景为一个空的Drawable对象，如果不设置这个，那么PopupWindow弹出后就无法退出了
	// share.setOutsideTouchable(true);//设置是否点击PopupWindow外退出PopupWindow
	// WindowManager.LayoutParams params =
	// getActivity().getWindow().getAttributes();//创建当前界面的一个参数对象
	// params.alpha =
	// 0.8f;//设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面
	// getActivity().getWindow().setAttributes(params);//把该参数对象设置进当前界面中
	// share.setOnDismissListener(new OnDismissListener() {//设置PopupWindow退出监听器
	// @Override
	// public void onDismiss()
	// {//如果PopupWindow消失了，即退出了，那么触发该事件，然后把当前界面的透明度设置为不透明
	// WindowManager.LayoutParams params =
	// getActivity().getWindow().getAttributes();
	// params.alpha = 1.0f;//设置为不透明，即恢复原来的界面
	// getActivity().getWindow().setAttributes(params);
	// }
	// });
	// // 显示窗口 (设置layout在PopupWindow中显示的位置)
	// share.showAtLocation(
	// getActivity().findViewById(R.id.main),
	// Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
	//
	// }

	@Override
	public void onResume() {
		super.onResume();

		// if (share != null) {
		// share.dismiss();
		// }
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// ShareSDK.stopSDK(getActivity());
	}

	/** 拍照获取相片 **/
	private static final int TAKE_PICTURE = 0x000001;

	private void doTakePhoto() {

		// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //
		// //调用系统相机
		// Uri imageUri = Uri.fromFile(new File(FileUtil.IMG_PATH+"/",
		// "head.jpg"));
		// // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		// // 直接使用，没有缩小
		// startActivityForResult(intent, requestCode); // 用户点击了从相机获取
		if (!(Environment.getExternalStorageState().equals("mounted")))
			;
		File localFile = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath().toString()
				+ "/DCIM/Camera/szjy");

		if (!(localFile.exists())) {
			localFile.mkdirs();
		}
		this.mCurPhotoFile = new File(FileUtil.IMG_PATH + "/", "head.jpg");
		try {
			mCurPhotoFile.createNewFile();
			Intent localIntent = new Intent(
					"android.media.action.IMAGE_CAPTURE");
			System.out.println(mCurPhotoFile.getPath());
			picPath = mCurPhotoFile.getPath();
			localIntent.putExtra("output", Uri.fromFile(mCurPhotoFile));
			startActivityForResult(localIntent, TAKE_PICTURE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("car", "  resultCode = " + resultCode);
		if (resultCode == getActivity().RESULT_OK) {
			switch (requestCode) {
			case CHOOSE_PICTURE_IMAGE_FIRST:
				HeadUtil.setViewByRequest(data, getActivity(), img_head);
				upHeadImage();
				break;
			case TAKE_PICTURE:
				Bitmap ivBitmap1 = HeadUtil.readBitmapAutoSize(
						mCurPhotoFile.getPath(), 200, 200);
				if (ivBitmap1 == null) {
					return;
				}
				HeadUtil.setImage(img_head, ivBitmap1);

				upHeadImage();
				break;

			default:

				break;

			}

		}
		if (resultCode == REFASH_PHOTO) {

			System.out.println("刷新当前页面" + App.uuid);
			if (!TextUtils.isEmpty(App.uuid)) {
				img_head.setOnClickListener(this);
				System.out.println(App.username + "姓名");
				tv_username.setText(App.username);
				if (TextUtils.isEmpty(App.userphoto)) {
					img_head.setImageResource(R.drawable.def);
				} else {
					bitmapUtils.display(img_head, UrlEntry.ip + App.userphoto);// 下载并显示图片
					bitmapUtils.configDefaultLoadFailedImage(R.drawable.def);
				}
			} else {
				tv_username.setText("请先登录");
				img_head.setImageResource(R.drawable.def);
			}

		} else if (resultCode == 22) {
			setViewDate();
		}
	}

	public void upHeadImage() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("uuid", App.uuid);
		params.addBodyParameter("uploadImage", new File(FileUtil.IMG_PATH
				+ "/head.jpg"));
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, UrlEntry.UPLOAD_USERINFO_URL,
				params, new RequestCallBack<String>() {

					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// resultText.setText(current + "/" + total);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("aaaa", " head  responseInfo.result = "
								+ responseInfo.result);

						getDTOArrayUp(responseInfo.result);

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Log.i("aaaa", " head  msg = " + msg);
					}
				});
	}

	public void getDTOArrayUp(String jsonString) {
		try {
			JSONObject jobj = new JSONObject(jsonString);
			if (jobj != null && jobj.getString("success").equals("1")) {

				// 保存头像信息
				SharedPreferences mSharedPreferences = getActivity()
						.getSharedPreferences(App.USERINFO, 1);
				Editor edit = mSharedPreferences.edit();
				edit.putString("PHOTO", jobj.getString("picture"));
				edit.commit();
				App.userphoto = jobj.getString("picture");

			} else {
				Toast.makeText(getActivity(), "上传失败！", 1).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		int what = msg.what;
		if (what == 1) {
			Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
		}
		// if (share != null) {
		// share.dismiss();
		// }
		return false;
	}

	// @Override
	// public void onCancel(Platform arg0, int arg1) {
	// Message msg = new Message();
	// msg.what = 0;
	// UIHandler.sendMessage(msg, this);
	// }
	//
	// @Override
	// public void onComplete(Platform plat, int action,
	// HashMap<String, Object> res) {
	// Message msg = new Message();
	// msg.arg1 = 1;
	// msg.arg2 = action;
	// msg.obj = plat;
	// UIHandler.sendMessage(msg, this);
	//
	// }
	//
	// @Override
	// public void onError(Platform arg0, int arg1, Throwable arg2) {
	// Message msg = new Message();
	// msg.what = 1;
	// UIHandler.sendMessage(msg, this);
	//
	// }
	//

}
