package com.pokeman.reimuguard.activity.base.implement;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.activity.base.BasePager;
import com.pokeman.reimuguard.dao.VirusDao;
import com.pokeman.reimuguard.utils.Md5Util;


/**
 * 杀毒界面
 * @author pokeman
 *
 */
public class AntiVirusPager extends BasePager {

	protected static final int SCANING = 100;
	protected static final int SCAN_FINSH = 101;
	private View mview;
	private TextView tv_name;
	private ImageView iv_scanning;
	private ProgressBar pb_bar;
	private LinearLayout ll_add_text;
	private AnimationDrawable anim;
	private int index = 0;
	private List<ScanInfo> mVirusScanInfoList;
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SCANING:
				//1.显示正在扫描应用的名称
				ScanInfo info = (ScanInfo)msg.obj;
				tv_name.setText(info.name);
				//2.在线性布局中添加一个正在扫描应用的textview
				TextView textView = new TextView(mActivity);
				if(info.isVirus){
					//是病毒
					textView.setTextColor(Color.RED);
					textView.setText("发现病毒:"+info.name);
				}else{
					//不是病毒
					textView.setTextColor(Color.BLACK);
					textView.setText("扫描安全:"+info.name);
				}
				ll_add_text.addView(textView,0);
				break;
            case SCAN_FINSH:
            	tv_name.setText("扫描完成");
            	
            	//停止动画
            	anim.stop();
            	
            	//告知用户卸载包含了病毒的应用
            	unInstallVirus();
				break;

			default:
				break;
			}
		};
	};

	public AntiVirusPager(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}
	
	protected void unInstallVirus() {
		for (ScanInfo scanInfo:mVirusScanInfoList) {
			String packageName = scanInfo.packageName;
			
			//卸载应用
			Intent intent = new Intent("android.intent.action.DELETE");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setData(Uri.parse("package:"+packageName));
			mActivity.startActivity(intent);
		}
		
	}

	@Override
	public void initData() {
		mview = View.inflate(mActivity, R.layout.pager_antivirus, null);
	    
		//先清空布局再添加,避免切换选项卡的时候页面布局重复加载的问题
		flContent.removeAllViews();
		
	    //动态添加布局
	    flContent.addView(mview);
	    
	    //设置标题
	    tvTitle.setText("手机杀毒");
	    
	    //隐藏菜单按钮
	    btnMenu.setVisibility(View.GONE);
	    
	    iv_scanning = (ImageView) mview.findViewById(R.id.iv_scanning);
		tv_name = (TextView) mview.findViewById(R.id.tv_name);
		pb_bar = (ProgressBar) mview.findViewById(R.id.pb_bar);
		ll_add_text = (LinearLayout) mview.findViewById(R.id.ll_add_text);
		
		//触摸萃香开始扫描
		iv_scanning.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//执行扫描
				checkVirus();
				//初始化扫描动画
				initAnimation();
			}
		});	
	}

	private void checkVirus() {
		new Thread(){
			

			public void run() {
				//获取数据库中所有的病毒的md5码
				List<String> virusList = VirusDao.getVirusList();
				//获取手机上的所有应用程序签名文件的md5码
				//1.获取包管理对象
				PackageManager pm = mActivity.getPackageManager();
				//2.获取所有应用程序签名文件 (PackageManager.GET_SIGNATURES 已安装应用的签名文件)
				//PackageManager.GET_UNINSTALLED_PACKAGES 卸载了的应用的残余文件
				List<PackageInfo> packageInfoList = pm.getInstalledPackages(
						PackageManager.GET_SIGNATURES + PackageManager.GET_UNINSTALLED_PACKAGES);
				mVirusScanInfoList = new ArrayList<ScanInfo>();
				//记录所有应用的集合
				List<ScanInfo> scanInfoList = new ArrayList<ScanInfo>();
				
				//设置进度条的最大值
				pb_bar.setMax(packageInfoList.size());
				
				//3.遍历应用集合
				for (PackageInfo packageInfo : packageInfoList) {
					ScanInfo scanInfo = new ScanInfo();
					//获取签名文件的数组
					Signature[] signatures = packageInfo.signatures;
					//获取签名文件数组的第一位进行md5编码，然后与数据库中的md5比对
					Signature signature = signatures[0];
					String str = signature.toCharsString();
					//32位字符串，16进制字符(0-f)
					String encoder = Md5Util.encoder(str);
					//4.通过比对判断应用是否为病毒
					
					if(virusList.contains(encoder)){
						//5.记录病毒
						scanInfo.isVirus = true;
						//将病毒加入集合
						mVirusScanInfoList.add(scanInfo);
					}else {
						scanInfo.isVirus = false;
					}
					//维护对象的包名和应用名称
					scanInfo.packageName = packageInfo.packageName;
					scanInfo.name = packageInfo.applicationInfo.loadLabel(pm).toString();
					scanInfoList.add(scanInfo);
					
					//7.在扫描的过程中更新进度条
					index++;
					pb_bar.setProgress(index);
					
					//让线程睡眠使扫描不会瞬间完成
					try {
						Thread.sleep(60);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//8.在子线程中发送消息通知主线程更新UI(1 顶部扫描的应用名称 2 扫描过程中往线性布局中添加view)
					Message msg = Message.obtain();
					msg.what = SCANING;
					msg.obj = scanInfo;
					mHandler.sendMessage(msg);
				}
				Message msg = Message.obtain();
				msg.what = SCAN_FINSH;
				mHandler.sendMessage(msg);
				
				//重置扫描进度条
				index = 0;
			};
		}.start();	
	}
	
	class ScanInfo{
		public boolean isVirus;
		public String packageName;
		public String name;
	}

	private void initAnimation() {
		anim = (AnimationDrawable) iv_scanning.getBackground();
		anim.start();
	}

}
