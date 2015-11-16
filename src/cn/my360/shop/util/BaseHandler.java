package cn.my360.shop.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class BaseHandler extends Handler {
	Context c;
	public BaseHandler(Context c) {
		this.c=c;
	}
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		if(msg.what==-1){
		}
		
		if(msg.what==-2){
			Toast.makeText(c, "ÎÞÍøÂç", Toast.LENGTH_SHORT).show();
		}
		if(msg.what==-3){
			Toast.makeText(c, "Çë´ò¿ªGPS", Toast.LENGTH_SHORT).show();
		}
		
		
	}
}