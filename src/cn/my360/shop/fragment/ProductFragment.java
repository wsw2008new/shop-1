package cn.my360.shop.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import cn.my360.shop.R;
import cn.my360.shop.activity.ChoseTypeActivity;
import cn.my360.shop.activity.LoginActivity;
import cn.my360.shop.activity.ProductInfoActivity;
import cn.my360.shop.activity.SearchProductActivity;
import cn.my360.shop.adapter.ListViewAdapter;
import cn.my360.shop.entity.ProductEntry;
import cn.my360.shop.util.App;
import cn.my360.shop.util.UrlEntry;
import cn.my360.shop.weight.AutoListView;
import cn.my360.shop.weight.AutoListView.OnLoadListener;
import cn.my360.shop.weight.AutoListView.OnRefreshListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * @version 1.0 2015-11-12
 * @since 1.0
 */
public class ProductFragment extends BaseFragment implements OnClickListener,
		OnRefreshListener, OnLoadListener {

	private ArrayList<View> mlistViews;
	private Button mLoginBtn;
	private SharedPreferences sp;

	private Button seachCatalogsbtn, seachzuixinbtn, seachremenbtn,
			seachjiagebtn, btn_search;
	// Fields
	private int mCurPos;
	private EditText et_to_search, et_search;
	private FrameLayout fl_search;
	private ImageButton ib_cancel;

	private String pigType = "";
	private AutoListView mlistView;
	private LayoutInflater mInflater;
	private ListViewAdapter infoAdapter;
	public static List<ProductEntry> childJson = new ArrayList<ProductEntry>();
	private String key = "";
	public static String page = "1", pageSize = "10";

	private String orderType;// 买卖状态
	private String pricepx;// 排序 BitmapUtils bitmapUtils;
	private BitmapUtils bitmapUtils;
	private final int TYPE_ID = 22;
	private final int REFRESH = 27;
	private final int TYPE_REFRESH = 1;
	private final int TYPE_LOAD = 2;

	public static ProductFragment newInstance() {
		ProductFragment fragment = new ProductFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.product_fragment, null);

		sp = getActivity().getSharedPreferences(App.USERINFO, 1);
		mLoginBtn = (Button) view.findViewById(R.id.login_btn);
		if (!TextUtils.isEmpty(sp.getString("loginkey", "").toString())) {
			mLoginBtn.setText("欢迎：" + sp.getString("username", "").toString());
		} else
			mLoginBtn.setOnClickListener(this);
		bitmapUtils = new BitmapUtils(getActivity());
		seachCatalogsbtn = (Button) view.findViewById(R.id.seachCatalogsbtn); // 分类
		seachCatalogsbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						ChoseTypeActivity.class);
				startActivityForResult(intent, TYPE_ID);
			}
		});
		seachzuixinbtn = (Button) view.findViewById(R.id.seachzuixinbtn); // 最新搜索
		seachzuixinbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		seachremenbtn = (Button) view.findViewById(R.id.seachremenbtn);// 热门搜索
		seachremenbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		seachjiagebtn = (Button) view.findViewById(R.id.seachjiagebtn); // 价格排序
		seachjiagebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		if(childJson.size()==0){
		childJson.clear();
		page = "1";
		loadData(page, pageSize, "", TYPE_REFRESH);
		}else{
			
		}
		initView(view);
		return view;
	}

	public void initView(View view) {

		mlistView = (AutoListView) view.findViewById(R.id.lstv);
		mInflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		et_to_search = (EditText) view.findViewById(R.id.et_to_search);
		et_to_search.setOnClickListener(this);
		et_search = (EditText) view.findViewById(R.id.et_search);
		et_search.addTextChangedListener(mTextWatcher);
		fl_search = (FrameLayout) view.findViewById(R.id.fl_search);
		ib_cancel = (ImageButton) view.findViewById(R.id.ib_cancel);
		ib_cancel.setOnClickListener(this);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		btn_search.setOnClickListener(this);
		infoAdapter = new ListViewAdapter(childJson, mInflater, bitmapUtils);
		mlistView.setAdapter(infoAdapter);
		mlistView.setOnRefreshListener(this);
		mlistView.setOnLoadListener(this);
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int witch,
					long arg3) {

				if (witch > childJson.size()) {
					return;
				}

				Intent intent = new Intent(getActivity(),
						ProductInfoActivity.class);
				System.out.println(childJson.get(witch - 1).getId() + "");
				intent.putExtra("id", childJson.get(witch - 1).getId());

				startActivityForResult(intent, REFRESH);

			}
		});

	}

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
		case TYPE_ID:
			if (data == null)
				return;
			Bundle b = data.getExtras();
			String name = b.getString("id");

			// setData("1","key", TYPE_REFRESH);
			// loadData("1", pageSize, provinceContent, cityContent,
			// quxianContent,
			// orderType, pigType, pricepx);
			break;
		case 23:
			if (data == null)
				return;
			Bundle bundle = data.getExtras();
			pigType = bundle.getString("pigType");
			System.out.println(pigType);
			// setData("1", TYPE_REFRESH);
			// loadData("1", pageSize, provinceContent, cityContent,
			// quxianContent,
			// orderType, pigType, pricepx);
			break;
		case 24:
			if (data == null)
				return;
			Bundle bd = data.getExtras();
			orderType = bd.getString("orderType");
			// loadData("1", pageSize, provinceContent, cityContent,
			// quxianContent,
			// orderType, pigType, pricepx);
			// setData("1", TYPE_REFRESH);
			break;
		case 25:

			if (data == null)
				return;
			Bundle d = data.getExtras();
			pricepx = d.getString("orderBy");

			// setData("1", TYPE_REFRESH);
			break;
		case REFRESH:
			// setData("1", TYPE_REFRESH);
			break;

		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (!TextUtils.isEmpty(sp.getString("loginkey", "").toString())) {
			mLoginBtn.setText("欢迎：" + sp.getString("username", "").toString());
			mLoginBtn.setEnabled(false);
		}

	}

	@Override
	public void onClick(View view) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (view.getId()) {
		case R.id.login_btn:
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.et_to_search:
			startActivity(new Intent(getActivity(),SearchProductActivity.class));
//			fl_search.setVisibility(View.VISIBLE);
//			et_search.requestFocus();
//			et_search.setFocusable(true);
//			et_search.setFocusableInTouchMode(true);
//
//			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

			break;
		case R.id.btn_search:

			imm.hideSoftInputFromWindow(et_search.getWindowToken(), 0);
			fl_search.setVisibility(View.GONE);
			if (btn_search.getText().equals("搜索")) {
				// 根据条件进行搜索
				System.out.println("按条件搜索");
				key = et_search.getText().toString();
				loadData("1", pageSize, key, TYPE_REFRESH);
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

}
