package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.winway.android.edcollection.adding.entity.CZTypeEnum;
import com.winway.android.edcollection.adding.entity.RightPro;
import com.winway.android.edcollection.adding.entity.SBZTEnum;
import com.winway.android.edcollection.adding.entity.ZYFLTypeEnum;
import com.winway.android.edcollection.base.BaseBll;

/**
 *@author Administrator
 *@data 2017年2月13日
 */
public class ChannelDLQBll extends BaseBll<String>{

	public ChannelDLQBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取材质枚举列表
	 * 
	 * @return
	 */
	public List<String> getCZList() {
		List<String> czList = new ArrayList<String>();
		czList.add(CZTypeEnum.zt.getValue());
		czList.add(CZTypeEnum.gd.getValue());
		czList.add(CZTypeEnum.sn.getValue());
		return czList;
	}
	
	/**
	 * 获取专业分类枚举列表
	 */
	public List<String> getZYFLList() {
		List<String> zyflList = new ArrayList<String>();
		zyflList.add(ZYFLTypeEnum.sd.getValue());
		zyflList.add(ZYFLTypeEnum.pd.getValue());
		return zyflList;
	}
	
	/**
	 * 获取设备状态枚举列表
	 */
	
	public List<String> getSBZTList() {
		List<String> sbztList = new ArrayList<String>()	;
		sbztList.add(SBZTEnum.wty.getValue());
		sbztList.add(SBZTEnum.zy.getValue());
		sbztList.add(SBZTEnum.xcly.getValue());
		return sbztList;
	}

}
