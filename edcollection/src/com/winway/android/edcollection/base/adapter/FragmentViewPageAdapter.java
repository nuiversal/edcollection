package com.winway.android.edcollection.base.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * 分页控件适配器
 * 
 * @author zgq
 *
 */
public class FragmentViewPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	public FragmentViewPageAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	/**
	 * 下面的 instantiteItem和destroyItem两个方法 创建不需要修改
	 * 
	 */

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
	}

}
