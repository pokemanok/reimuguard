package com.pokeman.reimuguard.activity.base.implement;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pokeman.reimuguard.activity.base.BasePager;


/**
 * 黑名单页面
 * @author pokeman
 *
 */
public class AntiVirusPager extends BasePager {

	public AntiVirusPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		System.out.println("黑名单初始化");
		//要给帧布局填充对象
	    TextView view = new TextView(mActivity);
	    view.setText("黑名单");
	    view.setTextSize(22);
	    view.setGravity(Gravity.CENTER);
	    
	    //动态添加布局
	    flContent.addView(view);
	    
	    //设置标题
	    tvTitle.setText("黑名单");
	    
	    //隐藏菜单按钮
	    btnMenu.setVisibility(View.GONE);
	}

}
