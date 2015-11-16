package cn.my360.shop.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import cn.my360.shop.R;

import com.nostra13.universalimageloader.core.ImageLoader;


public class ViewFactory {

	/**
	 * 获取ImageView视图的同时加载显示url
	 * 
	 * @param text
	 * @return
	 */
	public static ImageView getImageView(Context context, String url) {
		ImageView imageView = (ImageView)LayoutInflater.from(context).inflate(
				R.layout.view_banner, null);
		ImageLoader.getInstance().displayImage(url, imageView);
		return imageView;
	}
}
