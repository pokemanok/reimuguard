package com.pokeman.reimuguard.activity.base.implement;

import android.R.string;
import android.app.Activity;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.base.BasePager;
import com.pokeman.reimuguard.info.GetDeviceInfo;
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
	
	float appSpace[]=new float[2];

	public HomePager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		System.out.println("首页初始化");

		mview = View.inflate(mActivity, R.layout.pager_home, null);
		
		appSpace = GetDeviceInfo.getAppSpace(mActivity);
		

		// 加入圆形统计图
		selfStatistics = (SelfStatistics) mview.findViewById(R.id.progress);
		//1 SD卡剩余可用空间，2 内置存储剩余可用空间，3 用户应用占用的空间，4 系统应用占用的空间
		float datas[] = new float[] {sd_freeSpace_float, rom_freeSpace_float, appSpace[0], appSpace[1] };
		selfStatistics.setDatas(datas);
		selfStatistics.startDraw();

		// 动态添加布局
		flContent.addView(mview);

		// 设置标题
		tvTitle.setText("首页");

		// 隐藏菜单按钮
		btnMenu.setVisibility(View.GONE);
	}

	    // 获取sd卡存储可用空间
		long sd_freeSpace_path = Environment.getExternalStorageDirectory().getFreeSpace();
		//String sd_freeSpace = Formatter.formatFileSize(mActivity,sd_freeSpace_path);
		
		
		//将byte转换成MB
		Float sd_freeSpace_float = (float) (sd_freeSpace_path/1048576);

	    // 获取内部存储可用空间
		long rom_freeSpace_path = Environment.getDataDirectory().getFreeSpace();
		//String rom_freeSpace = Formatter.formatFileSize(mActivity,rom_freeSpace_path);
		
		//将byte转换成MB
		Float rom_freeSpace_float = (float) (rom_freeSpace_path/1048576);
		
		

}
