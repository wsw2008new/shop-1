package cn.my360.shop.activity;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.my360.shop.R;
import cn.my360.shop.util.App;
import cn.my360.shop.util.FileUtil;
import cn.my360.shop.util.HeadUtil;
import cn.my360.shop.util.MD5;
import cn.my360.shop.util.UrlEntry;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class UserInfoActivity extends BaseActivity {

	private SharedPreferences sp;
	private TextView tv_phone;
	private String newphone;
	private RelativeLayout layout_pwd;
	private ImageView ivPhoto;
	private BitmapUtils bitmapUtils;
	private RelativeLayout btn_exit_info;
	private static final int CHOOSE_PICTURE_IMAGE_FIRST = 11;
	/** 拍照获取相片 **/
	private static final int TAKE_PICTURE = 0x000001;
	private String imgpath;
	private File mCurPhotoFile;
	private String picPath = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		sp = getSharedPreferences(App.USERINFO, MODE_PRIVATE);
		((TextView) findViewById(R.id.def_head_tv)).setText("个人资料");
		((TextView) findViewById(R.id.tv_username)).setText(sp.getString(
				"username", "").toString());
		ImageButton backbtn = (ImageButton) findViewById(R.id.def_head_back);
		backbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setResult(22);
				onBackPressed();
			}
		});
		bitmapUtils = new BitmapUtils(this);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		tv_phone.setText(sp.getString("phone", ""));
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
		if(sp.getString("PHOTO", "").toString().equals("")){
			ivPhoto.setImageResource(R.drawable.def);
		}else{
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.def);
			bitmapUtils.display(ivPhoto,
					UrlEntry.ip + sp.getString("PHOTO", "").toString());// 下载并显示图片
		}
		
		ivPhoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				ShowPickDialog();
				
				
				
			}
		});
		
		tv_phone.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final EditText et = new EditText(UserInfoActivity.this);
				new AlertDialog.Builder(UserInfoActivity.this)
						.setTitle("请输入要修改的电话号码")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setView(et)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										newphone = et.getText().toString()
												.trim();
										sendData("phone",newphone,"");
									}
								}).setNegativeButton("取消", null).show();
			}
		});
		layout_pwd = (RelativeLayout) findViewById(R.id.layout_pwd);

		layout_pwd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
//				Intent intent = new Intent(UserInfoActivity.this,UpdatePwdActivity.class);
//				
//				startActivity(intent);
				
//				MyDialog dialog = new MyDialog(UserInfoActivity.this, "修改密码",
//						new MyDialog.OnDialogListener() {
//							
//
//							@Override
//							public void back(String oldpwd, String newpwd) {
//								// TODO Auto-generated method stub
//								System.out.println(oldpwd+"   "+newpwd);
//								sendData("pwd",oldpwd,newpwd);
//							}
//						});
//
//				dialog.show();

			}
		});

	}

	private void sendData(final String type, String str,String str1) {
		showDialog();
		RequestParams params = new RequestParams();
		if (type.equals("phone")) {
			params.addBodyParameter("e.phone", str);
		} else {
			params.addBodyParameter("e.password", (new MD5()).GetMD5Code(str));
			params.addBodyParameter("newPassword", (new MD5()).GetMD5Code(str1));
		}
		params.addBodyParameter("uuid", App.uuid);

		final HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, UrlEntry.UPDATE_USERINFO_URL,
				params, new RequestCallBack<String>() {
					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						disDialog();

						Log.i("login", " login responseInfo.result = "
								+ responseInfo.result);

						JSONObject jsonresult;
						try {
							jsonresult = new JSONObject(responseInfo.result);
							if (jsonresult.get("success").toString()
									.equals("1")) {
								showToast("修改成功！");
                              if(type.equals("phone")){
								tv_phone.setText(newphone);
								App.userphone = newphone;
								editor.putString("phone", newphone);
								editor.commit();
                              }
							} else {
//								successType(jsonresult.get("success").toString(), "修改失败！");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						showToast("修改失败,请稍后再试！");
						disDialog();

					}
				});
	}

	// 退出当前账户
	public void loginexit(View view) {
		editor.clear();
		editor.commit();
		App.uuid = "";
		App.username = "";
		App.userphone = "";
		App.userphoto = "";
		App.usertype = "" ;
		App.userID = "";
		
		setResult(22);
		finish();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch (requestCode) {
//		case 1 ://相册
//			startPhotoZoom(data.getData());
//			break;
//		case 2 : //相机
//			File temp = new File(Environment.getExternalStorageDirectory()
//					+ "/head.jpg");
//			startPhotoZoom(Uri.fromFile(temp));
//			break;
//		case 3://裁剪
//			if(data != null){
//				setPicToView(data);
//				upHeadImage();
//			}
//			break;
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case CHOOSE_PICTURE_IMAGE_FIRST:
				HeadUtil.setViewByRequest(data, this, ivPhoto);
				upHeadImage();

				break;
			case TAKE_PICTURE:
				Bitmap ivBitmap1 = HeadUtil.readBitmapAutoSize(
						mCurPhotoFile.getPath(), 200, 200);
				if (ivBitmap1 == null) {
					return;
				}
				HeadUtil.setImage(ivPhoto, ivBitmap1);

				upHeadImage();
				break;
			
			default:

				break;

			}

		}
//		
//		default:
//			break;
//		}

		super.onActivityResult(requestCode, resultCode, data);
	}
	
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
	/**
	 * 裁剪图片方法实现
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		//下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 3);
	}
	/**
	 * 选择提示对话框
	 */
	private void ShowPickDialog() {
		new AlertDialog.Builder(this)
				.setTitle("请选择")
				.setNegativeButton("相册", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						
						Intent openAlbumIntent = new Intent(
		Intent.ACTION_GET_CONTENT);
openAlbumIntent.setType("image/*");
startActivityForResult(openAlbumIntent,
		CHOOSE_PICTURE_IMAGE_FIRST);

					}
				})
				.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					
						doTakePhoto();
					}
				}).show();
		
	}

	/**
	 * 保存裁剪之后的图片数据
	 * @param picdata
	 */
	
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			ivPhoto.setImageBitmap(photo);
			
			FileUtil.saveBitmap(photo, "head");
			
			
			imgpath = FileUtil.SDPATH+"head.JPEG";
			
			
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
				SharedPreferences mSharedPreferences =
						getSharedPreferences(App.USERINFO, 1);
				Editor edit = mSharedPreferences.edit();
				edit.putString("PHOTO", jobj.getString("picture"));
				edit.commit();
				App.userphoto = jobj.getString("picture");

			} else {
				Toast.makeText(UserInfoActivity.this, "上传失败！", 1).show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
