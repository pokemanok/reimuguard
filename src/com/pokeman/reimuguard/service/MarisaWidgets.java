package com.pokeman.reimuguard.service;

import com.pokeman.reimuguard.R;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

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
        //在响铃的时候显示吐司,和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");
        
        //挂件初始位置
        params.gravity = Gravity.LEFT;
        
        mWidgetsView = View.inflate(this, R.layout.widgets_view, null);
        mWM.addView(mWidgetsView, params);
        Log.d("nimabi", "骂了隔壁？");
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
	
		super.onDestroy();
	}

}
