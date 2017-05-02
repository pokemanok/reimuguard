package com.pokeman.reimuguard.base.implement;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.base.BasePager;
import com.pokeman.reimuguard.dao.AppLockDao;
import com.pokeman.reimuguard.info.AppInfo;
import com.pokeman.reimuguard.service.LockAppService;
import com.pokeman.reimuguard.utils.AppInfoProvider;
import com.pokeman.reimuguard.utils.ConstantValue;
import com.pokeman.reimuguard.utils.Md5Util;
import com.pokeman.reimuguard.utils.SpUtil;
import com.pokeman.reimuguard.utils.ToastUtil;



/**
 * 防盗页面
 * @author pokeman
 *
 */
/**
 * @author pokeman
 *
 */
/**
 * @author pokeman
 *
 */
/**
 * @author pokeman
 *
 */
public class AppLockPager extends BasePager {

	private View mview;
	private Button bt_unlock,bt_lock;
	private LinearLayout ll_unlock,ll_lock;
	private TextView tv_unlock,tv_lock;
	private ListView lv_unlock,lv_lock;
	private List<AppInfo> mAppInfoList;
	private List<AppInfo> mLockList;
	private List<AppInfo> mUnLockList;
	private AppLockDao mDao;
	private MyAdapter mLockAdapter;
	private MyAdapter mUnLockAdapter;
	private TranslateAnimation mTranslateAnimation;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//6.接收到消息,填充已加锁和未加锁的数据适配器
			mLockAdapter = new MyAdapter(true);
			lv_lock.setAdapter(mLockAdapter);
			
