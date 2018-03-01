package com.winway.android.edcollection.export.bll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.adding.entity.RightPro;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.export.entity.ExcelEntity4DistributionRoom;
import com.winway.android.edcollection.export.entity.ExcelEntity4Dypdx;
import com.winway.android.edcollection.export.entity.ExcelEntity4Kgz;
import com.winway.android.edcollection.export.entity.ExcelEntity4Substation;
import com.winway.android.edcollection.export.entity.ExcelEntity4Tower;
import com.winway.android.edcollection.export.entity.ExcelEntity4Transformer;
import com.winway.android.edcollection.export.entity.ExcelEntity4WorkWell;
import com.winway.android.edcollection.export.entity.ExcelEntity4WorkWellCover;
import com.winway.android.edcollection.export.entity.ExcelEntity4Xsbdz;
import com.winway.android.edcollection.export.utils.ExportExcelUtils;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.util.DateUtils;
import com.winway.jxl.core.ExcelWriter;

import android.content.Context;

/**
 * 节点设备数据导出：电缆井、变电站、箱式变电站、开关站、杆塔、变压器、配电室、低压配电箱、
 * 
 * @author xs
 * @version 创建时间：2018年2月5日
 *
 */
public class NodeDevExportExcelBll {
	// 上下文
	private Context context = null;
	// 文件路径
	private String rootExportFilePath = null;
	// DBUtil
	private BaseDBUtil dbUtil = null;
	// 节点映射
	private Map<String, EcNodeEntity> mOid2NodeMap = null;

	public NodeDevExportExcelBll(Context context, BaseDBUtil dbUtil, String rootExportFilePath,
			Map<String, EcNodeEntity> mOid2NodeMap) {
		this.context = context;
		this.rootExportFilePath = rootExportFilePath;
		this.dbUtil = dbUtil;
		this.mOid2NodeMap = mOid2NodeMap;
	}

