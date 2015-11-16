package cn.my360.shop.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import cn.my360.shop.R;
import cn.my360.shop.entity.PicInfo;
import cn.my360.shop.entity.ProductEntry;
import cn.my360.shop.http.HttpUtil;
import cn.my360.shop.util.App;
import cn.my360.shop.util.HeadUtil;
import cn.my360.shop.util.UrlEntry;
import cn.my360.shop.util.ViewFactory;
import cn.my360.shop.weight.CycleViewPager;
import cn.my360.shop.weight.LoadingDialog;
import cn.my360.shop.weight.CycleViewPager.ImageCycleViewListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.StringBody;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @author Administrator
 * 
 */
public class ProductInfoActivity extends Activity {
	private TextView mTvTitle;
	private ImageButton mIbBack;
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<PicInfo> infos = new ArrayList<PicInfo>();
	private CycleViewPager cycleViewPager;
	private String id = "";
	private LoadingDialog dialog = null;
	// private String[] imageUrls = {
	// "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
	// "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
	// "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
	// "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
	// "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg" };
	private String[] imageUrl;
	private TextView mTvProductName;
	private TextView mTvProductNowPrice;
	private TextView mTvProductOldPrice;
	private TextView mTvProductSellCount;
	private TextView mTvProductStock;
	private Button mBtnMinus;
	private Button mBtnAdd;
	private Button mOrderPay;
	private TextView mTvBuyCount;
	private TextView mTvTotalPrice;
	private int mBuyCount = 1;
	public Toast mToast = null;
	private float mNowPrice = 0f;
	private int mStockCount;
	private TextView mTvBusinessName;
	private LayoutParams mParams;
	private FrameLayout mFlImages;
	private RelativeLayout mRlDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		mTvTitle = ((TextView) findViewById(R.id.def_head_tv));
		mTvTitle.setText("商品详情");
		mIbBack = (ImageButton) findViewById(R.id.def_head_back);
		mIbBack.setOnClickListener(l);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		showDialog();
		new Thread(runnable).start();
		mTvProductName = (TextView) findViewById(R.id.tv_product_name);
		mTvProductNowPrice = (TextView) findViewById(R.id.tv_product_now_price);
		mTvProductOldPrice = (TextView) findViewById(R.id.tv_product_old_price);
		mTvProductSellCount = (TextView) findViewById(R.id.tv_product_sell_count);
		mTvProductStock = (TextView) findViewById(R.id.tv_product_stock);
		mBtnMinus = (Button) findViewById(R.id.btn_minus);
		mBtnMinus.setOnClickListener(l);
		mBtnAdd = (Button) findViewById(R.id.btn_add);
		mBtnAdd.setOnClickListener(l);
		mOrderPay = (Button) findViewById(R.id.order_btn);
		mOrderPay.setOnClickListener(l);
		mTvBuyCount = (TextView) findViewById(R.id.tv_buy_count);
		mTvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
		mTvBusinessName = (TextView) findViewById(R.id.tv_business_name);
		mFlImages = (FrameLayout) findViewById(R.id.fl_images);
		mParams = new LayoutParams(getSize(true), getSize(true));
		mFlImages.setLayoutParams(mParams);
		mRlDetails = (RelativeLayout) findViewById(R.id.rl_details);
		mRlDetails.setOnClickListener(l);
	}

	private OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.def_head_back:
				onBackPressed();
				break;
			case R.id.btn_minus:
				if (mBuyCount < 2) {
					showToast("购买数量最少为1");
					return;
				}
				mBuyCount--;
				mTvBuyCount.setText(String.valueOf(mBuyCount));
				mTvTotalPrice.setText(mBuyCount * mNowPrice + "");
				break;
			case R.id.btn_add:
				if (mBuyCount > mStockCount) {
					showToast("购买数量不能超出库存");
					return;
				}
				mBuyCount++;
				mTvBuyCount.setText(String.valueOf(mBuyCount));
				mTvTotalPrice.setText(mBuyCount * mNowPrice + "");
				break;
			case R.id.rl_details:
				Intent intent = new Intent(ProductInfoActivity.this,
						ActivityDetails.class);
				intent.putExtra("id", id);
				startActivity(intent);
				break;
			case R.id.order_btn:
				if (isLogin(ProductInfoActivity.this)) {
					showToast("请先登录！");
					//
				} else {
					showDialog();
					new Thread(payrunnable).start();
				}
				break;
			}
		}
	};

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

	@SuppressLint("NewApi")
	private void initialize() {
		cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.fragment_cycle_viewpager_content);
		for (int i = 0; i < imageUrl.length; i++) {
			PicInfo info = new PicInfo();
			info.setUrl(imageUrl[i]);
			info.setContent("图片-->" + i);
			infos.add(info);
		}

		// 将最后一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(infos.size() - 1)
				.getUrl()));
		for (int i = 0; i < infos.size(); i++) {
			views.add(ViewFactory.getImageView(this, infos.get(i).getUrl()));
		}
		// 将第一个ImageView添加进来
		views.add(ViewFactory.getImageView(this, infos.get(0).getUrl()));
		
		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views, infos, mAdCycleViewListener);
		if(infos.size()==1){
			// 设置轮播
			cycleViewPager.setWheel(false);
		}else
		
		cycleViewPager.setWheel(true);// 设置轮播

		// 设置轮播时间，默认5000ms
		cycleViewPager.setTime(2000);
		// 设置圆点指示图标组居中显示，默认靠右
		cycleViewPager.setIndicatorCenter();
	}

	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(PicInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				// Toast.makeText(ProductInfoActivity.this,
				// "position-->" + info.getContent(), Toast.LENGTH_SHORT)
				// .show();
			}

		}

	};

	/**
	 * 配置ImageLoder
	 */
	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			// showDialog();
			Message msg = new Message();
			Bundle data = new Bundle();
			String result = "";
			MultipartEntity mpEntity = new MultipartEntity();
			try {

				mpEntity.addPart("e.id", new StringBody(id));

			} catch (UnsupportedEncodingException e) {
			}
			HttpUtil http = new HttpUtil();
			result = http.postDataMethod(UrlEntry.QUERY_PRODUCT_BYID_URL,
					mpEntity);
			data.putString("value", result);
			msg.setData(data);
			msg.what = 0;

			handler.sendMessage(msg);
		}
	};

	Runnable payrunnable = new Runnable() {
		@Override
		public void run() {

			Message msg = new Message();
			Bundle data = new Bundle();
			String result = "";
			MultipartEntity mpEntity = new MultipartEntity();

			try {

				mpEntity.addPart("uuid", new StringBody(App.uuid));
				mpEntity.addPart("num", new StringBody(mTvBuyCount.getText()
						.toString()));
				mpEntity.addPart("productId", new StringBody(id));

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpUtil http = new HttpUtil();
			result = http.postDataMethod(UrlEntry.PAY_ORDER_URL, mpEntity);
			data.putString("value", result);
			msg.setData(data);
			msg.what = 1;

			handler.sendMessage(msg);
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			disDialog();
			Bundle data1 = msg.getData();
			String result = data1.getString("value");

			switch (msg.what) {
			case 0:
				try {
					JSONObject jsonresult = new JSONObject(result);
					if (jsonresult.optString("success").equals("1")) {
						JSONObject jsono = jsonresult.optJSONObject("list");
						GsonBuilder gsonb = new GsonBuilder();
						Gson gson = gsonb.create();
						ProductEntry mInfoEntry = gson.fromJson(
								jsono.toString(), ProductEntry.class);
						mTvProductName.setText(mInfoEntry.getName());
						mTvProductNowPrice.setText(mInfoEntry.getNowPrice());
						mTvTotalPrice.setText(mInfoEntry.getNowPrice());
						mTvProductOldPrice.setText(mInfoEntry.getPrice());
						mTvProductOldPrice.getPaint().setFlags(
								Paint.STRIKE_THRU_TEXT_FLAG);
						mTvProductSellCount.setText("已售"
								+ mInfoEntry.getSellcount());
						mTvProductStock.setText("库存" + mInfoEntry.getStock());
						mStockCount = mInfoEntry.getStock();
						mTvBusinessName.setText(mInfoEntry.getCompName());
						try {
							mNowPrice = Float.parseFloat(mInfoEntry
									.getNowPrice());
						} catch (Exception e) {
							mNowPrice = 0f;
						}
						if (!TextUtils.isEmpty(mInfoEntry.getImages())) {
							String[] imgs = mInfoEntry.getImages().split(",");
							imageUrl = new String[imgs.length];
							for (int i = 0; i < imgs.length; i++) {
								imageUrl[i] = UrlEntry.ip + imgs[i];
							}
							configImageLoader();
							initialize();
						}else{
							imageUrl = new String[1];
							imageUrl[0] = UrlEntry.ip+mInfoEntry.getPicture();
							configImageLoader();
							initialize();
						}
					} else {
						// showToast("查询失败！");
					}
				} catch (JSONException e) {
				}
				break;
			case 1:
				try {
					JSONObject jsonresult = new JSONObject(result);
					if (jsonresult.optString("success").equals("1")) {

						Intent intent = new Intent(ProductInfoActivity.this,
								PayActivity.class);
						intent.putExtra("url", jsonresult.get("url").toString());
						startActivityForResult(intent, 11);
					} else {
						Toast.makeText(getApplicationContext(),
								"用户信息错误，请重新登录！", Toast.LENGTH_SHORT).show();
						SharedPreferences sp = getSharedPreferences(
								App.USERINFO, 0);
						Editor editor = sp.edit();
						editor.putString("loginkey", "");
						editor.commit();
						App.uuid = "";
						Intent intent = new Intent(ProductInfoActivity.this,
								LoginActivity.class);
						startActivityForResult(intent, 22);
					}
				} catch (JSONException e) {
				}
				break;

			}

		}
	};

	/**
	 * 
	 * 
	 * @return
	 */
	public int getSize(boolean mIsWidth) {
		Display display = getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		if (mIsWidth)
			return width < height ? width : height; //
		else
			return height > width ? height : width; //
	}

	// 环形加载进度条
	public void showDialog() {
		dialog = new LoadingDialog(this);
		dialog.setCancelable(false);
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

	public boolean isLogin(Context context) {
		boolean falg = false;
		SharedPreferences sp = context.getSharedPreferences("USERINFO", 1);
		if (TextUtils.isEmpty(sp.getString("loginkey", ""))) {
			falg = true;
			Intent intent = new Intent(context, LoginActivity.class);
			startActivityForResult(intent, 22);
		} else {
			App.userID = sp.getString("id", "");
			App.username = sp.getString("username", "");
			App.uuid = sp.getString("loginkey", "");
			App.userphone = sp.getString("phone", "");
			App.userphoto = sp.getString("PHOTO", "");
			App.usertype = sp.getString("TYPE", "");
		}
		return falg;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i("car", "  resultCode = " + resultCode);

	}
}
