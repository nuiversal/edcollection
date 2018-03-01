package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.adding.entity.GDLXTypeEnum;
import com.winway.android.edcollection.adding.entity.SBZTEnum;
import com.winway.android.edcollection.adding.entity.ZYFLTypeEnum;
import com.winway.android.edcollection.base.BaseBll;

import android.content.Context;

/**
 * 电缆管道
 * 
 * @author
 *
 */
public class ChannelDLGDBll extends BaseBll<Object> {

	public ChannelDLGDBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
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
		List<String> sbztList = new ArrayList<String>();
		sbztList.add(SBZTEnum.wty.getValue());
		sbztList.add(SBZTEnum.zy.getValue());
		sbztList.add(SBZTEnum.xcly.getValue());
		return sbztList;
	}

	/**
	 * 管道类型枚举列表
	 */
	public List<String> getGDLXList() {
		List<String> GDLXList = new ArrayList<String>();
		GDLXList.add(GDLXTypeEnum.DL.getValue());
		GDLXList.add(GDLXTypeEnum.SZ.getValue());
		GDLXList.add(GDLXTypeEnum.YH.getValue());
		GDLXList.add(GDLXTypeEnum.XQ.getValue());
		return GDLXList;
	}

}
