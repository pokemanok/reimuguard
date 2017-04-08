package com.pokeman.reimuguard.info;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;



public class GetDeviceInfo {
	
	
	//得到app占用的内置存储空间和外置存储空间的大小
	public static float[] getAppSpace(Context context){
		long appSpaceSys = 0;
		long appSpaceUser = 0;
		float appSpace[]=new float[2];
		
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
		appSpace[0] = (float) (appSpaceUser/1048576);
		appSpace[1] = (float) (appSpaceSys/1048576);
		
		return appSpace;
		
	}


}
