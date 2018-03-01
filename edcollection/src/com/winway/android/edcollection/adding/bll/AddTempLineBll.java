package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;

import android.content.Context;

/**
 * 添加临时线路
 * 
 * @author zgq
 *
 */
public class AddTempLineBll extends BaseBll<EcLineEntity> {

	public AddTempLineBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 保存线路
	 * 
	 * @param ecLineEntity
	 * @return
	 */
	public boolean saveLine(EcLineEntity ecLineEntity) {
		return this.saveOrUpdate(ecLineEntity);
	}

	/**
	 * 根据项目id获取变电站列表
	 * 
	 * @param prjId
	 * @return
	 */
	public List<EcSubstationEntity> getSubstationListByPrjId(String prjId) {
		List<EcSubstationEntity> list = new ArrayList<EcSubstationEntity>();
		String expr = "PRJID='" + prjId + "'";
		list = this.queryByExpr2(EcSubstationEntity.class, expr);
		return list;
	}
	/**
	 * 根据机构id获取变电站列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<EcSubstationEntity> getSubstationListByOrgId(String orgId) {
		List<EcSubstationEntity> list = new ArrayList<EcSubstationEntity>();
		String expr = "ORGID='" + orgId + "'";
		list = this.queryByExpr2(EcSubstationEntity.class, expr);
		return list;
	}
	
	/**
	 * 通过变电站名称获取变电站实体
	 * @param substationName
	 * @return
	 */
	public EcSubstationEntity getSubstationByName(String substationName){
		String expr = "NAME='"+substationName+"'";
		List<EcSubstationEntity> list = this.queryByExpr2(EcSubstationEntity.class, expr);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 判断是否存在该名称的线路
	 * 
	 * @param lineName
	 * @return true表示已经存在，false表示不存在
	 */
	public boolean isExistLineName(String lineName) {
		String expr = "NAME='" + lineName + "'";
		List<EcLineEntity> list = this.queryByExpr(EcLineEntity.class, expr);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否存在该编号的线路
	 * 
	 * @param lineNo
	 * @return true表示已经存在，false表示不存在
	 */
	public boolean isExistLineNo(String lineNo) {
		String expr = "LINE_NO='" + lineNo + "'";
		List<EcLineEntity> list = this.queryByExpr(EcLineEntity.class, expr);
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据变电站编号拿到变电站名称
	 * @param stationNo
	 * @return
	 */
	public String getSubName(String stationNo) {
		String subname = "";
		List<EcSubstationEntity> list = new ArrayList<EcSubstationEntity>();
		String where = "STATION_NO = '"+stationNo+"'";
		list = this.queryByExpr2(EcSubstationEntity.class, where);
		if (list != null && list.size() >0) {
			for (EcSubstationEntity ecSubstationEntity : list) {
				 subname = ecSubstationEntity.getName();
			}
		}
		return subname;
	}

}
