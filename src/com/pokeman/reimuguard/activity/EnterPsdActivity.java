package com.pokeman.reimuguard.activity;


import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.utils.ConstantValue;
import com.pokeman.reimuguard.utils.Md5Util;
import com.pokeman.reimuguard.utils.SpUtil;
import com.pokeman.reimuguard.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EnterPsdActivity extends Activity {
	private String packagename;
	private TextView tv_app_name;
	private ImageView iv_app_icon;
	private EditText et_psd;
	private Button bt_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//获取包名
		packagename = getIntent().getStringExtra("packagename");
		setContentView(R.layout.activity_enter_psd);
		initUI();
		initData();
	}

	@SuppressWarnings("deprecation")
	private void initData() {
		//通过传递过来的包名获取拦截应用的图标以及名称
		PackageManager pm = getPackageManager();
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packagename,0);
			Drawable icon = applicationInfo.loadIcon(pm);
			iv_app_icon.setBackgroundDrawable(icon);
			tv_app_name.setText(applicationInfo.loadLabel(pm).toString());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		bt_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String psd = et_psd.getText().toString().trim();
				//获取保存的正确密码
				String psd_right = SpUtil.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");
				if(!TextUtils.isEmpty(psd)){
					//将输入的密码经过md5编码后再进行比较
					if(psd_right.equals(Md5Util.encoder(psd))){
						//解锁,进入应用,告知服务不要再去监听已经解锁的应用,发送广播
						Intent intent = new Intent("android.intent.action.SKIP");
						intent.putExtra("packagename",packagename);
						sendBroadcast(intent);
						
						finish();
					}else{
						ToastUtil.show(getApplicationContext(), "密码错误");
					}
				}else{
					ToastUtil.show(getApplicationContext(), "请输入密码");
				}
			}
		});
	}

	private void initUI() {
		tv_app_name = (TextView) findViewById(R.id.tv_app_name);
		iv_app_icon = (ImageView) findViewById(R.id.iv_app_icon);
		
		et_psd = (EditText) findViewById(R.id.et_psd);
		bt_submit = (Button) findViewById(R.id.bt_submit);
	}
	
	@Override
	public void onBackPressed() {
		//通过隐式意图,跳转到桌面
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
		super.onBackPressed();
	}
}
