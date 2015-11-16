package cn.my360.shop.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import cn.my360.shop.R;
import cn.my360.shop.http.HttpUtil;
import cn.my360.shop.util.UrlEntry;

import com.lidroid.xutils.http.client.multipart.MultipartEntity;
import com.lidroid.xutils.http.client.multipart.content.StringBody;

public class ChoseTypeActivity extends BaseActivity implements OnClickListener {

	private ListView mListview1, mListview2, mListview3;
	private TextView mTextview;
	private Button mButton;
	private String choicefirst = "";
	private String choicesecond = "";
	private String choicethree = "";
	private SimpleAdapter adapter;
	private String pid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_selector);
		((TextView) findViewById(R.id.def_head_tv)).setText("请选择");

		new Thread(runnable).start();
		initView();

	}

	private void initView() {
		mTextview = (TextView) findViewById(R.id.tv_choice);
		mButton = (Button) findViewById(R.id.btn_sure);
		mButton.setOnClickListener(this);
		mListview1 = (ListView) findViewById(R.id.lv_list1);
	

	}

	private void setListView(final List<Map<String, String>> data) {
		adapter = new SimpleAdapter(this, data, R.layout.item_province,
				new String[] { "name" }, new int[] { R.id.text1 });
		mListview1.setAdapter(adapter);
		mListview1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				choicefirst = data.get(position).get("name");
				pid = data.get(position).get("pid");
				mTextview.setText(choicefirst);
				new Thread(runnable).start();
			}
		});

		
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.btn_sure:
			if (pid.equals("")) {
				onBackPressed();
				return;
			}

			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("id", pid);

			intent.putExtras(bundle);
			setResult(22, intent);
			finish();
			break;
		}
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			showDialog();
			Message msg = new Message();
			Bundle data = new Bundle();
			String result = "";
			MultipartEntity mpEntity = new MultipartEntity();

			try {

				mpEntity.addPart("pid", new StringBody(pid));

			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HttpUtil http = new HttpUtil();
			result = http.postDataMethod(UrlEntry.QUERY_PRODUCT_TYPE_URL,
					mpEntity);

			data.putString("value", result);
			msg.setData(data);
			msg.what = 0;

			handler.sendMessage(msg);
		}
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			disDialog();
			Bundle data1 = msg.getData();
			String result = data1.getString("value");
			try {
				JSONObject jsonresult = new JSONObject(result);
				List<Map<String, String>> datalist = new ArrayList<Map<String, String>>();
				if (jsonresult.optString("success").equals("1")) {
					JSONArray array = jsonresult.optJSONArray("list");
					if (array.length() == 0) {
						// 直接跳轉到搜索頁面
						finish();
						return;
					}
					for (int i = 0; i < array.length(); i++) {
						JSONObject jobj2 = array.optJSONObject(i);

						Map<String, String> map = new HashMap<String, String>();

						map.put("pid", jobj2.optString("id"));
						map.put("name", jobj2.optString("name"));
						datalist.add(map);

					}
					setListView(datalist);

				} else {
					showToast("查询失败！");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

}
