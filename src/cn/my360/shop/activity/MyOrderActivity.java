package cn.my360.shop.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.BitmapUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import cn.my360.shop.R;
import cn.my360.shop.adapter.OrderCommodityDetailAdapter;
import cn.my360.shop.adapter.OrderListViewAdapter;
import cn.my360.shop.entity.Order;
import cn.my360.shop.entity.OrderProductDetails;
import cn.my360.shop.http.HttpUtil;
import cn.my360.shop.util.App;
import cn.my360.shop.util.AppStaticUtil;
import cn.my360.shop.util.UrlEntry;
import cn.my360.shop.weight.LoadingDialog;

public class MyOrderActivity extends BaseActivity {
	private TextView mTvTitle;
	private ImageButton mIbBack;
	private LinearLayout mLlLoading;
	private LinearLayout mLlLoadFailure;
	private SwipeRefreshLayout mSrlData;
	private ListView mLv;
	private View mFooterView;
	private LinearLayout mLlFooterLoading;
	private TextView mTvFooterResult;
	private int mPage;
	private static final int PAGE_SIZE = 20;
	private MyOrderThread mSelectedOrderListThread;
	private boolean mIsLoadAll = false;
	private List<Order> mList;
	private static List<Order> tempList;
	private OrderListViewAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_order);
		initView();
	}

	private void initView() {
		mTvTitle = ((TextView) findViewById(R.id.def_head_tv));
		mTvTitle.setText("我的订单");
		mIbBack = (ImageButton) findViewById(R.id.def_head_back);
		mIbBack.setOnClickListener(mClickListener);
		mLlLoading = (LinearLayout) findViewById(R.id.ll_loading);
		mLlLoadFailure = (LinearLayout) findViewById(R.id.ll_load_failure);
		mLlLoadFailure.setOnClickListener(mClickListener);
		mLv = (ListView) findViewById(R.id.lv);
		// mLv.setOnItemClickListener(mItemClickListener);
		mLv.setOnScrollListener(mOnScrollListener);
		mSrlData = (SwipeRefreshLayout) findViewById(R.id.srl_data);
		mList = new ArrayList<Order>();
		mSrlData.setOnRefreshListener(mRefreshListener);
		mSrlData.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		reload();
		mFooterView = getLayoutInflater().inflate(R.layout.footer_view, null);
		mLlFooterLoading = (LinearLayout) mFooterView
				.findViewById(R.id.ll_footer_loading);
		mTvFooterResult = (TextView) mFooterView
				.findViewById(R.id.tv_footer_result);
	}

	private void reload() {
		mPage = 1;
		mIsLoadAll = false;
		mSelectedOrderListThread = new MyOrderThread(mPage, PAGE_SIZE,
				App.uuid, mHandler);
		mSelectedOrderListThread.start();
	}

	/**
	 * 下拉刷新
	 */
	private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

		@Override
		public void onRefresh() {
			reload();
		}
	};

	private OnScrollListener mOnScrollListener = new OnScrollListener() {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			if (firstVisibleItem + visibleItemCount == totalItemCount) {
				if (mSelectedOrderListThread != null || totalItemCount == 0)
					return;
				if (mIsLoadAll)
					return;
				mPage += 1;
				mSelectedOrderListThread = new MyOrderThread(mPage, PAGE_SIZE,
						App.uuid, mHandler);
				mSelectedOrderListThread.start();
			}
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}
	};

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.def_head_back:
				finish();
				break;
			case R.id.ll_load_failure:
				load();
				break;
			}
		}
	};

	private void load() {
		reload();
		mLlLoading.setVisibility(View.VISIBLE);
		mSrlData.setVisibility(View.GONE);
		mLlLoadFailure.setVisibility(View.GONE);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				success();
				break;
			case 0:
				failure();
				break;
			}
		}
	};

	private class MyOrderThread extends Thread {
		private int offset;
		private int pagesize;
		private String uuid;
		private Handler handler;

		public MyOrderThread(int offset, int pagesize, String uuid,
				Handler handler) {
			super();
			this.offset = offset;
			this.pagesize = pagesize;
			this.uuid = uuid;
			this.handler = handler;
		}

		@Override
		public void run() {
			super.run();
			boolean flag = selectOrderList(offset, pagesize, uuid);
			if (flag)
				handler.sendEmptyMessage(1);
			else
				handler.sendEmptyMessage(0);
		}
	}

	/**
	 * 查询订单
	 * 
	 * @param offset
	 *            (起始序号: 假如每页20条数据,第一页offset为0,第二页为20,第三页为40)
	 */
	public static boolean selectOrderList(int offset, int pagesize, String uuid) {
		String result = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pager.offset", String
				.valueOf(offset)));
		params.add(new BasicNameValuePair("pager.pageSize", String
				.valueOf(pagesize)));
		params.add(new BasicNameValuePair("uuid", uuid));
		result = HttpUtil.postRequest(UrlEntry.QUERY_ORDER_URL, params);
		if (!TextUtils.isEmpty(result)) {
			System.out.println("QUERY_ORDER_URL:" + result);
			try {
				JSONObject jsonresult = new JSONObject(result);
				if (jsonresult.optString("success").equals("1")) {
					tempList = new ArrayList<Order>();
					JSONArray array = jsonresult.optJSONArray("list");
					if (array != null) {
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = array.optJSONObject(i);
							GsonBuilder gsonb = new GsonBuilder();
							Gson gson = gsonb.create();
							Order order = gson.fromJson(obj.toString(),
									Order.class);
							List<OrderProductDetails> details = new ArrayList<OrderProductDetails>();
							JSONArray arr = obj.optJSONArray("orderdetail");
							if (arr != null) {
								for (int j = 0; j < arr.length(); j++) {
									JSONObject o = arr.optJSONObject(j);
									GsonBuilder gb = new GsonBuilder();
									Gson g = gb.create();
									OrderProductDetails detail = g.fromJson(
											o.toString(),
											OrderProductDetails.class);
									details.add(detail);
								}
							}
							order.setOrderdetail(details);
							tempList.add(order);
						}
					}
					return true;
				}
			} catch (Exception e) {
			}
		}
		return false;
	}

	private void success() {
		mSelectedOrderListThread = null;
		if (mLlLoading.getVisibility() == View.VISIBLE) {
			mLlLoading.setVisibility(View.GONE);
			mSrlData.setVisibility(View.VISIBLE);
		} else {
			mSrlData.setRefreshing(false);
		}
		if (mPage == 1) {
			if (mList.size() > 0) {
				mList.clear();
				mLv.removeFooterView(mFooterView);
			}
			mLv.addFooterView(mFooterView);
			mList.addAll(tempList);
			mAdapter = new OrderListViewAdapter(MyOrderActivity.this, mList);
			mLv.setAdapter(mAdapter);
			if (tempList.size() == 0) {
				mLlFooterLoading.setVisibility(View.GONE);
				mTvFooterResult.setVisibility(View.VISIBLE);
				mTvFooterResult.setText("查询结果为空");
				mIsLoadAll = true;
			} else if (tempList.size() < 20) {
				mLlFooterLoading.setVisibility(View.GONE);
				mTvFooterResult.setVisibility(View.VISIBLE);
				mTvFooterResult.setText("已加载全部");
				mIsLoadAll = true;
			} else {
				mLlFooterLoading.setVisibility(View.VISIBLE);
				mTvFooterResult.setVisibility(View.GONE);
			}
		} else {
			mList.addAll(tempList);
			mAdapter = new OrderListViewAdapter(MyOrderActivity.this, mList);
			mLv.requestLayout();
			mAdapter.notifyDataSetChanged();
			if (tempList.size() < 20) {
				mLlFooterLoading.setVisibility(View.GONE);
				mTvFooterResult.setVisibility(View.VISIBLE);
				mTvFooterResult.setText("已加载全部");
				mIsLoadAll = true;
			} else {
				mLlFooterLoading.setVisibility(View.VISIBLE);
				mTvFooterResult.setVisibility(View.GONE);
			}
		}
	}

	private void failure() {
		mSelectedOrderListThread = null;
		if (mLlLoading.getVisibility() == View.VISIBLE) {
			mLlLoading.setVisibility(View.GONE);
			mLlLoadFailure.setVisibility(View.VISIBLE);
		} else {
			mSrlData.setRefreshing(false);
		}
	}

	public class OrderListViewAdapter extends BaseAdapter {
		private Context ctx;
		private ViewHolder holder;
		private List<Order> childJson;
		private LayoutInflater mInflater;
		private BitmapUtils bitmapUtils;
		private CommitReceipt mCommitReceipt;

		private class ViewHolder {
			ListView mLv;
			TextView mShopName;// 商家名字
			TextView mStatus;
			TextView mOrderTotalPrice;// 总价
			Button mOrderStatusBtn; // 确认收货
			TextView mOrderStatusTxt;// 已确认收货
			TextView mShopOrderStatus;// 卖家发货情况
		}

		public OrderListViewAdapter(Context ctx, List<Order> list) {
			this.childJson = list;
			mInflater = LayoutInflater.from(ctx);
			bitmapUtils = new BitmapUtils(ctx);
		}

		@Override
		public int getCount() {
			return childJson.size();
		}

		@Override
		public Order getItem(int position) {
			return childJson.get(position);
		}

		@Override
		public long getItemId(int posotion) {
			return posotion;
		}

		@Override
		public View getView(int posotion, View convertView, ViewGroup arg2) {
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();//
				convertView = mInflater.inflate(R.layout.item_myorderinfo_list,
						null);
				holder.mLv = (ListView) convertView.findViewById(R.id.lv);
				holder.mShopName = (TextView) convertView
						.findViewById(R.id.shop_name_text);
				holder.mStatus = (TextView) convertView
						.findViewById(R.id.shop_status_text);
				holder.mOrderTotalPrice = (TextView) convertView
						.findViewById(R.id.totaltxt);
				holder.mOrderStatusTxt = (TextView) convertView
						.findViewById(R.id.confirmedgoods_text);
				holder.mOrderStatusBtn = (Button) convertView
						.findViewById(R.id.confirmedgoods_btn);
				holder.mShopOrderStatus = (TextView) convertView
						.findViewById(R.id.shop_status_text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final Order order = childJson.get(posotion);
			OrderCommodityDetailAdapter adapter = new OrderCommodityDetailAdapter(
					mInflater, bitmapUtils, order.getOrderdetail());
			holder.mLv.setAdapter(adapter);
			AppStaticUtil.setListViewHeightBasedOnChildren(holder.mLv);
			holder.mShopName.setText(order.getCompName());
			holder.mStatus.setText(getOrderStatus(order.getStatus()));
			holder.mOrderTotalPrice.setText(order.getAmount());
			if (order.getStatus().equals("send")) {
				holder.mOrderStatusBtn.setVisibility(View.VISIBLE);
				holder.mOrderStatusTxt.setVisibility(View.GONE);
				holder.mOrderStatusBtn
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								showDialog();
								mCommitReceipt = new CommitReceipt(order
										.getId(), App.uuid, mHandler);
								mCommitReceipt.start();
							}
						});
			} else if (order.getStatus().equals("sign")) {
				holder.mOrderStatusBtn.setVisibility(View.GONE);
				holder.mOrderStatusTxt.setVisibility(View.VISIBLE);
			} else {
				holder.mOrderStatusBtn.setVisibility(View.GONE);
				holder.mOrderStatusTxt.setVisibility(View.GONE);
			}
			return convertView;
		}

		/**
		 * 添加数据列表项
		 * 
		 * @param newsitem
		 */
		public void addInfoItem(Order info) {
			childJson.add(info);
		}

		private String getOrderStatus(String status) {
			if (TextUtils.isEmpty(status))
				return "";
			if (status.equals("init"))
				return "等待发货";
			else if (status.equals("pass"))
				return "已审核";
			else if (status.equals("send"))
				return "已发货";
			else if (status.equals("sign"))
				return "已签收";
			else if (status.equals("cancel"))
				return "已取消";
			else if (status.equals("file"))
				return "已归档";
			else if (status.equals("tikuan"))
				return "提款申请中";
			return status;
		}

		private Handler mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				disDialog();
				switch (msg.what) {
				case 1:
					load();
					break;
				case 0:
					showToast("确认收货失败");
					// failure();
					break;
				}
			}
		};

		private class CommitReceipt extends Thread {
			private String orderId;
			private String uuid;
			private Handler handler;

			public CommitReceipt(String orderId, String uuid, Handler handler) {
				super();
				this.orderId = orderId;
				this.uuid = uuid;
				this.handler = handler;
			}

			@Override
			public void run() {
				super.run();
				boolean flag = commitReceipt(orderId, uuid);
				if (flag)
					handler.sendEmptyMessage(1);
				else
					handler.sendEmptyMessage(0);
			}
		}

		public boolean commitReceipt(String orderid, String uuid) {
			System.out.println(orderid + "--------" + uuid);
			String result = null;
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("orderId", orderid));
			params.add(new BasicNameValuePair("uuid", uuid));
			result = HttpUtil.postRequest(UrlEntry.UPDATE_ORDER_STATUS_URL,
					params);
			if (!TextUtils.isEmpty(result)) {
				System.out.println("UPDATE_ORDER_STATUS_URL:" + result);
				try {
					JSONObject jsonresult = new JSONObject(result);
					if (jsonresult.optString("success").equals("1")) {
						return true;
					}
				} catch (Exception e) {
				}
			}
			return false;
		}
	}
}
