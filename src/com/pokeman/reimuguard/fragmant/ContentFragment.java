package com.pokeman.reimuguard.fragmant;

import java.util.ArrayList;

import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.base.BasePager;
import com.pokeman.reimuguard.activity.base.implement.BlackListPager;
import com.pokeman.reimuguard.activity.base.implement.HomePager;
import com.pokeman.reimuguard.activity.base.implement.ProcessManagerPager;
import com.pokeman.reimuguard.activity.base.implement.SettingPager;
import com.pokeman.reimuguard.activity.base.implement.TheftProofPager;
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

	private NoScrollViewPager mViewPager;
	
	private RadioGroup rgGroup;

	// 标签页的集合
	private ArrayList<BasePager> mPagers;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		mViewPager = (NoScrollViewPager) view.findViewById(R.id.vp_content);
		rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);
		return view;
	}

	@Override
	public void initDtata() {
		mPagers = new ArrayList<BasePager>();

		// 添加标签页
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new TheftProofPager(mActivity));
		mPagers.add(new ProcessManagerPager(mActivity));
		mPagers.add(new BlackListPager(mActivity));
		mPagers.add(new SettingPager(mActivity));
		

		mViewPager.setAdapter(new ContentAdapter());
		
		//底部菜单切换监听
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch(checkedId){
				case R.id.rb_home:
					// 首页
					// mViewPager.setCurrentItem(0);
					mViewPager.setCurrentItem(0, false);// 参2:表示是否具有滑动动画
					break;
				case R.id.rb_theftproof:
					// 防盗
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_process:
					// 进程管理
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_blacklist:
					// 黑名单
					mViewPager.setCurrentItem(3, false);
					break;
				case R.id.rb_setting:
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
				if(position==0 || position==mPagers.size()-1){
					//首页和设置页禁用侧边栏
					setSlidingMenuEnable(false);
				}else{
					setSlidingMenuEnable(true);
				}
				
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
    
	//开启或禁用侧边栏
	protected void setSlidingMenuEnable(boolean anable) {
		//获取侧边栏对象
		
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

}
