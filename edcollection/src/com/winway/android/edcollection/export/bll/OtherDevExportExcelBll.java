package com.winway.android.edcollection.export.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.export.entity.ExcelEntity4ChannelRefMarker;
import com.winway.android.edcollection.export.entity.ExcelEntity4LineRefMarker;
import com.winway.android.edcollection.export.utils.ExportExcelUtils;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.util.DateUtils;
import com.winway.jxl.core.ExcelWriter;

/**其他设备导出：线路节点关联表、通道节点关联表
 * @author lyh
 * @version 创建时间：2018年2月6日
 *
 */
public class OtherDevExportExcelBll {
	private String mPrjExportDir = null;
	private BaseDBUtil mBaseDBUtil = null;
	private Context context = null;
	private Map<String, EcNodeEntity> mOid2NodeMap = null;
	private Map<String, EcLineEntity> mLineMap = null;
	
	public OtherDevExportExcelBll(String mPrjExportDir, BaseDBUtil mBaseDBUtil, Context context,
			Map<String, EcNodeEntity> mOid2NodeMap,Map<String, EcLineEntity> mLineMap) {
		super();
		this.mPrjExportDir = mPrjExportDir;
		this.mBaseDBUtil = mBaseDBUtil;
		this.context = context;
		this.mOid2NodeMap = mOid2NodeMap;
		this.mLineMap = mLineMap;
	}
	
	/**
	 * 导出线路节点关联表
	 */
	public void exportLineNodeData() {
		String filePath = mPrjExportDir + File.separator + "线路节点关联表_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+".xls";
		try {
			ExcelWriter<ExcelEntity4LineRefMarker> excelWriter = new ExcelWriter<ExcelEntity4LineRefMarker>(filePath);
			List<ExcelEntity4LineRefMarker> list = new ArrayList<>();
			EcLineNodesEntity queryEntity = new EcLineNodesEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcLineNodesEntity> lineNodesEntities = mBaseDBUtil.excuteQuery(queryEntity);
			if (lineNodesEntities != null && lineNodesEntities.size() > 0) {
				StringBuffer lineidBuffer = new StringBuffer();
				StringBuffer oidBuffer = new StringBuffer();
				for (int i = 0, len = lineNodesEntities.size(); i < len; i++) { 
					EcLineNodesEntity entity = lineNodesEntities.get(i);
					if (i > 0) {
						lineidBuffer.append("','" + entity.getEcLineId());
						oidBuffer.append("','" + entity.getOid());
					} else {
						lineidBuffer.append(entity.getEcLineId());
						oidBuffer.append(entity.getOid());
					}
				}	
				Map<String, EcLineEntity> lineMapBylineId = ExportExcelUtils.getInstance().getLineMapBylineId(mBaseDBUtil, lineidBuffer.toString(),mLineMap);
				Map<String, EcNodeEntity> nodeMapByOid= ExportExcelUtils.getInstance().getNodeMapByOid(mBaseDBUtil, oidBuffer.toString(),mOid2NodeMap);
				for (EcLineNodesEntity entity : lineNodesEntities) {
					ExcelEntity4LineRefMarker excelEntity = new ExcelEntity4LineRefMarker();
					excelEntity.setIndex(entity.getNIndex());
					excelEntity.setRemark(entity.getRemark());
					excelEntity.setUpLay(entity.getUpLay());
					excelEntity.setUpSpace(entity.getUpSpace()+"");
					excelEntity.setLineno(lineMapBylineId == null ? "" : lineMapBylineId.get(entity.getEcLineId()).getLineNo());
					excelEntity.setMarkerno(nodeMapByOid == null ? "" :nodeMapByOid.get(entity.getOid()).getMarkerNo());
					list.add(excelEntity);
				}		
			}
			String fileName = "lineNode_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
			excelWriter.writeExcel(list, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出通道节点关联表
	 */
	public void exportChannelNodeData() {
		String filePath = mPrjExportDir + File.separator + "通道节点关联表_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+".xls";
		try {
			ExcelWriter<ExcelEntity4ChannelRefMarker> excelWriter = new ExcelWriter<ExcelEntity4ChannelRefMarker>(filePath);
			List<ExcelEntity4ChannelRefMarker> list = new ArrayList<>();
			EcChannelNodesEntity queryEntity = new EcChannelNodesEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcChannelNodesEntity> lineNodesEntities = mBaseDBUtil.excuteQuery(queryEntity);
			if (lineNodesEntities != null && lineNodesEntities.size() > 0) {
				StringBuffer oidBuffer = new StringBuffer();
				for (int i = 0, len = lineNodesEntities.size(); i < len; i++) { 
					EcChannelNodesEntity entity = lineNodesEntities.get(i);
					if (i > 0) {
						oidBuffer.append("','" + entity.getOid());
					} else {
						oidBuffer.append(entity.getOid());
					}
				}	
				Map<String, EcNodeEntity> nodeMapByOid= ExportExcelUtils.getInstance().getNodeMapByOid(mBaseDBUtil, oidBuffer.toString(),mOid2NodeMap);
				for (EcChannelNodesEntity entity : lineNodesEntities) {
					ExcelEntity4ChannelRefMarker excelEntity = new ExcelEntity4ChannelRefMarker();
					excelEntity.setChannelid(entity.getEcChannelId());
					excelEntity.setIndex(entity.getNIndex());
					excelEntity.setMarkerno(nodeMapByOid == null ? "" :nodeMapByOid.get(entity.getOid()).getMarkerNo());
					list.add(excelEntity);
				}		
			}
			String fileName = "channelNode_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
			excelWriter.writeExcel(list, fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
