package com.pokeman.reimuguard.service;

import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.MainActivity;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class MarisaWidgets extends Service {
	private WindowManager mWM;
	private int mScreenHeight;
	private int mScreenWidth;
	private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
	private View mWidgetsView;


	@Override
	public void onCreate() {
		
		//获取窗体对象
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);
		
		mScreenHeight = mWM.getDefaultDisplay().getHeight();
		mScreenWidth = mWM.getDefaultDisplay().getWidth();
		
		//开启挂件
		showWidgets();
		super.onCreate();

	}

	private void showWidgets() {
		//自定义挂件(使用吐司)
	    final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	默认能够被触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        //位于所有程序之上
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        
        //挂件初始位置
        params.gravity = Gravity.RIGHT;
        
        mWidgetsView = View.inflate(this, R.layout.widgets_view, null);
        mWM.addView(mWidgetsView, params);
        Log.d("logtest", "wtf???");
        
        ImageView iv_widgets = (ImageView) mWidgetsView.findViewById(R.id.iv_widgets);
        iv_widgets.setOnTouchListener(new OnTouchListener() {
        	private int startX;
			private int startY;
			private long currentMS;
			private int moveX;
			private int moveY;
			private Intent intent;
			private int disX;
			private int disY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					//获取按下的xy坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					currentMS = System.currentTimeMillis();//long currentMS     获取系统时间  
					break;
				case MotionEvent.ACTION_MOVE:
					//第一次移动到的位置,作为第二次移动的初始位置
					moveX = (int) event.getRawX();
					moveY = (int) event.getRawY();
					
					disX = startX - moveX;
					disY = moveY - startY;
					
					params.x = params.x+disX;
					params.y = params.y+disY;
				
					//告知吐司在窗体上刷新
					mWM.updateViewLayout(mWidgetsView, params);
					
					//在第一次移动完成后,将最终坐标作为第二次移动的起始坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:
					 long moveTime = System.currentTimeMillis() - currentMS;//移动时间  
					       //当点击时间小于100毫秒与移动距离为0的时候判断为点击事件
		                	if(moveTime<100&&(disX+disY)==0){
		                		System.out.print(disX+"malebazi"+disY);
		                		//跳转主界面
			    				intent = new Intent(getApplicationContext(),
			    						MainActivity.class);
			    				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			    				startActivity(intent);
		                	}
					break;
				}
				return true;
			}
		});
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	//服务结束时窗体对象移除视图
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(mWM!=null && mWidgetsView!=null){
			mWM.removeView(mWidgetsView);
		}
		super.onDestroy();
	}

}
