package com.winway.android.edcollection.adding.util;

import java.util.List;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EcChannelEntity;

import android.content.Context;

/**
 * 检查通道名称是否重复
 * @author mr-lao
 *
 */
public class CheckInputChannelNameUtil {
	private BaseDBUtil dbUtil;

	private CheckInputChannelNameUtil(BaseDBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	static CheckInputChannelNameUtil util = null;

	public static CheckInputChannelNameUtil getInstance(BaseDBUtil dbUtil) {
		synchronized (CheckInputChannelNameUtil.class) {
			if (null == util) {
				util = new CheckInputChannelNameUtil(dbUtil);
			}
		}
		return util;
	}

	public static CheckInputChannelNameUtil getInstance(Context context, String dbFile) {
		return getInstance(new BaseDBUtil(context, dbFile));
	}

	public boolean isHasChannelName(String name, EcChannelEntity ignoreChannel) {
		try {
			EcChannelEntity queryEntity = new EcChannelEntity();
			queryEntity.setName(name);
			List<EcChannelEntity> queryResult = dbUtil.excuteQuery(EcChannelEntity.class, queryEntity);
			if (queryResult == null || queryResult.isEmpty()) {
				return false;
			}
			if (null != ignoreChannel) {
				if (queryResult.get(0).getEcChannelId().equals(ignoreChannel.getEcChannelId())) {
					return false;
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return true;
	}
}
