package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.winway.android.edcollection.adding.entity.DangerStatusType;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.project.entity.EcVRRefEntity;
import com.winway.android.edcollection.project.entity.EmDangerInfoEntity;

/**
 * 通道隐患业务处理
 * 
 * @author winway_zgq
 *
 */
public class ChannelDangerBll extends BaseBll<EmDangerInfoEntity> {

	public ChannelDangerBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据节点oid获取节点所在通道的隐患
	 * @param oid
	 * @return
	 */
	public EmDangerInfoEntity getDangerByOid(String oid) {
		String expr = "LNK_OBJ_ID='" + oid + "'";
		List<EmDangerInfoEntity> tempDangers = this.queryByExpr(EmDangerInfoEntity.class, expr);
		if (tempDangers != null && tempDangers.size() > 0) {
			return tempDangers.get(0);
		}
		return null;
	}

	public List<String> getDangerStatusTypes() {
		// TODO Auto-generated method stub
		List<String> typeDatas = new ArrayList<>();
		for (DangerStatusType dangerStatusType:DangerStatusType.values()) {
			typeDatas.add(dangerStatusType.getValue());
		}
		return typeDatas;
	}

}
