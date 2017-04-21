package com.pokeman.reimuguard.activity.base.implement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
public class InfoPager extends BasePager {

	private View mview;
	private SelfStatistics selfStatistics;
	private ListView mlistView;
	float Space[] = new float[5];
	
	public InfoPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initData() {
		System.out.println("信息初始化");

		mview = View.inflate(mActivity, R.layout.pager_home, null);
		
		//先清空布局再添加,避免切换选项卡的时候页面布局重复加载的问题
		flContent.removeAllViews();

		Space = DeviceInfo.getSpace(mActivity);

		// 加入圆形统计图
		selfStatistics = (SelfStatistics) mview.findViewById(R.id.progress);
		// 0 SD卡剩余空间，1 内置存储剩余空间，2 用户应用占用空间，3 系统应用占用空间,4 其他文件占用空间
		float datas[] = new float[] {Space[0], Space[1], Space[2], Space[3],Space[4]};
		
		selfStatistics.setDatas(datas);
		selfStatistics.startDraw();
		
		

		// 动态添加布局
		flContent.addView(mview);

		// 设置标题
		tvTitle.setText("设备信息");

		Button button = (Button) mview.findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 跳转到设备信息界面
				Activity currentActivity = (Activity) v.getContext();
				Intent intent = new Intent(currentActivity, DeviceInfo.class);
				currentActivity.startActivity(intent);

			}
		});

		// 加入listview
		mlistView = (ListView) mview.findViewById(R.id.storagelistView);

		// 初始化ListView数据
		String[] itemText = { "SD卡剩余空间:" + Space[0] + "MB",
                "内置存储剩余空间:" + Space[1] + "MB", 
                "用户应用占用空间:" + Space[2] + "MB",
                "系统应用占用空间:" + Space[3] + "MB", 
                "其他文件占用空间:" + Space[4] + "MB" };
		
		int[] itemIcon = { R.drawable.dot1, R.drawable.dot2,
				R.drawable.dot3, R.drawable.dot4, R.drawable.dot5 };
		
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < itemText.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("itemText", itemText[i]);
			listItem.put("itemIcon", itemIcon[i]);
			listItems.add(listItem);
		}

		// 给listview设置适配器
		SimpleAdapter simplead = new SimpleAdapter(mActivity, listItems,
				R.layout.list_item_storage, new String[] { "itemText",
						"itemIcon" },
				new int[] { R.id.itemTextStorage, R.id.itemIconStorage });

		mlistView.setAdapter(simplead);

	}

}
