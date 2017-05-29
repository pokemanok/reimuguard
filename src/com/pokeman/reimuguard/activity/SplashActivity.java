package com.pokeman.reimuguard.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.pokeman.reimuguard.R;
import com.pokeman.reimuguard.R.layout;
import com.pokeman.reimuguard.service.MarisaWidgets;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;


public class SplashActivity extends Activity {

    private RelativeLayout rl_root;
	private Intent intent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        //开启桌面挂件
        //startService(new Intent(getApplicationContext(), MarisaWidgets.class));
        
        //初始化splash界面动画
        initAnimation();
        
        //初始化数据库
        initDB();
        
    }

	private void initDB() {
		
		//3,拷贝病毒数据库
		initAntiVirusDB("antivirus.db");
	}
	

	/**
	 * 拷贝数据库到files文件夹下
	 * @param dbName	数据库名称
	 */
	private void initAntiVirusDB(String dbName) {
		//1,在files文件夹下创建同名dbName数据库文件过程，路径/data/data//files
		File files = getFilesDir();
		File file = new File(files, dbName);
		if(file.exists()){
			return;
		}
		InputStream stream = null;
		FileOutputStream fos = null;
		//2,输入流读取assets目录下的文件
		try {
			stream = getAssets().open(dbName);
			//3,将读取的内容写入到指定文件夹的文件中去
			fos = new FileOutputStream(file);
			//4,每次的读取内容大小
			byte[] bs = new byte[1024];
			int temp = -1;
			//判断是否成功读取
			while( (temp = stream.read(bs))!=-1){
				//参数1 要被写入的缓冲区， 参数2 开始位置， 参数3写入数量
				fos.write(bs, 0, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(stream!=null && fos!=null){
				try {
					stream.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initAnimation() {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(3000);
		
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
		rl_root.startAnimation(alphaAnimation);
		
		//监控动画播放状态
		alphaAnimation.setAnimationListener(new AnimationListener(){
			@Override
			public void onAnimationEnd(Animation animation) {
				
				//跳转主界面
				intent = new Intent(getApplicationContext(),
						MainActivity.class);
				
				startActivity(intent);
				
				//结束activity，让用户无法回退
				finish();
			}

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
								
			}

		});
		
	

	}
}
