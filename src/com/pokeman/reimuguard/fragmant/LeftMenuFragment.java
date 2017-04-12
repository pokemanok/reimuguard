package com.pokeman.reimuguard.fragmant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.MainActivity;
import com.pokeman.reimuguard.activity.base.BasePager;
import com.pokeman.reimuguard.activity.base.implement.AntiVirusPager;
import com.pokeman.reimuguard.activity.base.implement.HomePager;
import com.pokeman.reimuguard.activity.base.implement.ProcessManagerPager;
import com.pokeman.reimuguard.activity.base.implement.SettingPager;
import com.pokeman.reimuguard.activity.base.implement.TheftProofPager;
import com.pokeman.reimuguard.view.NoScrollViewPager;

import android.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ToggleButton;

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
	private String[] itemText = { "首页", "进程管理", "黑名单", "设置", "防盗" };

	private int[] itemIcon = { R.drawable.item_icon_0, R.drawable.item_icon_1,
			R.drawable.item_icon_2, R.drawable.item_icon_3,
			R.drawable.item_icon_4 };

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		mlistView = (ListView) view.findViewById(R.id.lv_list);
		return view;
	}

	@Override
	public void initDtata() {
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
	protected int getPosition(int position) {
		
	   return position;
	}

	// 控制侧边栏开关
	protected void toggle() {
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();

		// 侧边栏关闭状态时打开，打开状态时关闭
		slidingMenu.toggle();
	}

}