	/**
	 * 导出工井
	 */
	public void exportWorkWell() {
		String filePath = this.rootExportFilePath + File.separator + "工井_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4WorkWell> excelWriter = new ExcelWriter<ExcelEntity4WorkWell>(filePath);
			List<ExcelEntity4WorkWell> list = new ArrayList<>();
			EcWorkWellEntity queryEntity = new EcWorkWellEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcWorkWellEntity> workWells = dbUtil.excuteQuery(queryEntity);
			if (null != workWells && !workWells.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = workWells.size(); i < len; i++) {
					EcWorkWellEntity entity = workWells.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> workWellId2NodeMap = ExportExcelUtils.getInstance()
						.getNodeMapsByNodeDevId(context, dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcWorkWellEntity entity : workWells) {
					ExcelEntity4WorkWell entity4WorkWell = new ExcelEntity4WorkWell();
					entity4WorkWell.setCjsj(entity.getCjsj());
					entity4WorkWell.setDeviceName(entity.getSbmc());
					entity4WorkWell.setDmcc(entity.getDmxx());
					entity4WorkWell.setDQTZ(entity.getDqtz());
					entity4WorkWell.setFsssqk(entity.getFsssqk());
					entity4WorkWell.setGbhd(entity.getGbhd() + "");
					entity4WorkWell.setGBKS(entity.getGbks());
					entity4WorkWell.setGjcd(entity.getGjcd() + "");
					entity4WorkWell.setGjkd(entity.getGjkd() + "");
					entity4WorkWell.setGjsd(entity.getGjsd() + "");
					entity4WorkWell.setGJXZ(entity.getGjxz());
					entity4WorkWell.setJG(entity.getJg());
					entity4WorkWell.setJgcc(entity.getJgcc() + "");
					entity4WorkWell.setJgccrq(entity.getJgccrq());
					entity4WorkWell.setJgcz(entity.getJgcz());
					entity4WorkWell.setJgxz(entity.getJgxz());
					entity4WorkWell.setJLX(entity.getJlx());
					entity4WorkWell.setJmgc(entity.getJmgc() + "");
					entity4WorkWell.setJqrq(entity.getJgrq());
					entity4WorkWell.setJxygjjl(entity.getJxygjjl() + "");
					entity4WorkWell.setMarkerno(workWellId2NodeMap.get(entity.getObjId()).getMarkerNo());
					entity4WorkWell.setNdgc(entity.getNdgc() + "");
					entity4WorkWell.setPtcs(entity.getPtcs());
					entity4WorkWell.setRemark(entity.getBz());
					entity4WorkWell.setSgdw(entity.getSgdw());
					entity4WorkWell.setSgrq(entity.getSgrq());
					entity4WorkWell.setWHBZ(entity.getWhbz());
					entity4WorkWell.setXygjfx(entity.getXygjfx());
					entity4WorkWell.setYWDW(entity.getYwdw());
					list.add(entity4WorkWell);
				}
			}
			String fileName = "workwell_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出变电站
	 */
	public void exportSubstation() {
		String filePath = this.rootExportFilePath + File.separator + "变电站_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4Substation> excelWriter = new ExcelWriter<ExcelEntity4Substation>(filePath);
			List<ExcelEntity4Substation> list = new ArrayList<>();
			EcSubstationEntity queryEntity = new EcSubstationEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcSubstationEntity> substations = dbUtil.excuteQuery(queryEntity);
			if (substations != null && substations.size() > 0) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = substations.size(); i < len; i++) {
					EcSubstationEntity entity = substations.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getEcSubstationId());
					} else {
						subBuffer.append(entity.getEcSubstationId());
					}
				}
				// 变电站id->节点
				Map<String, EcNodeEntity> subid2NodeMap = ExportExcelUtils.getInstance().getNodeMapsByNodeDevId(context,
						dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcSubstationEntity entity : substations) {
					ExcelEntity4Substation excelEntity4Substation = new ExcelEntity4Substation();
					excelEntity4Substation.setCjsj(entity.getCjsj());
					excelEntity4Substation.setNodeno(subid2NodeMap.get(entity.getEcSubstationId()).getMarkerNo());
					excelEntity4Substation.setRemark(null);
					if (RightPro.GY.getValue().equals(entity.getRightPro())) {
						excelEntity4Substation.setRightPro(RightPro.GY.getName());
					} else {
						excelEntity4Substation.setRightPro(RightPro.ZY.getName());
					}
					excelEntity4Substation.setStationName(entity.getName());
					excelEntity4Substation.setStationNo(entity.getStationNo());
					excelEntity4Substation.setVotage(entity.getVoltage());
					list.add(excelEntity4Substation);
				}
			}
			String fileName = "substation_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出箱式变电站
	 */
	public void exportXsbdz() {
		String filePath = this.rootExportFilePath + File.separator + "箱式变电站_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4Xsbdz> excelWriter = new ExcelWriter<ExcelEntity4Xsbdz>(filePath);
			List<ExcelEntity4Xsbdz> list = new ArrayList<>();
			EcXsbdzEntity queryEntity = new EcXsbdzEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcXsbdzEntity> xsbdzs = dbUtil.excuteQuery(queryEntity);
			if (null != xsbdzs && !xsbdzs.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = xsbdzs.size(); i < len; i++) {
					EcXsbdzEntity entity = xsbdzs.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> xsbdzId2NodeMap = ExportExcelUtils.getInstance()
						.getNodeMapsByNodeDevId(context, dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcXsbdzEntity entity : xsbdzs) {
					ExcelEntity4Xsbdz entity4Xsbdz = new ExcelEntity4Xsbdz();
					entity4Xsbdz.setBz(entity.getBz());
					entity4Xsbdz.setCcbh(entity.getCcbh());
					entity4Xsbdz.setCcrq(entity.getCcrq());
					entity4Xsbdz.setDeviceName(entity.getSbmc());
					entity4Xsbdz.setDqtz(entity.getDqtz());
					entity4Xsbdz.setDxmpid(entity.getDxmpid());
					entity4Xsbdz.setDydj(entity.getDydj());
					entity4Xsbdz.setHwbz(entity.getWhbz());
					entity4Xsbdz.setJddz(entity.getJddz());
					entity4Xsbdz.setMarkerno(xsbdzId2NodeMap.get(entity.getObjId()).getMarkerNo());
					entity4Xsbdz.setPbzrl(entity.getPbzrl());
					entity4Xsbdz.setSbzjfs(entity.getSbzjfs());
					entity4Xsbdz.setSbzt(entity.getSbzt());
					entity4Xsbdz.setSccj(entity.getSccj());
					entity4Xsbdz.setSfdw(entity.getSfdw());
					entity4Xsbdz.setSfjyhw(entity.getSfjyhw());
					entity4Xsbdz.setSfnw(entity.getSfnw());
					entity4Xsbdz.setSsds(entity.getSsds());
					entity4Xsbdz.setTyrq(entity.getTyrq());
					entity4Xsbdz.setXblx(entity.getXblx());
					entity4Xsbdz.setXh(entity.getXh());
					entity4Xsbdz.setYwdw(entity.getYwdw());
					entity4Xsbdz.setYxbh(entity.getYxbh());
					entity4Xsbdz.setZcdw(entity.getZcdw());
					entity4Xsbdz.setZcxz(entity.getZcxz());
					entity4Xsbdz.setZycd(entity.getZycd());
					entity4Xsbdz.setZz(entity.getZz());
					list.add(entity4Xsbdz);
				}
			}
			String fileName = "xsbdz_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出开关站
	 */
	public void exportKgz() {
		String filePath = this.rootExportFilePath + File.separator + "开关站_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4Kgz> excelWriter = new ExcelWriter<ExcelEntity4Kgz>(filePath);
			List<ExcelEntity4Kgz> list = new ArrayList<>();
			EcKgzEntity queryEntity = new EcKgzEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcKgzEntity> kgzs = dbUtil.excuteQuery(queryEntity);
			if (null != kgzs && !kgzs.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = kgzs.size(); i < len; i++) {
					EcKgzEntity entity = kgzs.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> kgzId2NodeMap = ExportExcelUtils.getInstance().getNodeMapsByNodeDevId(context,
						dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcKgzEntity entity : kgzs) {
					ExcelEntity4Kgz entity4Kgz = new ExcelEntity4Kgz();
					entity4Kgz.setByjcxjg(entity.getByjcxjg());
					entity4Kgz.setBzfs(entity.getBzfs());
					entity4Kgz.setCjsj(entity.getCjsj());
					entity4Kgz.setDeviceName(entity.getSbmc());
					entity4Kgz.setDxmpid(entity.getDxmpid());
					entity4Kgz.setDydj(entity.getDydj());
					entity4Kgz.setDzzyjb(entity.getDzzyjb());
					entity4Kgz.setFwfs(entity.getFwfs());
					entity4Kgz.setHwbz(entity.getWhbz());
					entity4Kgz.setMarkerno(kgzId2NodeMap.get(entity.getObjId()).getMarkerNo());
					entity4Kgz.setRemark(entity.getBz());
					entity4Kgz.setSbzt(entity.getSbzt());
					entity4Kgz.setSfdw(entity.getSfdw());
					entity4Kgz.setSfnw(entity.getSfnw());
					entity4Kgz.setSfznbdz(entity.getSfznbdz());
					entity4Kgz.setSsds(entity.getSsds());
					entity4Kgz.setTyrq(entity.getTyrq());
					entity4Kgz.setWhdj(entity.getWhdj());
					entity4Kgz.setYwdw(entity.getYwdw());
					entity4Kgz.setYxbh(entity.getYxbh());
					entity4Kgz.setZbfs(entity.getZbfs());
					entity4Kgz.setZcdw(entity.getZcdw());
					entity4Kgz.setZcxz(entity.getZcxz());
					entity4Kgz.setZz(entity.getAddress());
					list.add(entity4Kgz);
				}
			}
			String fileName = "xsbdz_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出杆塔
	 */
	public void exportTower() {
		String filePath = this.rootExportFilePath + File.separator + "杆塔_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4Tower> excelWriter = new ExcelWriter<ExcelEntity4Tower>(filePath);
			List<ExcelEntity4Tower> list = new ArrayList<>();
			EcTowerEntity queryEntity = new EcTowerEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcTowerEntity> towers = dbUtil.excuteQuery(queryEntity);
			if (null != towers && !towers.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = towers.size(); i < len; i++) {
					EcTowerEntity entity = towers.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> towerId2NodeMap = ExportExcelUtils.getInstance()
						.getNodeMapsByNodeDevId(context, dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcTowerEntity entity : towers) {
					ExcelEntity4Tower entity4Tower = new ExcelEntity4Tower();
					entity4Tower.setCreateTime(entity.getCreateTime());
					entity4Tower.setTowerNo(entity.getTowerNo());
					entity4Tower.setRunningStatus(entity.getRunningStatus());
					entity4Tower.setPropertyRights(entity.getPropertyRights());
					entity4Tower.setCommissioningDate(entity.getCommissioningDate());
					entity4Tower.setManufacturer(entity.getManufacturer());
					entity4Tower.setEquipmentModel(entity.getEquipmentModel());
					entity4Tower.setManufacturingNumber(entity.getManufacturingNumber());
					entity4Tower.setAccomplishDate(entity.getAccomplishDate());
					entity4Tower.setMaterial(entity.getMaterial());
					entity4Tower.setCornerDegree(entity.getCornerDegree());
					entity4Tower.setHeight(entity.getHeight() != null ? (float) ((double) entity.getHeight()) : null);
					entity4Tower.setRemarks(entity.getRemarks());
					entity4Tower.setMarkerno(towerId2NodeMap.get(entity.getObjId()).getMarkerNo());
					entity4Tower.setMaintenanceDepartment(entity.getMaintenanceDepartment());
					list.add(entity4Tower);
				}
			}
			String fileName = "tower_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出变压器
	 */
	public void exportTransformer() {
		String filePath = this.rootExportFilePath + File.separator + "变压器_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4Transformer> excelWriter = new ExcelWriter<ExcelEntity4Transformer>(filePath);
			List<ExcelEntity4Transformer> list = new ArrayList<>();
			EcTransformerEntity queryEntity = new EcTransformerEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcTransformerEntity> transformers = dbUtil.excuteQuery(queryEntity);
			if (null != transformers && !transformers.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = transformers.size(); i < len; i++) {
					EcTransformerEntity entity = transformers.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> transformerId2NodeMap = ExportExcelUtils.getInstance()
						.getNodeMapsByNodeDevId(context, dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcTransformerEntity entity : transformers) {
					ExcelEntity4Transformer entity4Transformer = new ExcelEntity4Transformer();
					entity4Transformer.setCreateTime(entity.getCreateTime());
					entity4Transformer.setSbmc(entity.getSbmc());
					entity4Transformer.setMaintenanceDepartment(entity.getMaintenanceDepartment());
					entity4Transformer.setVoltage(entity.getVoltage());
					entity4Transformer.setRunningStatus(entity.getRunningStatus());
					entity4Transformer.setStationHouse(entity.getStationHouse());
					entity4Transformer.setSpace(entity.getSpace());
					entity4Transformer.setEquipmentModel(entity.getEquipmentModel());
					entity4Transformer.setRatedCapacity(entity.getRatedCapacity());
					entity4Transformer.setPropertyRights(entity.getPropertyRights());
					entity4Transformer.setCommissioningDate(entity.getCommissioningDate());
					entity4Transformer.setManufacturer(entity.getManufacturer());
					entity4Transformer.setManufacturingNumber(entity.getManufacturingNumber());
					entity4Transformer.setUserImportantGrade(entity.getUserImportantGrade());
					entity4Transformer.setZgMark(entity.getZgMark());
					entity4Transformer.setCustomerName(entity.getCustomerName());
					entity4Transformer.setCustomerNumber(entity.getCustomerNumber());
					entity4Transformer.setNoLoadCurrent(entity.getNoLoadCurrent());
					entity4Transformer.setNoLoadLoss(entity.getNoLoadLoss());
					entity4Transformer.setRemarks(entity.getRemarks());
					entity4Transformer.setMarkerno(transformerId2NodeMap.get(entity.getObjId()).getMarkerNo());
					entity4Transformer.setLoadLoss(entity.getLoadLoss());
					list.add(entity4Transformer);
				}
			}
			String fileName = "transformer_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出配电房
	 */
	public void exportDistributionRoom() {
		String filePath = this.rootExportFilePath + File.separator + "配电房_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4DistributionRoom> excelWriter = new ExcelWriter<ExcelEntity4DistributionRoom>(
					filePath);
			List<ExcelEntity4DistributionRoom> list = new ArrayList<>();
			EcDistributionRoomEntity queryEntity = new EcDistributionRoomEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcDistributionRoomEntity> distributionRooms = dbUtil.excuteQuery(queryEntity);
			if (null != distributionRooms && !distributionRooms.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = distributionRooms.size(); i < len; i++) {
					EcDistributionRoomEntity entity = distributionRooms.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> distributionRoomId2NodeMap = ExportExcelUtils.getInstance()
						.getNodeMapsByNodeDevId(context, dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcDistributionRoomEntity entity : distributionRooms) {
					ExcelEntity4DistributionRoom entity4DistributionRoom = new ExcelEntity4DistributionRoom();
					entity4DistributionRoom.setSbid(entity.getSbid());
					entity4DistributionRoom.setSbmc(entity.getSbmc());
					entity4DistributionRoom.setYxbh(entity.getYxbh());
					entity4DistributionRoom.setDxmpid(entity.getDxmpid());
					entity4DistributionRoom.setDydj(entity.getDydj());
					entity4DistributionRoom.setSsds(entity.getSsds());
					entity4DistributionRoom.setYwdw(entity.getYwdw());
					entity4DistributionRoom.setWhbz(entity.getWhbz());
					entity4DistributionRoom.setSbzt(entity.getSbzt());
					entity4DistributionRoom.setTyrq(entity.getTyrq());
					entity4DistributionRoom.setPbts(entity.getPbts());
					entity4DistributionRoom.setPbzrl(entity.getPbzrl());
					entity4DistributionRoom.setWgbcrl(entity.getWgbcrl());
					entity4DistributionRoom.setFwfs(entity.getFwfs());
					entity4DistributionRoom.setJddz(entity.getJddz());
					entity4DistributionRoom.setZz(entity.getZz());
					entity4DistributionRoom.setDqtz(entity.getDqtz());
					entity4DistributionRoom.setZycd(entity.getZycd());
					entity4DistributionRoom.setZcxz(entity.getZcxz());
					entity4DistributionRoom.setZcdw(entity.getZcdw());
					entity4DistributionRoom.setBz(entity.getBz());
					entity4DistributionRoom.setCjsj(entity.getCjsj());
					entity4DistributionRoom
							.setMarkerno(distributionRoomId2NodeMap.get(entity.getObjId()).getMarkerNo());
					list.add(entity4DistributionRoom);
				}
			}
			String fileName = "distributionroom_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出低压配电箱
	 */
	public void exportDypdx() {
		String filePath = this.rootExportFilePath + File.separator + "低压配电箱_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4Dypdx> excelWriter = new ExcelWriter<ExcelEntity4Dypdx>(filePath);
			List<ExcelEntity4Dypdx> list = new ArrayList<>();
			EcDypdxEntity queryEntity = new EcDypdxEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcDypdxEntity> dypdxs = dbUtil.excuteQuery(queryEntity);
			if (null != dypdxs && !dypdxs.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = dypdxs.size(); i < len; i++) {
					EcDypdxEntity entity = dypdxs.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getObjId());
					} else {
						subBuffer.append(entity.getObjId());
					}
				}
				Map<String, EcNodeEntity> dypdxId2NodeMap = ExportExcelUtils.getInstance()
						.getNodeMapsByNodeDevId(context, dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcDypdxEntity entity : dypdxs) {
					ExcelEntity4Dypdx entity4Dypdx = new ExcelEntity4Dypdx();
					entity4Dypdx.setSbid(entity.getSbid());
					entity4Dypdx.setSbmc(entity.getSbmc());
					entity4Dypdx.setYxbh(entity.getYxbh());
					entity4Dypdx.setDxmpid(entity.getDxmpid());
					entity4Dypdx.setDldj(entity.getDldj());
					entity4Dypdx.setSsds(entity.getSsds());
					entity4Dypdx.setYwdw(entity.getYwdw());
					entity4Dypdx.setWhbz(entity.getWhbz());
					entity4Dypdx.setLx(entity.getLx());
					entity4Dypdx.setTyrq(entity.getTyrq());
					entity4Dypdx.setSbzt(entity.getSbzt());
					entity4Dypdx.setZcxz(entity.getZcxz());
					entity4Dypdx.setZcdw(entity.getZcdw());
					entity4Dypdx.setXh(entity.getXh());
					entity4Dypdx.setSccj(entity.getSccj());
					entity4Dypdx.setCcbh(entity.getCcbh());
					entity4Dypdx.setCcrq(entity.getCcrq());
					entity4Dypdx.setJddz(entity.getJddz());
					entity4Dypdx.setBz(entity.getBz());
					entity4Dypdx.setCjsj(entity.getCjsj());
					entity4Dypdx.setMarkerno(dypdxId2NodeMap.get(entity.getObjId()).getMarkerNo());
					list.add(entity4Dypdx);
				}
			}
			String fileName = "dypdx_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
	 * 导出井盖
	 */
	public void exportWorkWellCover() {
		String filePath = this.rootExportFilePath + File.separator + "井盖_"
				+ DateUtils.date2Str(DateUtils.yyyymmddhhmmss) + ".xls";
		try {
			ExcelWriter<ExcelEntity4WorkWellCover> excelWriter = new ExcelWriter<ExcelEntity4WorkWellCover>(filePath);
			List<ExcelEntity4WorkWellCover> list = new ArrayList<>();
			EcWorkWellCoverEntity queryEntity = new EcWorkWellCoverEntity();
			queryEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			List<EcWorkWellCoverEntity> workwells = dbUtil.excuteQuery(queryEntity);
			if (null != workwells && !workwells.isEmpty()) {
				StringBuffer subBuffer = new StringBuffer();
				for (int i = 0, len = workwells.size(); i < len; i++) {
					EcWorkWellCoverEntity entity = workwells.get(i);
					if (i > 0) {
						subBuffer.append("','" + entity.getSsgj());
					} else {
						subBuffer.append(entity.getSsgj());
					}
				}
				Map<String, EcNodeEntity> workwellId2NodeMap = ExportExcelUtils.getInstance()
						.getNodeMapsByNodeDevId(context, dbUtil, subBuffer.toString(), mOid2NodeMap);
				for (EcWorkWellCoverEntity entity : workwells) {
					ExcelEntity4WorkWellCover entity4WorkWellCover = new ExcelEntity4WorkWellCover();
					entity4WorkWellCover.setXh(entity.getXh());
					entity4WorkWellCover.setSbmc(entity.getSbmc());
					entity4WorkWellCover.setSsgj(entity.getSsgj());
					entity4WorkWellCover.setBiz(entity.getBiz());
					entity4WorkWellCover.setYwdw(entity.getYwdw());
					entity4WorkWellCover.setWhbz(entity.getWhbz());
					entity4WorkWellCover.setSszrq(entity.getSszrq());
					entity4WorkWellCover.setJgcd(entity.getJgcd() + "");
					entity4WorkWellCover.setJgkd(entity.getJgkd() + "");
					entity4WorkWellCover.setJghd(entity.getJghd() + "");
					entity4WorkWellCover.setCjsj(entity.getCjsj());
					entity4WorkWellCover.setBz(entity.getBz());
					entity4WorkWellCover.setMarkerno(workwellId2NodeMap.get(entity.getSsgj()).getMarkerNo());
					list.add(entity4WorkWellCover);
				}
			}
			String fileName = "workwellcover_" + DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
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
