package com.winway.android.edcollection.export.bll;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.datacenter.api.DataCenter;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.util.FileUtil;

/**
 * 数据导出
 * 
 * @author zgq
 *
 */
public class CommentExportExcelBll {

	public String mPrjExportDir = null;
	public DataCenter mDataCenter = null;
	public BaseDBUtil mBaseDBUtil = null;
	public Map<String, EcNodeEntity> mOid2NodeMap = new HashMap<>();
	public Map<String, EcLineEntity> mLineMap = new HashMap<>();

	public CommentExportExcelBll(Context context) {
		mDataCenter = new DataCenterImpl(context, GlobalEntry.prjDbUrl);
		mBaseDBUtil = new BaseDBUtil(context, GlobalEntry.prjDbUrl);
		createPrjExportDir();
		getNodeMapsByPrj(GlobalEntry.currentProject.getEmProjectId());
		getLineMapsByPrj(GlobalEntry.currentProject.getEmProjectId());
	}

	/**
	 * 创建项目导出目录
	 */
	private void createPrjExportDir() {
		String filePath = FileUtil.AppRootPath + File.separator + "export" + File.separator
				+ GlobalEntry.currentProject.getPrjNo();
		FileUtil.createPath(filePath);
		mPrjExportDir = filePath;
	}

	/**
	 * 根据工程id获取oid的节点map
	 * 
	 * @param prjId
	 */
	private void getNodeMapsByPrj(String prjId) {
		EcNodeEntity queryEntity = new EcNodeEntity();
		queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		try {
			List<EcNodeEntity> nodes = mBaseDBUtil.excuteQuery(queryEntity);
			if (nodes != null && nodes.size() > 0) {
				for (EcNodeEntity nodeEntity : nodes) {
					mOid2NodeMap.put(nodeEntity.getOid(), nodeEntity);
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据工程id获取线路map
	 * 
	 * @param prjId
	 */
	private void getLineMapsByPrj(String prjId) {
		EcLineEntity queryEntity = new EcLineEntity();
		queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		try {
			List<EcLineEntity> lines = mBaseDBUtil.excuteQuery(queryEntity);
			if (lines != null && lines.size() > 0) {
				for (EcLineEntity nodeEntity : lines) {
					mLineMap.put(nodeEntity.getLineNo(), nodeEntity);
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
	}
	
}
