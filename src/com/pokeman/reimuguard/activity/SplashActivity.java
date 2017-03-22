package com.pokeman.reimuguard.activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.R.layout;
import com.pokeman.reimuguard.service.MarisaWidgets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;


public class SplashActivity extends Activity {

    private RelativeLayout rl_root;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        //开启桌面挂件
        startService(new Intent(getApplicationContext(), MarisaWidgets.class));
        
        //初始化splash界面动画
        initAnimation();
        
    }

	private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(3000);
		
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
		rl_root.startAnimation(alphaAnimation);
		
		//监控动画播放状态
		alphaAnimation.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				
				//跳转主界面
				intent = new Intent(getApplicationContext(),
						MainActivity.class);
				
				startActivity(intent);
				
				//结束activity，让用户无法回退
				finish();
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
								
			}

		});
		
	

	}
}
