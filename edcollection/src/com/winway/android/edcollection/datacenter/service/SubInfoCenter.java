package com.winway.android.edcollection.datacenter.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.datacenter.entity.SubStationDetailsEntity;
import com.winway.android.edcollection.datacenter.loader.LoaderConfig;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;

import android.content.Context;

/**
 * 变电站信息中心
 * 
 * @author lyh
 * @version 创建时间：2017年1月5日 上午10:47:19
 * 
 */
public class SubInfoCenter {
	BaseDBUtil dbUtil;

	public SubInfoCenter(Context context, String dbUrl) {
		super();
		if (dbUrl == null) {
			dbUrl = LoaderConfig.dbUrl;
		}
		dbUtil = new BaseDBUtil(context, new File(dbUrl));
	}

	public SubInfoCenter(BaseDBUtil dbUtil) {
		super();
		this.dbUtil = dbUtil;
	}

	/**
	 * 1、获得具体的变电站信息
	 * @param subStationId=变电站ID值
	 * @param containsLines=限定返回的数据中是否要包含线路数据，
	 * true表示返回的数据中要包含线路数据，false则表示返回的数据中不包含线路数据
	 */
	public SubStationDetailsEntity getSubStationDetails(String subStationId,
			boolean containsLines) {
		try {
			EcSubstationEntity data = dbUtil.getById(EcSubstationEntity.class, subStationId);
			if (data == null) {
				return null;
			}
			SubStationDetailsEntity infoCenEntity = new SubStationDetailsEntity();
			infoCenEntity.setData(data);
			if (containsLines == true) {
				List<EcLineEntity> lines = getSubStationLineList(subStationId);
				if (lines != null && lines.size() > 0) {
					infoCenEntity.setLines(lines);
				}
			}
			return infoCenEntity;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2、获得变电站下的所有线路
	 * subStationId=变电站ID值
	 * @param subStationId
	 * @return
	 */
	public List<EcLineEntity> getSubStationLineList(String subStationId) {
		try {
			EcSubstationEntity substationEntity = dbUtil.getById(EcSubstationEntity.class,
					subStationId);
			if (substationEntity != null) {
				EcLineEntity queryEntity = new EcLineEntity();
				queryEntity.setStartStation(substationEntity.getStationNo());
				return dbUtil.excuteQuery(EcLineEntity.class, queryEntity);
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据变电站编号拿到变电站名称
	 * @param stationNo
	 * @return
	 */
	public String getSubName(String stationNo) {
		String subname = "";
		try {
			List<EcSubstationEntity> list = new ArrayList<EcSubstationEntity>();
			String where = "STATION_NO = '"+stationNo+"'";
			list = dbUtil.excuteWhereQuery(EcSubstationEntity.class, where);
			if (list != null && list.size() >0) {
				for (EcSubstationEntity ecSubstationEntity : list) {
					 subname = ecSubstationEntity.getName();
				}
			}
			return subname;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
