package com.pokeman.reimuguard.activity.base.implement;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pokeman.reimuguard.activity.base.BasePager;


/**
 * 进程管理界面
 * @author pokeman
 *
 */
public class ProcessManagerPager extends BasePager {

	public ProcessManagerPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initData() {
		System.out.println("进程管理初始化");
		//要给帧布局填充对象
	    TextView view = new TextView(mActivity);
	    view.setText("进程管理");
	    view.setTextSize(22);
	    view.setGravity(Gravity.CENTER);
	    
	    //动态添加布局
	    flContent.addView(view);
	    
	    //设置标题
	    tvTitle.setText("进程管理");
	    
	    //隐藏菜单按钮
	    btnMenu.setVisibility(View.GONE);
	}

}
