package com.pokeman.reimuguard.base.implement;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.base.BasePager;
import com.pokeman.reimuguard.utils.ConstantValue;
import com.pokeman.reimuguard.utils.SpUtil;
import com.pokeman.reimuguard.utils.ToastUtil;


/**
 * 定位追踪页面
 * @author pokeman
 *
 */
public class LocationPager extends BasePager {

	private View mview;
	private EditText et_phone_number;
	private Button bt_save_number;
	private String  phone;

	public LocationPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		System.out.println("定位页面初始化");
		
		mview = View.inflate(mActivity, R.layout.pager_location, null);
	    
	    //先清空布局再添加,避免切换选项卡的时候页面布局重复加载的问题
	  	flContent.removeAllViews();
	    
	    //动态添加布局
	    flContent.addView(mview);
	    
	    //设置标题
	    tvTitle.setText("定位追踪");
	    
	  //显示电话号码的输入框
	  et_phone_number = (EditText) mview.findViewById(R.id.et_phone_number);
	  
	  //回显已保存的号码
	  et_phone_number.setText(phone);

	  //保存电话号码
		  bt_save_number = (Button) mview.findViewById(R.id.bt_save_number);
		  bt_save_number.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				phone =  et_phone_number.getText().toString();
				//检测号码是否为空
				if(!TextUtils.isEmpty(phone)){
					SpUtil.putString(mActivity, ConstantValue.CONTACT_PHONE, phone);
					ToastUtil.show(mActivity,"号码保存成功");
				}else{
					ToastUtil.show(mActivity,"请输入电话号码");
				}
				}
			});
	}

}
