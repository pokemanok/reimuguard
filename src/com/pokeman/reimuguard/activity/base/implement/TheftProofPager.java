package com.pokeman.reimuguard.activity.base.implement;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pokeman.reimuguard.activity.base.BasePager;


/**
 * 防盗页面
 * @author pokeman
 *
 */
public class TheftProofPager extends BasePager {

	public TheftProofPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		System.out.println("防盗页面初始化");
		//要给帧布局填充对象
	    TextView view = new TextView(mActivity);
	    view.setText("防盗");
	    view.setTextSize(22);
	    view.setGravity(Gravity.CENTER);
	    
	    //动态添加布局
	    flContent.addView(view);
	    
	    //防盗页面
	    tvTitle.setText("防盗页面");
	    
	    //显示按钮
	    btnMenu.setVisibility(View.VISIBLE);
	}

}
