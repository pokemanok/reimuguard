package com.pokeman.reimuguard.fragmant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	public FragmentActivity mActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 获取当前所依赖的activity
		mActivity = getActivity();
	}
	
	//初始化fragment布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View initView = initView(); 
		return initView;
	}
	
	//fragment依赖的activity的onCreate方法执行结束
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//初始化数据
		initDtata();
	}
	
	//初始化布局，由子类实现
	public abstract View initView();
	
	//初始化数据，由子类实现
	public abstract void initDtata();

}
