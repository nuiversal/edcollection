package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;

import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcLineEntity;

/**
 * 选择采集对象
 * 
 * @author zgq
 *
 */
public class SelectCollectObjBll extends BaseBll<EcLineEntity> {

	public SelectCollectObjBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据工程id获取线路列表
	 * 
	 * @param prjid
	 * @param mActivity
	 * @return
	 */
	@Deprecated
	public ArrayList<EcLineEntity> getLineListByPrjId(String prjid, Activity mActivity) {
		ArrayList<EcLineEntity> datas = new ArrayList<EcLineEntity>();
		try {
			// 找到当前项目的所有数据
			String expr = "PRJID='" + prjid + "' order by UPDATE_TIME desc";
			List<EcLineEntity> lineEntities = this.queryByExpr(EcLineEntity.class, expr);
			if (lineEntities != null && lineEntities.size() > 0) {
				datas.addAll(lineEntities);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 根据机构id获取线路列表
	 * 
	 * @param orgid
	 * @param mActivity
	 * @return
	 */
	public ArrayList<EcLineEntity> getLineListByOrgId(String orgid, Activity mActivity) {
		ArrayList<EcLineEntity> datas = new ArrayList<EcLineEntity>();
		try {
			// 找到当前项目的所有数据
			String expr = "ORGID='" + orgid + "' order by UPDATE_TIME desc";
			List<EcLineEntity> lineEntities = this.queryByExpr(EcLineEntity.class, expr);
			if (lineEntities != null && lineEntities.size() > 0) {
				datas.addAll(lineEntities);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 获取路径点类型
	 * 
	 * @param mActivity
	 * @return
	 */
	public ArrayList<String> getNodeType(Activity mActivity) {
		ArrayList<String> type = new ArrayList<String>();
		type.add(NodeMarkerType.BSQ.getName());
		type.add(NodeMarkerType.BSD.getName());
		type.add(NodeMarkerType.XND.getName());
		type.add(NodeMarkerType.GT.getName());
		return type;
	}
	
	

}
