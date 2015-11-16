package cn.my360.shop.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.AdapterView.OnItemClickListener;
import cn.my360.shop.R;
import cn.my360.shop.adapter.ListViewAdapter;
import cn.my360.shop.entity.ProductEntry;
import cn.my360.shop.util.AppManager;
import cn.my360.shop.util.UrlEntry;
import cn.my360.shop.weight.AutoListView;
import cn.my360.shop.weight.AutoListView.OnLoadListener;
import cn.my360.shop.weight.AutoListView.OnRefreshListener;

public class SearchProductActivity extends BaseActivity implements OnClickListener,
OnRefreshListener, OnLoadListener{

	private AutoListView mlistView;
	private EditText et_search;
	private FrameLayout fl_search;
	private ImageButton ib_cancel;
	private String key = "";
	private Button btn_search;
	private LayoutInflater mInflater;
	private ListViewAdapter infoAdapter;
	public static List<ProductEntry> childJson = new ArrayList<ProductEntry>();
	public static String page = "1", pageSize = "10";
	private BitmapUtils bitmapUtils;
	private final int REFRESH = 27;
	private final int TYPE_REFRESH = 1;
	private final int TYPE_LOAD = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_product);
		bitmapUtils = new BitmapUtils(this);
		AppManager.addActivity(this);
		initView();
		
	}

	public void initView() {

		mlistView = (AutoListView)findViewById(R.id.lstv);
		mInflater = (LayoutInflater)getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		
		et_search = (EditText) findViewById(R.id.et_search);
		et_search.addTextChangedListener(mTextWatcher);
		fl_search = (FrameLayout) findViewById(R.id.fl_search);
		ib_cancel = (ImageButton) findViewById(R.id.ib_cancel);
		ib_cancel.setOnClickListener(this);
		btn_search = (Button) findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		infoAdapter = new ListViewAdapter(childJson, mInflater, bitmapUtils);
		mlistView.setAdapter(infoAdapter);
		mlistView.setLoadFullhide();
//		mlistView.setOnRefreshListener(this);
		mlistView.setOnLoadListener(this);
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int witch,
					long arg3) {

				if (witch > childJson.size()) {
					return;
				}

				Intent intent = new Intent(SearchProductActivity.this,
						ProductInfoActivity.class);
				System.out.println(childJson.get(witch - 1).getId() + "");
				intent.putExtra("id", childJson.get(witch - 1).getId());

				startActivityForResult(intent, REFRESH);

			}
		});

	}
	TextWatcher mTextWatcher = new TextWatcher() {
		private CharSequence temp;

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			temp = s;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

			if (temp.length() == 0) {
				btn_search.setText("取消");
				ib_cancel.setVisibility(View.GONE);
			} else {
				btn_search.setText("搜索");
				ib_cancel.setVisibility(View.VISIBLE);
			}

		}
	};
	public void loadData(String offset, String pagesize, String key,
			final int type) {

		this.pageSize = pagesize;
		page = offset;
		RequestParams params = new RequestParams();

		System.out.println(page + "页数" + pageSize);
		params.addBodyParameter("pager.offset", page);
		params.addBodyParameter("e.pageSize", pageSize);

		System.out.println(key);
		params.addBodyParameter("key", key);

		final HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST,
				UrlEntry.QUERY_PRODUCT_BYNAME_URL, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.i("insert", " insert responseInfo.result = "
								+ responseInfo.result);

						getDate(responseInfo.result, type);

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						mlistView.onRefreshComplete();

					}
				});
	}

	private void getDate(String result, int type) {

		JSONObject jsonresult;
		try {
			jsonresult = new JSONObject(result);
			if (jsonresult.get("success").toString().equals("1")) {

				List<ProductEntry> childJsonItem = new ArrayList<ProductEntry>();
				JSONArray jArrData = jsonresult.getJSONArray("list");

				for (int i = 0; i < jArrData.length(); i++) {
					JSONObject jobj2 = jArrData.optJSONObject(i);
					GsonBuilder gsonb = new GsonBuilder();
					Gson gson = gsonb.create();
					ProductEntry mInfoEntry = gson.fromJson(jobj2.toString(),
							ProductEntry.class);
					childJsonItem.add(mInfoEntry);

				}

				if (type == TYPE_REFRESH) {

					mlistView.onRefreshComplete();
					childJson.clear();
					childJson.addAll(childJsonItem);

				} else if (type == TYPE_LOAD) {

					mlistView.onLoadComplete();
					childJson.addAll(childJsonItem);
				}

				mlistView.setResultSize(childJsonItem.size());

				infoAdapter.notifyDataSetChanged();
			} else {
				// successType(jsonresult.get("success").toString(), "发布失败！");

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		page = "1";
		switch (resultCode) {
		
		
		case REFRESH:
			// setData("1", TYPE_REFRESH);
			break;

		}
	}



	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		

	}

	@Override
	public void onClick(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (view.getId()) {
		case R.id.login_btn:
			Intent intent = new Intent(SearchProductActivity.this, LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.et_to_search:
			fl_search.setVisibility(View.VISIBLE);
			et_search.requestFocus();
			et_search.setFocusable(true);
			et_search.setFocusableInTouchMode(true);

			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

			break;
		case R.id.btn_search:

			imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
			
			if (btn_search.getText().equals("搜索")) {
				// 根据条件进行搜索
				System.out.println("按条件搜索");
				key = et_search.getText().toString();
				loadData("1", pageSize, key, TYPE_REFRESH);
			}else{
				onBackPressed();
			}
			et_search.setText("");
			break;
		case R.id.ib_cancel:
			et_search.setText("");
			break;

		}
	}

	@Override
	public void onLoad() {
		System.out.println("加载更多" + page);
		page = String.valueOf(Integer.parseInt(page) + 1);
		setData(page, TYPE_LOAD);

	}

	@Override
	public void onRefresh() {
		page = "1";
		System.out.println("刷新");
		key = "";
		setData(page, TYPE_REFRESH);
	}

	private void setData(String page, int type) {
		loadData(page, pageSize, key, type);
	}
}
