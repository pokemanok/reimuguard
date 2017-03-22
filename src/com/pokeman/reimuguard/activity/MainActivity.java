package com.pokeman.reimuguard.activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pokeman.reimuguard.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class MainActivity extends SlidingFragmentActivity {
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

	}

}
