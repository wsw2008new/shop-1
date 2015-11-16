package cn.my360.shop.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.my360.shop.R;
import cn.my360.shop.entity.OrderProductDetails;
import cn.my360.shop.util.UrlEntry;

import com.lidroid.xutils.BitmapUtils;

public class OrderCommodityDetailAdapter extends BaseAdapter {
	private Context ctx;
	private ViewHolder holder;
	private List<OrderProductDetails> childJson;
	private LayoutInflater mInflater;
	private BitmapUtils bitmapUtils;

	private class ViewHolder {
		ImageView mOrderIcon;// ��ƷͼƬ
		TextView mOrderTitle;// ��Ʒ����
		TextView mOrderNowprice;// ����۸�
		TextView mOrderCount;// ��������
	}

	public OrderCommodityDetailAdapter(LayoutInflater mInflater,BitmapUtils bitmapUtils,
			List<OrderProductDetails> list) {
		this.childJson = list;
		this.mInflater = mInflater;
		this.bitmapUtils = bitmapUtils;
	}

	@Override
	public int getCount() {
		return childJson.size();
	}

	@Override
	public OrderProductDetails getItem(int position) {
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
			convertView = mInflater.inflate(
					R.layout.item_order_commodity_details, null);
			holder.mOrderIcon = (ImageView) convertView
					.findViewById(R.id.imagehead);
			holder.mOrderTitle = (TextView) convertView
					.findViewById(R.id.order_name_text);
			holder.mOrderCount = (TextView) convertView
					.findViewById(R.id.order_count_text);
			holder.mOrderNowprice = (TextView) convertView
					.findViewById(R.id.order_price_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		OrderProductDetails details = childJson.get(posotion);
		holder.mOrderTitle.setText(details.getProductName());
		holder.mOrderCount.setText(details.getNumber());
		holder.mOrderNowprice.setText(details.getPrice());
		if (details.getPicture().toString().equals("")) {
			holder.mOrderIcon.setImageResource(R.drawable.wutu);
		} else {
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.wutu);
			bitmapUtils.display(holder.mOrderIcon, UrlEntry.ip
					+ details.getPicture().toString());// ���ز���ʾͼƬ
		}

		return convertView;
	}

	/**
	 * ��������б���
	 * 
	 * @param newsitem
	 */
	public void addInfoItem(OrderProductDetails info) {
		childJson.add(info);
	}

}
