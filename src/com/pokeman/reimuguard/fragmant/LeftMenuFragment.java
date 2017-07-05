package com.pokeman.reimuguard.fragmant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.MainActivity;
import com.pokeman.reimuguard.base.BasePager;
import com.pokeman.reimuguard.service.MarisaWidgets;
import com.pokeman.reimuguard.utils.ServiceUtil;
import com.pokeman.reimuguard.view.NoScrollViewPager;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 左侧菜单fragment
 * 
 * @author pokeman
 * 
 */
public class LeftMenuFragment extends BaseFragment {

	private ListView mlistView;

	private NoScrollViewPager mViewPager;
	

	
	// 标签页的集合
	private ArrayList<BasePager> mPagers;

	// listview的数据
	private String[] itemText = { "设备", "杀毒", "进程", "应用锁", "定位","挂件"};

	private int[] itemIcon = { R.drawable.device, R.drawable.antivirus,
			R.drawable.process, R.drawable.lock,
			R.drawable.location,R.drawable.widgets};

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		mlistView = (ListView) view.findViewById(R.id.lv_list);
		return view;
	}

	@Override
	public void initData() {
		// 初始化ListView数据
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < itemText.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("itemText", itemText[i]);
			listItem.put("itemIcon", itemIcon[i]);
			listItems.add(listItem);
		}

		// 给listview设置适配器
		SimpleAdapter simplead = new SimpleAdapter(mActivity, listItems,
				R.layout.list_item_left_menu, new String[] { "itemText",
						"itemIcon" },
				new int[] { R.id.itemText, R.id.itemIcon });

		mlistView.setAdapter(simplead);
		
		// 给listview设置点击事件监听
		mlistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				getPosition(position);
				
				// 收起侧边栏
				toggle();
			}

		});
	} 
    
	//获取点击位置
	protected void getPosition(int position) {
		
		//判断挂件服务是否开启
		boolean isRunning = ServiceUtil.isRunning(mActivity, "com.pokeman.reimuguard.service.MarisaWidgets");
		
		// 获取Activity
		MainActivity mainUI = (MainActivity) mActivity;
		// 获取ContentFragment
		ContentFragment fragment = mainUI.getContentFragment();
		
		//如果条目位置小于5则切换显示页面，大于5则控制挂件开启
		if(position<5){
			fragment.setpager(position);
		}else{
			if(isRunning){
				//停止挂件服务
				mActivity.stopService(new Intent(mActivity, MarisaWidgets.class));
			}else{
				//开启挂件服务
			    mActivity.startService(new Intent(mActivity, MarisaWidgets.class));
			}
		}
		
		
	}

	// 控制侧边栏开关
	protected void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();

		// 侧边栏关闭状态时打开，打开状态时关闭
		slidingMenu.toggle();
	}

}
