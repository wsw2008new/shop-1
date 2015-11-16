package cn.my360.shop.adapter;

import java.util.List;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.my360.shop.R;
import cn.my360.shop.entity.ProductEntry;
import cn.my360.shop.util.UrlEntry;

import com.lidroid.xutils.BitmapUtils;



public class ListViewAdapter extends BaseAdapter {

	private ViewHolder holder;
	private List<ProductEntry> childJson;
	private LayoutInflater mInflater;
	private BitmapUtils bitmapUtils ;
	private class ViewHolder {
		ImageView mInfoIcon;
		TextView mInfoTitle;
		TextView mInfoOldprice;
		TextView mInfoNowprice;
	}
	public ListViewAdapter(List<ProductEntry> list,LayoutInflater mInflater,BitmapUtils bitmapUtils) {
		this.childJson = list;
		this.mInflater= mInflater;
		this.bitmapUtils = bitmapUtils;
	}

	
	@Override
	public int getCount() {
		return childJson.size();
	}

	@Override
	public Object getItem(int position) {
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
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_info_fragment_list, null);
			holder.mInfoIcon = (ImageView) convertView.findViewById(R.id.imagehead);
			holder.mInfoTitle = (TextView) convertView.findViewById(R.id.info_list_name_text);
			holder.mInfoOldprice = (TextView) convertView.findViewById(R.id.info_oldprice_text);
			holder.mInfoOldprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); 
			holder.mInfoNowprice = (TextView) convertView.findViewById(R.id.info_nowprice_text);
			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ProductEntry info = childJson.get(posotion);
		holder.mInfoTitle.setText(info.getName());
		holder.mInfoOldprice.setText( String.valueOf(info.getPrice()));
		holder.mInfoNowprice.setText(String.valueOf(info.getNowPrice()));
			
		

		
		

		if (info.getPicture().toString().equals("")) {
			holder.mInfoIcon.setImageResource(R.drawable.wutu);
		} else {
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.wutu);
			bitmapUtils.display(holder.mInfoIcon, UrlEntry.ip
					+ info.getPicture().toString());// 下载并显示图片
			
		}
		
		return convertView;
	}
	 /**
     * 添加数据列表项
     * @param newsitem
     */
    public void addInfoItem(ProductEntry info){
    	childJson.add(info);
    }


}