			mUnLockAdapter = new MyAdapter(false);
			lv_unlock.setAdapter(mUnLockAdapter);
		};
	};
	
	@Override
	public void initData() {
		System.out.println("应用锁页面初始化");
		
		mview = View.inflate(mActivity, R.layout.pager_applock, null);
		
	    //先清空布局再添加,避免切换选项卡的时候页面布局重复加载的问题
	  	flContent.removeAllViews();
	    
	    //动态添加布局
	    flContent.addView(mview);
	    
	    //防盗页面
	    tvTitle.setText("应用锁");
	    
	    initUI();
		initList();
		initAnimation();
		showDialog();
		
		//开启服务
		mActivity.startService(new Intent(mActivity, LockAppService.class));

	}

	
	/**
	 * 判断是否已设置安全密码
	 */
	protected void showDialog() {
		//判断本地是否有存储密码(sp	字符串)
		String psd = SpUtil.getString(mActivity, ConstantValue.MOBILE_SAFE_PSD, "");
		if(TextUtils.isEmpty(psd)){
			//1,初始设置密码对话框
			showSetPsdDialog();
		}
	}
	
	/**
	 * 设置密码
	 */
	private void showSetPsdDialog() {
		//因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
				//view是由自己编写的xml转换成的view对象xml----->view
				Builder builder = new AlertDialog.Builder(mActivity);
				final AlertDialog dialog = builder.create();
				
				final View view = View.inflate(mActivity, R.layout.dialog_set_psd, null);
				//让对话框显示一个自己定义的对话框界面效果
//				dialog.setView(view);
				
				//为了兼容低版本,给对话框设置布局的时候,让其没有内边距(android系统默认提供出来的)
				dialog.setView(view, 0, 0, 0, 0);
				dialog.show();
				
				Button bt_submit = (Button) view.findViewById(R.id.bt_submit_psd);
				Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel_psd);
				
				//按下确认按钮保存密码
				bt_submit.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
						EditText et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);
						
						String psd = et_set_psd.getText().toString();
						String confirmPsd = et_confirm_psd.getText().toString();
						
						if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)){
							if(psd.equals(confirmPsd)){
								
								//跳转到新的界面以后需要去隐藏对话框
								dialog.dismiss();
								
								//保存的时候对密码进行md5编码
								SpUtil.putString(mActivity, 
										ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(confirmPsd));
							}else{
								ToastUtil.show(mActivity,"确认密码错误");
							}
						}else{
							//提示用户密码输入有为空的情况
							ToastUtil.show(mActivity, "请输入密码");
						}
					}
				});
				
				bt_cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	}
	
	class MyAdapter extends BaseAdapter{
		private boolean isLock;
		/**
		 * @param isLock	用于区分已加锁和未加锁应用的标示	true已加锁数据适配器	false未加锁数据适配器
		 */
		public MyAdapter(boolean isLock) {
			this.isLock = isLock;
		}
		@Override
		public int getCount() {
			if(isLock){
				tv_lock.setText("已加锁应用:"+mLockList.size());
				return mLockList.size();
			}else{
				tv_unlock.setText("未加锁应用:"+mUnLockList.size());
				return mUnLockList.size();
			}
		}

		@Override
		public AppInfo getItem(int position) {
			if(isLock){
				return mLockList.get(position);
			}else{
				return mUnLockList.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.listview_islock_item, null);
				holder = new ViewHolder();
				holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
				holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
				holder.iv_lock = (ImageView) convertView.findViewById(R.id.iv_lock);
				
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			final AppInfo appInfo = getItem(position);
			final View animationView = convertView;
			
			holder.iv_icon.setBackgroundDrawable(appInfo.icon);
			holder.tv_name.setText(appInfo.name);
			if(isLock){
				holder.iv_lock.setBackgroundResource(R.drawable.lock);
			}else{
				holder.iv_lock.setBackgroundResource(R.drawable.unlock);
			}
			holder.iv_lock.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//添加动画效果,动画默认是非阻塞的,所以执行动画的同时,动画以下的代码也会执行
					animationView.startAnimation(mTranslateAnimation);//500毫秒
					//对动画执行过程做事件监听,监听到动画执行完成后,再去移除集合中的数据,操作数据库,刷新界面
					mTranslateAnimation.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
							//动画开始的是调用方法
						}
						@Override
						public void onAnimationRepeat(Animation animation) {
							//动画重复时候调用方法
						}
						//动画执行结束后调用方法
						@Override
						public void onAnimationEnd(Animation animation) {
							if(isLock){
								//已加锁------>未加锁过程
								//1.已加锁集合删除一个,未加锁集合添加一个,对象就是getItem方法获取的对象
								mLockList.remove(appInfo);
								mUnLockList.add(appInfo);
								//2.从已加锁的数据库中删除一条数据
								mDao.delete(appInfo.packageName);
							}else{
								//未加锁------>已加锁过程
								//1.已加锁集合添加一个,未加锁集合移除一个,对象就是getItem方法获取的对象
								mLockList.add(appInfo);
								mUnLockList.remove(appInfo);
								//2.从已加锁的数据库中插入一条数据
								mDao.insert(appInfo.packageName);
							}
							//3.刷新数据适配器
							mLockAdapter.notifyDataSetChanged();
							//3.刷新数据适配器
							mUnLockAdapter.notifyDataSetChanged();
						}
					});
				}
			});
			return convertView;
		}
	}
	
	static class ViewHolder{
		ImageView iv_icon;
		TextView tv_name;
		ImageView iv_lock;
	}
	

	public AppLockPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
		
	/**
	 * 初始化平移动画的方法(平移自身的一个宽度大小)
	 */
	private void initAnimation() {
		mTranslateAnimation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0, 
				Animation.RELATIVE_TO_SELF, 1, 
				Animation.RELATIVE_TO_SELF, 0, 
				Animation.RELATIVE_TO_SELF, 0);
		mTranslateAnimation.setDuration(500);
	}
	
	/**
	 * 区分已加锁和未加锁应用的集合
	 */
	private void initList() {
		new Thread(){
			public void run() {
				//1.获取所有手机中的应用
				mAppInfoList = AppInfoProvider.getAppInfoList(mActivity);
				//2.区分已加锁应用和未加锁应用
				mLockList = new ArrayList<AppInfo>();
				mUnLockList = new ArrayList<AppInfo>();
				
				//3.获取数据库中已加锁应用包名的的结合
				mDao = AppLockDao.getInstance(mActivity);
				List<String> lockPackageList = mDao.findAll();
				
				for (AppInfo appInfo : mAppInfoList) {
					//4,如果循环到的应用的包名,在数据库中,则说明是已加锁应用
					if(lockPackageList.contains(appInfo.packageName)){
						mLockList.add(appInfo);
					}else{
						mUnLockList.add(appInfo);
					}
				}
				//5.告知主线程,可以使用维护的数据
				mHandler.sendEmptyMessage(0);
			};
		}.start();
	}
	
	private void initUI() {
		bt_unlock = (Button) mview.findViewById(R.id.bt_unlock);
		bt_lock = (Button) mview.findViewById(R.id.bt_lock);
		
		ll_unlock = (LinearLayout) mview.findViewById(R.id.ll_unlock);
		ll_lock = (LinearLayout) mview.findViewById(R.id.ll_lock);
		
		tv_unlock = (TextView) mview.findViewById(R.id.tv_unlock);
		tv_lock = (TextView) mview.findViewById(R.id.tv_lock);
		
		lv_unlock = (ListView) mview.findViewById(R.id.lv_unlock);
		lv_lock = (ListView) mview.findViewById(R.id.lv_lock);
		
		bt_unlock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.已加锁列表隐藏,未加锁列表显示
				ll_lock.setVisibility(View.GONE);
				ll_unlock.setVisibility(View.VISIBLE);
				//2.未加锁变成深色图片,已加锁变成浅色图片
				bt_unlock.setBackgroundResource(R.drawable.tab_left_pressed);
				bt_lock.setBackgroundResource(R.drawable.tab_right_default);
			}
		});
		
		bt_lock.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1.已加锁列表显示,未加锁列表隐藏
				ll_lock.setVisibility(View.VISIBLE);
				ll_unlock.setVisibility(View.GONE);
				//2.未加锁变成浅色图片,已加锁变成深色图片
				bt_unlock.setBackgroundResource(R.drawable.tab_left_default);
				bt_lock.setBackgroundResource(R.drawable.tab_right_pressed);
			}
		});
	}

}
