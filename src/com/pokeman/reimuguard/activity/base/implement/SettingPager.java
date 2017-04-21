package com.pokeman.reimuguard.activity.base.implement;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.base.BasePager;


/**
 * 设定页面
 * @author pokeman
 *
 */
public class SettingPager extends BasePager {

	private View mview;

	public SettingPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		System.out.println("设定页面初始化");
		
		mview = View.inflate(mActivity, R.layout.pager_antivirus, null);
	    
	    //先清空布局再添加,避免切换选项卡的时候页面布局重复加载的问题
	  	flContent.removeAllViews();
	    
	    //动态添加布局
	    flContent.addView(mview);
	    
	    //设置标题
	    tvTitle.setText("设置页面");
 
	}

}
