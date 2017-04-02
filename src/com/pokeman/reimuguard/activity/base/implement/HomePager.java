package com.pokeman.reimuguard.activity.base.implement;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.base.BasePager;


/**
 * 首页
 * @author pokeman
 *
 */
public class HomePager extends BasePager {

	private View mview;

	public HomePager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		System.out.println("首页初始化");
		
         mview = View.inflate(mActivity, R.layout.pager_home, null);
	    //动态添加布局
	    flContent.addView(mview);
	    
	    //设置标题
	    tvTitle.setText("首页");
	    
	    //隐藏菜单按钮
	    btnMenu.setVisibility(View.GONE);
	}

}
