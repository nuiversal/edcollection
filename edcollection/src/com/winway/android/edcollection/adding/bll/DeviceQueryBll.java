package com.winway.android.edcollection.adding.bll;

import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.util.LogUtil;

import android.content.Context;

/**
 * 设备查询
 * @author winway_cmx
 *
 */
public class DeviceQueryBll extends BaseBll<Object>{

	public DeviceQueryBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 获取标签实体
	 * @param lableNo
	 * @return
	 */
	public List<EcLineLabelEntity> getLineLable(String lableNo){
		String expr = "LABEL_NO ='" + lableNo + "'";
		List<EcLineLabelEntity> lineLableList = queryByExpr2(EcLineLabelEntity.class, expr);
		return lineLableList;
	}
	
	public List<EcLineLabelEntity> getLineLable1(String lableno){
		String sql = "select * from ec_line_label where LABEL_NO like '%"+lableno+"%'";
		try {
			List<EcLineLabelEntity> lineLableList = excuteQuery(EcLineLabelEntity.class, sql);
			return lineLableList;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void getLine(String oid){
		String expr = "OID ='" + oid + "'";
		List<EcLineNodesEntity> lineNodes = queryByExpr2(EcLineNodesEntity.class, expr);
		LogUtil.i(lineNodes.size()+"多少线路关联表");
		EcLineNodesEntity ecLineNodesEntity = lineNodes.get(0);
		String ecLineId = ecLineNodesEntity.getEcLineId();
		String expr2 = "EC_LINE_ID ='" + ecLineId + "'";
		List<EcLineEntity> ecLines = queryByExpr2(EcLineEntity.class, expr2);
		LogUtil.i(ecLines.size()+"多少线路");
		EcLineEntity ecLineEntity = ecLines.get(0);
	}
}
