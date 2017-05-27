package com.pokeman.reimuguard.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.pokeman.reimuguard.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * 获取GPU相关信息
 * @author pokeman
 *
 */
public class DeviceInfo extends Activity implements GLSurfaceView.Renderer{
    private TextView textView;
    private GLSurfaceView glSurfaceView;
    private StringBuilder sb;
    public String str;
	private ConfigurationInfo configurationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gl_rl_layout);

        textView = (TextView) findViewById(R.id.textView1);

        final ActivityManager activityManager =  (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        configurationInfo = activityManager
                .getDeviceConfigurationInfo();
        sb=new StringBuilder();
        
        textView.setText(sb.toString());
        this.glSurfaceView = new GLSurfaceView(this);
        this.glSurfaceView.setRenderer(this);
        ((ViewGroup)textView.getParent()).addView(this.glSurfaceView);
    }

    public void onClick(View v) {
    }
    
    //获取GPU相关信息
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    	sb.append("手机品牌:").append(android.os.Build.BRAND).append("\n");
    	sb.append("手机型号:").append(android.os.Build.MODEL).append("\n");
    	sb.append("系统版本:").append(android.os.Build.VERSION.RELEASE).append("\n");
    	sb.append("系统名称:").append(android.os.Build.DISPLAY).append("\n");
    	sb.append("CPU型号:").append(android.os.Build.HARDWARE).append("\n");
    	sb.append("CPU类型:").append(android.os.Build.CPU_ABI).append("\n");
        sb.append("GPU型号:").append(gl.glGetString(GL10.GL_RENDERER)).append("\n");
        sb.append("GPU制造商:").append( gl.glGetString(GL10.GL_VENDOR)).append("\n");
        sb.append("OpenGL版本:").append(configurationInfo.getGlEsVersion()).append("\n");
        sb.append("OpenGL ES版本:").append(gl.glGetString(GL10.GL_VERSION)).append("\n");
        //sb.append("EXTENSIONS").append(gl.glGetString(GL10.GL_EXTENSIONS));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(sb.toString());
                glSurfaceView.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
    }

    @Override
    public void onDrawFrame(GL10 gl) {
    }
    
    
    /**
  	 * 获得各种存储信息
  	 * 
  	 * @return
  	 */
  	public static float[] getSpace(Context context){
  		long appSpaceSys = 0;
  		long appSpaceUser = 0;
  		long sd_freeSpace_path;
  		long rom_freeSpace_path;
  		long total_space;
  		long other_space;
  		float Space[]=new float[5]; //保存存储信息的数组
  		
  		//获取外部存储可用空间
  		sd_freeSpace_path = Environment.getExternalStorageDirectory().getFreeSpace();
 		
 		//将byte转换成MB
 		Float sd_freeSpace_float = (float) (sd_freeSpace_path/1048576);
 		Space[0] = sd_freeSpace_float;

 	    // 获取内部存储可用空间
 		rom_freeSpace_path = Environment.getDataDirectory().getFreeSpace();
 		
 		//将byte转换成MB
 		Float rom_freeSpace_float = (float) (rom_freeSpace_path/1048576);
 		Space[1] = rom_freeSpace_float;
  		
 		//获取系统应用和用户应用占用的空间
  		PackageManager pm = context.getPackageManager();
  		List<PackageInfo> packInfos = pm.getInstalledPackages(0);
  		for(PackageInfo packInfo:packInfos){
  			//得到apk的路径
  			String apkpath = packInfo.applicationInfo.sourceDir;
  			File file = new File(apkpath);
  			long appSize = file.length();
  			
  			if(apkpath.startsWith("/system/")){
  				//系统应用
  				appSpaceSys += appSize;
  			}else {
  				//用户应用
  				appSpaceUser += appSize; 
  			}
  		}
  		
  		//计算总的占用空间
  		Space[2] = (float) (appSpaceUser/1048576);
  		Space[3] = (float) (appSpaceSys/1048576);
  		
  		//获取SD卡+内置存储的总容量
  		total_space = Environment.getExternalStorageDirectory().getTotalSpace()+
  		Environment.getDataDirectory().getTotalSpace();
  		
  		//获得其他文件占用的空间
  		other_space = total_space - (sd_freeSpace_path+rom_freeSpace_path+appSpaceUser+appSpaceSys);
  		Space[4] = (float) (other_space/1048576);
  		
  		return Space;		
  	}
  		
}
