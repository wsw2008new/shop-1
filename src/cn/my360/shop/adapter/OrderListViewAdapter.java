package cn.my360.shop.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.my360.shop.R;
import cn.my360.shop.entity.Order;
import cn.my360.shop.entity.OrderProductDetails;
import cn.my360.shop.http.HttpUtil;
import cn.my360.shop.util.App;
import cn.my360.shop.util.AppStaticUtil;
import cn.my360.shop.util.UrlEntry;
import cn.my360.shop.weight.LoadingDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.BitmapUtils;

public class OrderListViewAdapter extends BaseAdapter {
	private Context ctx;
	private ViewHolder holder;
	private List<Order> childJson;
	private LayoutInflater mInflater;
	private BitmapUtils bitmapUtils;
	private CommitReceipt mCommitReceipt;
	private LoadingDialog dialog = null;

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
			holder.mOrderStatusBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showDialog();
					mCommitReceipt = new CommitReceipt(order.getId(), App.uuid,
							mHandler);
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
				// success();
				break;
			case 0:
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

	public static boolean commitReceipt(String orderid, String uuid) {
		System.out.println(orderid + "--------" + uuid);
		String result = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("orderId", orderid));
		params.add(new BasicNameValuePair("uuid", uuid));
		result = HttpUtil.postRequest(UrlEntry.UPDATE_ORDER_STATUS_URL, params);
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

	// 环形加载进度条
	public void showDialog() {
		dialog = new LoadingDialog(ctx);
		dialog.setCancelable(false);
		dialog.show();
	}

	public void showDialog(String msg) {
		dialog = new LoadingDialog(ctx, msg);
		dialog.show();
	}

	public void disDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
