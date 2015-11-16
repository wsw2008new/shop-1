package cn.my360.shop.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.my360.shop.R;
import cn.my360.shop.http.HttpUtil;
import cn.my360.shop.util.App;
import cn.my360.shop.util.MD5;
import cn.my360.shop.util.UrlEntry;

/**
 * @author Administrator
 */
public class LoginActivity extends BaseActivity {

	// UI references.
	private EditText mUserView;
	private EditText mPasswordView;
	private Button mLoginBtn;
	private TextView mRegister;
	private int type = 0;
	private String username, pwd;
	private SharedPreferences sp;
	private String intentsell = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		sp = getSharedPreferences(App.USERINFO, MODE_PRIVATE);
		((TextView) findViewById(R.id.def_head_tv)).setText("µÇ Â¼");
		((ImageButton) findViewById(R.id.def_head_back))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent intent = new Intent(LoginActivity.this,
								ActivityMain.class);
						startActivity(intent);
						finish();

					}
				});

		if (getIntent().hasExtra("sell")) {
			intentsell = getIntent().getStringExtra("sell");
		}

		mUserView = (EditText) findViewById(R.id.et_user);
		if (!TextUtils.isEmpty(sp.getString("username", ""))) {
			mUserView.setText(sp.getString("username", ""));
			App.userID = sp.getString("id", "");
			App.username = sp.getString("username", "");
			App.uuid = sp.getString("loginkey", "");
			App.userphone = sp.getString("phone", "");
			App.userphoto = sp.getString("PHOTO", "");
			App.usertype = sp.getString("TYPE", "");
		}
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mUserView.getWindowToken(), 0);
		mPasswordView = (EditText) findViewById(R.id.et_password);
		mPasswordView.setHint("ÇëÊäÈëÃÜÂë");

		mLoginBtn = (Button) findViewById(R.id.btn_login);
		mLoginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDialog();
				new Thread(runnable).start();
				// finish();
			}
		});
		mRegister = (TextView) findViewById(R.id.textView2);
		mRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				// intent.putExtra("type", String.valueOf(type));
				startActivity(intent);
				// finish();
			}
		});

	}

	private String roletype = "1";
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			disDialog();
			switch (msg.what) {
			case 1:
				showToast("µÇÂ¼³É¹¦£¡");
				// if (intentsell != "") {
				// Intent intent = new Intent(LoginActivity.this,
				// ActivityMain.class);
				// startActivity(intent);
				// } else
				setResult(22);
				finish();
				break;
			case 2:

				showToast("µÇÂ¼Ê§°Ü,ÓÃ»§Ãû»òÃÜÂë´íÎó£¡");
				break;
			case 3:

				break;
			}
			super.handleMessage(msg);
		}
	};
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Message msg = new Message();
			Bundle data = new Bundle();
			username = mUserView.getText().toString();
			MD5 md5 = new MD5();
			pwd = md5.GetMD5Code(mPasswordView.getText().toString());
			HttpUtil http = new HttpUtil();
			String result = http.postDataMethod(UrlEntry.LOGIN_URL, username,
					pwd);
			try {
				JSONObject jsonresult = new JSONObject(result);
				if (jsonresult.get("success").toString().equals("1")) {
					result = "µÇÂ¼³É¹¦£¡";
					msg.what = 1;
					Editor edit = sp.edit();
					edit.putString("username", mUserView.getText().toString());
					edit.putString("loginkey", jsonresult.get("uuid")
							.toString());
					edit.putString("id", jsonresult.get("id").toString());
					edit.putString("phone", jsonresult.get("phone").toString());
					edit.putString("PHOTO", jsonresult.get("picture")
							.toString());
					edit.putString("TYPE", jsonresult.get("type").toString());

					edit.commit();
					App.userID = jsonresult.get("id").toString();
					App.username = mUserView.getText().toString();
					App.uuid = jsonresult.get("uuid").toString();
					App.userphone = jsonresult.get("phone").toString();
					App.userphoto = jsonresult.get("picture").toString();
					App.usertype = jsonresult.get("type").toString();

					msg.what = 1;

				} else {

					result = jsonresult.get("success").toString();
					msg.what = 2;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			data.putString("value", result);
			msg.setData(data);

			handler.sendMessage(msg);
		}
	};

}
