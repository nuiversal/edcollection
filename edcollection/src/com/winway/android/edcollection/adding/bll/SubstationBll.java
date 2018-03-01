package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.adding.entity.RightPro;
import com.winway.android.edcollection.adding.entity.VoltageGrade;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.vo.EcSubstationEntityVo;

import android.content.Context;

/**
 * 变电站业务操作
 * 
 * @author zgq
 *
 */
public class SubstationBll extends BaseBll<EcSubstationEntity> {

	public SubstationBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	public boolean saveSubstation(EcSubstationEntity ecSubstationEntity) {
		return this.save(ecSubstationEntity);
	}

	/**
	 * 获取产权属性枚举列表
	 * 
	 * @return
	 */
	public List<String> getRightProNameList() {
		List<String> rightProList = new ArrayList<String>();
		rightProList.add(RightPro.GY.getName());
		rightProList.add(RightPro.ZY.getName());
		return rightProList;
	}
	/**
	 * 获取产权属性对应的value值
	 * @param name
	 * @return
	 */
	public Integer getRightProValue(String name){
		if(RightPro.GY.getName().equals(name)){
			return RightPro.GY.getValue();
		}else if (RightPro.ZY.getName().equals(name)) {
			return RightPro.ZY.getValue();
		}
		return null;
	}
	/**
	 * 获取产权属性对应的name
	 * @param value
	 * @return
	 */
	public String getRightProName(int value){
		if(RightPro.GY.getValue()==value){
			return RightPro.GY.getName();
		}else if (RightPro.ZY.getValue()==value) {
			return RightPro.ZY.getName();
		}
		return null;
	}
	
	/**
	 * 判断是否存在该编号的线路
	 * 
	 * @param substationNo
	 * @return true表示已经存在，false表示不存在
	 */
	public boolean isExistSubstationNo(String substationNo) {
		String expr = "STATION_NO='" + substationNo + "'";
		List<EcSubstationEntity> list = this.queryByExpr(EcSubstationEntity.class, expr);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

}
