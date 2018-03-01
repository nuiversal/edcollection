package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.adding.entity.SBZTEnum;
import com.winway.android.edcollection.adding.entity.ZYFLTypeEnum;
import com.winway.android.edcollection.base.BaseBll;

import android.content.Context;

/**
 * 电缆沟
 * 
 * @author
 *
 */
public class ChannelDLGBll extends BaseBll<Object> {

	public ChannelDLGBll(Context context, String dbUrl) {
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
}
