package cn.my360.shop.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import cn.my360.shop.R;
import cn.my360.shop.util.App;
import cn.my360.shop.util.MD5;
import cn.my360.shop.util.UrlEntry;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class RegisterActivity extends BaseActivity {

	private EditText mUserEdit,mPwdEdit,mPhoneEdit;
	private Button mRegisterBtn;
	private SharedPreferences sp;
	private Spinner roleSpinner;
	private String roles = "1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		((TextView)findViewById(R.id.def_head_tv)).setText("注  册");
		mUserEdit = (EditText) findViewById(R.id.et_user);
		mPwdEdit = (EditText) findViewById(R.id.et_password);
		mPhoneEdit = (EditText) findViewById(R.id.et_phone);
		sp = getSharedPreferences(App.USERINFO,MODE_PRIVATE);
		((ImageButton) findViewById(R.id.def_head_back))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						finish();

					}
				});
		mRegisterBtn = (Button) findViewById(R.id.btn_refister);
		mRegisterBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				loadData();
				
			}
		});
		roleSpinner = (Spinner) findViewById(R.id.Spinner01);
		ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);   
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		roleSpinner.setAdapter(adapter);
		roleSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				System.out.println(position+" ");
//				roles = arg0.getItemAtPosition(position).toString();
				roles = String.valueOf(position+1);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void loadData() {
		
	if(TextUtils.isEmpty(mUserEdit.getText().toString())||TextUtils.isEmpty(mPwdEdit.getText().toString())||TextUtils.isEmpty(mPhoneEdit.getText().toString())){
		showToast("注册信息不能为空！");
		return;
	}
	showDialog();
	RequestParams params = new RequestParams();


	String result = "";
		MD5 md = new MD5();
		params.addBodyParameter("e.username", mUserEdit.getText().toString());
		params.addBodyParameter("e.password", md.GetMD5Code(mPwdEdit.getText().toString()));
		params.addBodyParameter("e.phone",mPhoneEdit.getText().toString());
		params.addBodyParameter("e.type", roles);
		final HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, UrlEntry.REISTER_URL, params,
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
						disDialog();
						// 清空所有已存在的数据
						{

						}
						getDTOArray(responseInfo.result);
						

						Log.i("login", " login responseInfo.result = "
								+ responseInfo.result);

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						showToast("注册失败,请稍后再试！");
						disDialog();
						
					}
				});

	}

	public void getDTOArray(String result)
	{
		try {
			JSONObject jsonresult= new JSONObject(result);
			if(jsonresult.get("success").toString().equals("1")){
				showToast("注册成功！");
				Editor edit = sp.edit();
				edit.putString("username", mUserEdit.getText().toString());
				edit.commit();
				finish();
				
				
			}else
			{
				showToast("注册失败！"+jsonresult.get("message").toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
}
