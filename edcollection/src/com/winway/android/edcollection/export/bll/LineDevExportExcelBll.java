package com.winway.android.edcollection.export.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.adding.entity.BindTargetType;
import com.winway.android.edcollection.adding.entity.TechFeatureType;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.export.entity.ExcelEntity4Dlfzx;
import com.winway.android.edcollection.export.entity.ExcelEntity4Dydlfzx;
import com.winway.android.edcollection.export.entity.ExcelEntity4Hwg;
import com.winway.android.edcollection.export.entity.ExcelEntity4Label;
import com.winway.android.edcollection.export.entity.ExcelEntity4MiddleJoin;
import com.winway.android.edcollection.export.utils.ExportExcelUtils;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.util.DateUtils;
import com.winway.jxl.core.ExcelWriter;

/**
 * 线路设备数据导出：标签、中间接头、电缆分支箱、低压电缆分支箱、环网柜
 * 
 * @author lyh
 * @version 创建时间：2018年2月5日
 *
 */
public class LineDevExportExcelBll {
	private String mPrjExportDir = null;
	private BaseDBUtil mBaseDBUtil = null;
	private Context context;
	private Map<String, EcNodeEntity> mOid2NodeMap = null;
	
	public LineDevExportExcelBll(Context context,String mPrjExportDir,Map<String, EcNodeEntity> mOid2NodeMap) {
		mBaseDBUtil = new BaseDBUtil(context, GlobalEntry.prjDbUrl);
		this.mPrjExportDir = mPrjExportDir;
		this.context = context;
		this.mOid2NodeMap = mOid2NodeMap;
	}
	/**
	 * 导出标签
	 */
	public void exportLabelData(Map<String, EcLineEntity> mLineMap) {
		String filePath = mPrjExportDir + File.separator + "标签_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+".xls";
		try {
			ExcelWriter<ExcelEntity4Label> excelWriter = new ExcelWriter<ExcelEntity4Label>(filePath);
			List<ExcelEntity4Label> list = new ArrayList<>();
			EcLineLabelEntity queryEntity = new EcLineLabelEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcLineLabelEntity> lineLabelEntities = mBaseDBUtil.excuteQuery(queryEntity);
			if (lineLabelEntities != null && lineLabelEntities.size() > 0) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = lineLabelEntities.size(); i < len; i++) { 
					EcLineLabelEntity entity = lineLabelEntities.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}	
				Map<String, EcNodeEntity> nodeMapsByDevId = ExportExcelUtils.getInstance().
						getNodeMapsByLineDevId(context, mBaseDBUtil, subBuffer.toString(), mOid2NodeMap);
				Map<String, EcLineEntity> lineMap = ExportExcelUtils.getInstance().
						getLineMapsByLineDevId(context, mBaseDBUtil, subBuffer.toString(), mLineMap);
					for (EcLineLabelEntity entity : lineLabelEntities) {
						ExcelEntity4Label excelEntity = new ExcelEntity4Label();
						if (entity.getBindTarget() != null) {
							if (entity.getBindTarget() == BindTargetType.dlbt.getIndex()) {
								excelEntity.setBindTarget(BindTargetType.dlbt.getType());
							}else if (entity.getBindTarget() == BindTargetType.zjjt.getIndex()) {
								excelEntity.setBindTarget(BindTargetType.zjjt.getType());
							}else if (entity.getBindTarget() == BindTargetType.zdsb.getIndex()) {
								excelEntity.setBindTarget(BindTargetType.zdsb.getType());
							}
						}
						excelEntity.setCjsj(entity.getCjsj());
						excelEntity.setDeviceName(entity.getDevName());
						excelEntity.setLabelno(entity.getLabelNo());
						excelEntity.setLineno(lineMap == null ? "" : lineMap.get(entity.getObjId()).getLineNo());
						excelEntity.setMarkerno(nodeMapsByDevId == null ? "" :nodeMapsByDevId.get(entity.getObjId()).getMarkerNo());
						list.add(excelEntity);
					}
			}
			String fileName = "label_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出中间接头
	 */
	public void exportMiddleJoinData(Map<String, EcLineEntity> mLineMap) {
		String filePath = mPrjExportDir + File.separator + "电缆中间接头_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+".xls";
		try {
			ExcelWriter<ExcelEntity4MiddleJoin> excelWriter = new ExcelWriter<ExcelEntity4MiddleJoin>(filePath);
			List<ExcelEntity4MiddleJoin> list = new ArrayList<>();
			EcMiddleJointEntity queryEntity = new EcMiddleJointEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcMiddleJointEntity> middleEntities =mBaseDBUtil.excuteQuery(queryEntity);
			if (middleEntities != null && middleEntities.size() > 0) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = middleEntities.size(); i < len; i++) { 
					EcMiddleJointEntity entity = middleEntities.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> nodeMapsByDevId = ExportExcelUtils.getInstance().
						getNodeMapsByLineDevId(context, mBaseDBUtil, subBuffer.toString(), mOid2NodeMap);
				Map<String, EcLineEntity> lineMap = ExportExcelUtils.getInstance().
						getLineMapsByLineDevId(context, mBaseDBUtil, subBuffer.toString(), mLineMap);
				for (EcMiddleJointEntity entity : middleEntities) {
					ExcelEntity4MiddleJoin excelEntity = new ExcelEntity4MiddleJoin();
					excelEntity.setCjsj(entity.getCjsj());
					excelEntity.setDeviceName(entity.getSbmc());
					excelEntity.setDevMode(entity.getDevModel());
					excelEntity.setInstalPerson(entity.getWorker());
					excelEntity.setInstalUnit(entity.getInstallationUnit());
					excelEntity.setLineno(lineMap == null ? "" :lineMap.get(entity.getObjId()).getLineNo());
					excelEntity.setMarkerno(nodeMapsByDevId == null ? "" :nodeMapsByDevId.get(entity.getObjId()).getMarkerNo());
					excelEntity.setProductFactory(entity.getManufacutrer());
					excelEntity.setProductTime(entity.getScDate());
					if (entity.getTechFeature() != null) {
						if (entity.getTechFeature() == TechFeatureType.lss.getIndex()) {
							excelEntity.setTechFeature(TechFeatureType.lss.getValue());
						}else if (entity.getTechFeature() == TechFeatureType.rss.getIndex()) {
							excelEntity.setTechFeature(TechFeatureType.rss.getValue());
						}else if (entity.getTechFeature() == TechFeatureType.yzs.getIndex()) {
							excelEntity.setTechFeature(TechFeatureType.yzs.getValue());
						}else if (entity.getTechFeature() == TechFeatureType.cys.getIndex()) {
							excelEntity.setTechFeature(TechFeatureType.cys.getValue());
						}
					}
					excelEntity.setUseDate(entity.getTyDate());
					list.add(excelEntity);
				}	
			}
			String fileName = "middleJoin_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出电缆分支箱
	 */
	public void exportDlfzxData() {
		String filePath = mPrjExportDir + File.separator + "电缆分支箱_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+".xls";
		try {
			ExcelWriter<ExcelEntity4Dlfzx> excelWriter = new ExcelWriter<ExcelEntity4Dlfzx>(filePath);
			List<ExcelEntity4Dlfzx> list = new ArrayList<>();
			EcDlfzxEntity queryEntity = new EcDlfzxEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcDlfzxEntity> dlfzxEntities =mBaseDBUtil.excuteQuery(queryEntity);
			if (dlfzxEntities != null && dlfzxEntities.size() > 0) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = dlfzxEntities.size(); i < len; i++) { 
					EcDlfzxEntity entity = dlfzxEntities.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
					
				}
				Map<String, EcNodeEntity> nodeMapsByDevId = ExportExcelUtils.getInstance().
						getNodeMapsByLineDevId(context, mBaseDBUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcDlfzxEntity entity : dlfzxEntities) {
					ExcelEntity4Dlfzx excelEntity = new ExcelEntity4Dlfzx();
					excelEntity.setBz(entity.getBz());
					excelEntity.setCcbh(entity.getCcbh());
					excelEntity.setCcrq(entity.getCcrq());
					excelEntity.setCjsj(entity.getCjsj());
					excelEntity.setDldj(entity.getDldj());
					excelEntity.setDxmpid(entity.getDxmpid());
					excelEntity.setJddz(entity.getJddz());
					excelEntity.setLx(entity.getLx());
					excelEntity.setMarkerno(nodeMapsByDevId == null ? "" :nodeMapsByDevId.get(entity.getObjId()).getMarkerNo());
					excelEntity.setSbid(entity.getSbid());
					excelEntity.setSbmc(entity.getSbmc());
					excelEntity.setSbzt(entity.getSbzt());
					excelEntity.setSccj(entity.getSccj());
					excelEntity.setSsds(entity.getSsds());
					excelEntity.setSfdw(entity.getSfdw());
					excelEntity.setSfnw(entity.getSfnw());
					excelEntity.setTyrq(entity.getTyrq());
					excelEntity.setWhbz(entity.getWhbz());
					excelEntity.setXh(entity.getXh());
					excelEntity.setYwdw(entity.getYwdw());
					excelEntity.setYxbh(entity.getYxbh());
					excelEntity.setZcdw(entity.getZcdw());
					excelEntity.setZcxz(entity.getZcxz());
					excelEntity.setZycd(entity.getZycd());
					excelEntity.setZz(entity.getZz());
					list.add(excelEntity);
				}
			}
			String fileName = "dlfzx_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出低压电缆分支箱
	 */
	public void exportDydlfzxData() {
		String filePath = mPrjExportDir + File.separator + "低压电缆分支箱_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+".xls";
		try {
			ExcelWriter<ExcelEntity4Dydlfzx> excelWriter = new ExcelWriter<ExcelEntity4Dydlfzx>(filePath);
			List<ExcelEntity4Dydlfzx> list = new ArrayList<>();
			EcDydlfzxEntity queryEntity = new EcDydlfzxEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcDydlfzxEntity> dydlfzxEntities =mBaseDBUtil.excuteQuery(queryEntity);
			if (dydlfzxEntities != null && dydlfzxEntities.size() > 0) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = dydlfzxEntities.size(); i < len; i++) { 
					EcDydlfzxEntity entity = dydlfzxEntities.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> nodeMapsByDevId = ExportExcelUtils.getInstance().
						getNodeMapsByLineDevId(context, mBaseDBUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcDydlfzxEntity entity : dydlfzxEntities) {
					ExcelEntity4Dydlfzx excelEntity = new ExcelEntity4Dydlfzx();
					excelEntity.setBz(entity.getBz());
					excelEntity.setCcbh(entity.getCcbh());
					excelEntity.setCcrq(entity.getCcrq());
					excelEntity.setCjsj(entity.getCjsj());
					excelEntity.setDldj(entity.getDldj());
					excelEntity.setDxmpid(entity.getDxmpid());
					excelEntity.setEddl(entity.getEddl());
					excelEntity.setEddy(entity.getEddy());
					excelEntity.setMarkerno(nodeMapsByDevId == null ? "" :nodeMapsByDevId.get(entity.getObjId()).getMarkerNo());
					excelEntity.setSbid(entity.getSbid());
					excelEntity.setSblx(entity.getSblx());
					excelEntity.setSbmc(entity.getSbmc());
					excelEntity.setSbzt(entity.getSbzt());
					excelEntity.setSccj(entity.getSccj());
					excelEntity.setSsds(entity.getSsds());
					excelEntity.setTyrq(entity.getTyrq());
					excelEntity.setWhbz(entity.getWhbz());
					excelEntity.setXh(entity.getXh());
					excelEntity.setYwdw(entity.getYwdw());
					excelEntity.setYxbh(entity.getYxbh());
					excelEntity.setZcdw(entity.getZcdw());
					excelEntity.setZcxz(entity.getZcxz());
					list.add(excelEntity);
				}
			}
			String fileName = "dydlfzx_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出环网柜
	 */
	public void exportHwgData() {
		String filePath = mPrjExportDir + File.separator + "环网柜_"+DateUtils.date2Str(DateUtils.yyyymmddhhmmss)+".xls";
		try {
			ExcelWriter<ExcelEntity4Hwg> excelWriter = new ExcelWriter<ExcelEntity4Hwg>(filePath);
			List<ExcelEntity4Hwg> list = new ArrayList<>();
			EcHwgEntity queryEntity = new EcHwgEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcHwgEntity> hwgEntities =mBaseDBUtil.excuteQuery(queryEntity);
			if (hwgEntities != null && hwgEntities.size() > 0) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = hwgEntities.size(); i < len; i++) { 
					EcHwgEntity entity = hwgEntities.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> nodeMapsByDevId = ExportExcelUtils.getInstance().
						getNodeMapsByLineDevId(context, mBaseDBUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcHwgEntity entity : hwgEntities) {
					ExcelEntity4Hwg excelEntity = new ExcelEntity4Hwg();
					excelEntity.setByjcxjgs(entity.getByjcxjgs());
					excelEntity.setBz(entity.getBz());
					excelEntity.setCcbh(entity.getCcbh());
					excelEntity.setCcrq(entity.getCcrq());
					excelEntity.setCjsj(entity.getCjsj());
					excelEntity.setDldj(entity.getDldj());
					excelEntity.setDqtz(entity.getDqtz());
					excelEntity.setDxmpid(entity.getDxmpid());
					excelEntity.setJddz(entity.getJddz());
					excelEntity.setMarkerno(nodeMapsByDevId == null ? "" :nodeMapsByDevId.get(entity.getObjId()).getMarkerNo());
					excelEntity.setSbid(entity.getSbid());
					excelEntity.setSbmc(entity.getSbmc());
					excelEntity.setSbzt(entity.getSbzt());
					excelEntity.setSfdw(entity.getSfdw());
					excelEntity.setSfnw(entity.getSfnw());
					excelEntity.setSccj(entity.getSccj());
					excelEntity.setSsds(entity.getSsds());
					excelEntity.setTyrq(entity.getTyrq());
					excelEntity.setWhbz(entity.getWhbz());
					excelEntity.setXh(entity.getXh());
					excelEntity.setYwdw(entity.getYwdw());
					excelEntity.setYxbh(entity.getYxbh());
					excelEntity.setZcdw(entity.getZcdw());
					excelEntity.setZcxz(entity.getZcxz());
					excelEntity.setZycd(entity.getZycd());
					excelEntity.setZz(entity.getZz());
					list.add(excelEntity);
				}	
			}
			String fileName = "hwg_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
