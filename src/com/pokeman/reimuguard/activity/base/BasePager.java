package com.pokeman.reimuguard.activity.base;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.MainActivity;

import android.R.raw;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 五个标签页的基类
 * 
 * @author pokeman
 * 
 */
public class BasePager {

	public Activity mActivity;

	public TextView tvTitle;
	public ImageButton btnMenu;

	// 空的帧布局对象，需要动态添加布局
	public FrameLayout flContent;

	// 当前页面的布局对象
	public View mRootView;

	public BasePager(Activity activity) {
		mActivity = activity;

		mRootView = initView();
	}

	// 初始化布局
	public View initView() {
		View view = View.inflate(mActivity, R.layout.base_pager, null);

		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		btnMenu = (ImageButton) view.findViewById(R.id.btn_menu);
		flContent = (FrameLayout) view.findViewById(R.id.fl_content);

		btnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle();
			}
		});

		return view;
	}

	// 初始化数据
	public void initData() {

	}

	// 控制侧边栏开关
	protected void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();

		// 侧边栏关闭状态时打开，打开状态时关闭
		slidingMenu.toggle();
	}

}
