package com.pokeman.reimuguard.activity.base.implement;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pokeman.reimuguard.activity.base.BasePager;


/**
 * 设定页面
 * @author pokeman
 *
 */
public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		System.out.println("设定页面初始化");
		//要给帧布局填充对象
	    TextView view = new TextView(mActivity);
	    view.setText("设置");
	    view.setTextSize(22);
	    view.setGravity(Gravity.CENTER);
	    
	    //先清空布局再添加,避免切换选项卡的时候页面布局重复加载的问题
	  	flContent.removeAllViews();
	    
	    //动态添加布局
	    flContent.addView(view);
	    
	    //设置标题
	    tvTitle.setText("设置页面");
	    
	   //隐藏菜单按钮
	    btnMenu.setVisibility(View.GONE);
	}

}
