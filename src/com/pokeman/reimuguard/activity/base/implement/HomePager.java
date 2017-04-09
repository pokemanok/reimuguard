package com.pokeman.reimuguard.activity.base.implement;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Environment;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.base.BasePager;
import com.pokeman.reimuguard.info.DeviceInfo;
import com.pokeman.reimuguard.view.SelfStatistics;

/**
 * 首页
 * 
 * @author pokeman
 * 
 */
public class HomePager extends BasePager {

	private View mview;
	
	private SelfStatistics selfStatistics;
	
	float Space[]=new float[5];
	
	public HomePager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		System.out.println("首页初始化");

		mview = View.inflate(mActivity, R.layout.pager_home, null);
		
		Space = DeviceInfo.getSpace(mActivity);
		
		// 加入圆形统计图
		selfStatistics = (SelfStatistics) mview.findViewById(R.id.progress);
		//0 SD卡剩余可用空间，1 内置存储剩余可用空间，2 用户应用占用的空间，3 系统应用占用的空间,4 其他文件占用的空间
		float datas[] = new float[] {Space[0], Space[1], Space[2], Space[3],Space[4]};
		selfStatistics.setDatas(datas);
		selfStatistics.startDraw();

		// 动态添加布局
		flContent.addView(mview);

		// 设置标题
		tvTitle.setText("首页");

		// 隐藏菜单按钮
		btnMenu.setVisibility(View.GONE);
		
		Button button = (Button) mview.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//跳转到设备信息界面
				Activity currentActivity = (Activity) v.getContext();
				Intent intent = new Intent(currentActivity, DeviceInfo.class); 
				currentActivity.startActivity(intent);
							
			}
		});
			
	}

	   
		
		

}
