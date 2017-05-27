package com.pokeman.reimuguard.fragmant;

import java.util.ArrayList;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.MainActivity;
import com.pokeman.reimuguard.base.BasePager;
import com.pokeman.reimuguard.base.implement.AntiVirusPager;
import com.pokeman.reimuguard.base.implement.AppLockPager;
import com.pokeman.reimuguard.base.implement.InfoPager;
import com.pokeman.reimuguard.base.implement.LocationPager;
import com.pokeman.reimuguard.base.implement.ProcessManagerPager;
import com.pokeman.reimuguard.view.NoScrollViewPager;

import android.R.raw;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 主页面fragment
 * 
 * @author pokeman
 * 
 */
public class ContentFragment extends BaseFragment {

	public NoScrollViewPager mViewPager;
	
	private RadioGroup rgGroup;

	// 标签页的集合
	private ArrayList<BasePager> mPagers;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_content);
		//设置缓存页数的数量
		mViewPager.setOffscreenPageLimit(4);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<BasePager>();

		// 添加标签页
		mPagers.add(new InfoPager(mActivity));
		mPagers.add(new AntiVirusPager(mActivity));
		mPagers.add(new ProcessManagerPager(mActivity));
		mPagers.add(new AppLockPager(mActivity));
		mPagers.add(new LocationPager(mActivity));
		

		mViewPager.setAdapter(new ContentAdapter());
		
		//底部菜单切换监听
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rb_device:
					// 首页
					// mViewPager.setCurrentItem(0);
					mViewPager.setCurrentItem(0, false);// 参2:表示是否具有滑动动画
					break;
				case R.id.rb_antivirus:
					// 防盗
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_process:
					// 进程管理
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_applock:
					// 黑名单
					mViewPager.setCurrentItem(3, false);
					break;
				case R.id.rb_theftproof:
					// 设置
					mViewPager.setCurrentItem(4, false);
					break;
				}
				
			}
		});
		
		//设置页面改变状态的监听
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}
    
	// 继承ViewPager提供的适配器
	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {

			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			BasePager pager = mPagers.get(position);

			// 获取当前页面对象的布局
			View view = pager.mRootView;

			// 初始化当前页面数据
			pager.initData();

			// 将当前页面对象的布局添加给容器
			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 删除页卡
			container.removeView((View) object);
		}

	}
    
	//通过leftfragment改变展示页
	public void setpager(int position) {
		
		//System.out.println("标签页改变测试");
		mViewPager.setCurrentItem(position);
	}

}
