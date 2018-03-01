package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.adding.entity.LayType;
import com.winway.android.edcollection.adding.entity.VoltageGrade;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;

import android.content.Context;

/**
 * 采集基本信息页业务处理
 * 
 * @author zgq
 *
 */
public class CollectCommonBll extends CommonBll {

	public CollectCommonBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取电压等级枚举列表
	 * 
	 * @return
	 */
	public List<String> getVoltageList() {
		ArrayList<String> voltageList = new ArrayList<String>();
		voltageList.add(VoltageGrade.VOLTAGE_10KV.getValue());
		voltageList.add(VoltageGrade.VOLTAGE_110KV.getValue());
		voltageList.add(VoltageGrade.VOLTAGE_220KV.getValue());
		return voltageList;
	}

	/**
	 * 获取所有敷设类型
	 * 
	 * @return
	 */
	public List<String> getLayTypeList() {
		List<String> list = new ArrayList<String>();
		list.add(LayType.dg.getValue());
		list.add(LayType.tlg.getValue());
		list.add(LayType.dlc.getValue());
		list.add(LayType.dlg.getValue());
		list.add(LayType.jk.getValue());
		list.add(LayType.mg.getValue());
		list.add(LayType.pg.getValue());
		list.add(LayType.sd.getValue());
		list.add(LayType.qj.getValue());
		list.add(LayType.zhg.getValue());
		return list;
	}

	/**
	 * 获取所有通道类型
	 * 
	 * @return
	 */
	public List<String> getChannelTypeList() {
		List<String> list = new ArrayList<String>();
		list.add(ResourceEnum.DG.getName());
		list.add(ResourceEnum.TLG.getName());
		list.add(ResourceEnum.GD.getName());
		list.add(ResourceEnum.PG.getName());
		list.add(ResourceEnum.MG.getName());
		list.add(ResourceEnum.QJ.getName());
		list.add(ResourceEnum.SD.getName());
		list.add(ResourceEnum.ZM.getName());
		list.add(ResourceEnum.DLC.getName());
		list.add(ResourceEnum.JKXL.getName());
		return list;
	}

}
