package cn.my360.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.my360.shop.R;

public class PayActivity extends BaseActivity   {

	private WebView webView;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_webview);
		((TextView) findViewById(R.id.def_head_tv)).setText("支付");
		ImageButton back = (ImageButton) findViewById(R.id.def_head_back);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setResult(11);
				onBackPressed();
				
			}
		});
		showDialog();
		webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.getSettings().setDefaultTextEncodingName("utf-8");
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); 
		
		Intent intent=getIntent();
		if(intent!=null)
		{
			
			url=intent.getStringExtra("url");
			webView.loadUrl(url); 
		
		webView.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	             view.loadUrl(url);
	            return true;
	        }
	           @Override
				public void onPageFinished(WebView view, String url) {
					disDialog();
					

					
					webView.setVisibility(View.VISIBLE);
					super.onPageFinished(view, url);
				}
	       });
		}

		
	}

	
	

	

	

	

}
