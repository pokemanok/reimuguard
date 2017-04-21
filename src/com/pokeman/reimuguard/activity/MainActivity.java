package com.pokeman.reimuguard.activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.fragmant.ContentFragment;
import com.pokeman.reimuguard.fragmant.LeftMenuFragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.DisplayMetrics;


/**
 * 主页面
 * @author pokeman
 *
 */
public class MainActivity extends SlidingFragmentActivity {
	

	private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
	private static final String TAG_CONTENT = "TAG_CONTENT";;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_main);

        //设置侧边栏
        setBehindContentView(R.layout.left_menu);
        
        //设置右侧侧边栏
        SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		//设置全屏触摸
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        
        int screenWidth = dm.widthPixels;
        
        //设置侧边栏宽度
		slidingMenu.setBehindOffset(screenWidth/2);
		
		initFragment();

	}
	
	//初始化fragment
	private void initFragment() {
		
		//获取fragment管理器
		android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
		
		//开启事务
		android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
		
		
		//用fragment替换帧布局
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),TAG_LEFT_MENU);
		transaction.replace(R.id.fl_main, new ContentFragment(),TAG_CONTENT);
		
		//提交事务
		transaction.commit();
		
	}
	
	     // 获取侧边栏fragment对象
		public LeftMenuFragment getLeftMenuFragment() {
			android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
			LeftMenuFragment fragment = (LeftMenuFragment) fm
					.findFragmentByTag(TAG_LEFT_MENU);// 根据标记找到对应的fragment
			return fragment;
		}

		// 获取主页fragment对象
		public ContentFragment getContentFragment() {
			android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
			ContentFragment fragment = (ContentFragment) fm
					.findFragmentByTag(TAG_CONTENT);// 根据标记找到对应的fragment
			return fragment;
		}
}
