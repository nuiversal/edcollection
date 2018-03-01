package com.winway.android.edcollection.adding.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;

import com.winway.android.edcollection.adding.entity.BindTargetType;
import com.winway.android.edcollection.adding.entity.LineDeviceType;
import com.winway.android.edcollection.adding.entity.LineTypeEnum;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.adding.entity.RightPro;
import com.winway.android.edcollection.adding.entity.TechFeatureType;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.ResourceEnumUtil;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.util.DataDealUtil;
import com.winway.android.edcollection.login.entity.EdpOrgInfoEntity;
import com.winway.android.edcollection.login.entity.EdpParamInfoEntity;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.edcollection.project.entity.EcDistBoxEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.edcollection.project.entity.EmProjectEntity;
import com.winway.android.util.DateUtils;
import com.winway.android.util.StringUtils;

/**
 * 表格控件数据工具类（将数据对象转化成符合表格控件使用的数据）
 * 
 * @author mr-lao
 *
 */
public class TableDataUtil {
	static class LongitudeLatitudeEntity {
		public double longitude;
		public double latitude;
	}

	public static LongitudeLatitudeEntity parseGeom(String geom) {
		// {"coordinates":[111.83359642,21.57469313]}
		if (geom == null) {
			return null;
		}
		int begin = geom.indexOf("[") + 1;
		int end = geom.lastIndexOf("]");
		LongitudeLatitudeEntity ll = new LongitudeLatitudeEntity();
		String llstr = geom.substring(begin, end);
		String[] split = llstr.split(",");
		ll.longitude = Double.parseDouble(split[0]);
		ll.latitude = Double.parseDouble(split[1]);
		return ll;
	}

	public static DataCenterImpl treeDataCenter = null;
	@SuppressLint("SimpleDateFormat")
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

	/**
	 * 拿到节点实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 *            不能为空
	 * @param lineid
	 *            可以为空，如果线路ID不为空，则会显示找到当前节点在线路中的序号
	 * @return
	 */
	public static ArrayList<Object[]> getByEcNodeEntity(EcNodeEntity ecEntity, String lineid) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		LongitudeLatitudeEntity geom = parseGeom(StringUtils.nullStrToEmpty(ecEntity.getGeom()));
		String td1[] = new String[] { "标识器标号", StringUtils.nullStrToEmpty(ecEntity.getMarkerNo()) };
		String td2[] = new String[] { "经度", "" + geom.longitude };
		String td3[] = new String[] { "纬度", "" + geom.latitude };
		String tdaz[] = new String[] { "安装位置", StringUtils.nullStrToEmpty(ecEntity.getInstallPosition()) };
		String td4[] = new String[] { "地理位置", StringUtils.nullStrToEmpty(ecEntity.getGeoLocation()) };
		String td5[] = new String[] { "深度[cm]", StringUtils.nullStrToEmpty(ecEntity.getCableDeepth()) };
		String td_altitude[] = new String[] { "高程", StringUtils.nullStrToEmpty(ecEntity.getAltitude()) };
		String ponittype = "";
		if (ecEntity.getMarkerType() != null) {
			if (ecEntity.getMarkerType() == 1) {
				ponittype = NodeMarkerType.BSQ.getName();
			} else if (ecEntity.getMarkerType() == 2) {
				ponittype = NodeMarkerType.BSD.getName();
			} else if (ecEntity.getMarkerType() == 0) {
				ponittype = NodeMarkerType.XND.getName();
			} else if(ecEntity.getMarkerType() == 3){
				ponittype = NodeMarkerType.GT.getName();
			}else if(ecEntity.getMarkerType() == 4){
				ponittype = NodeMarkerType.AJH.getName();
			}
		}
//		String td13[] = new String[] { "敷设类型", StringUtils.nullStrToEmpty(ecEntity.getLayType()) };
		String lineorder = "";
		EcLineNodesEntity lineNode = treeDataCenter.getLineNode(ecEntity.getOid(), lineid, null, null);
		if (null != lineNode) {
			lineorder = lineNode.getNIndex() + "";
		}
		String td5_1[] = new String[] { "线路序号", lineorder };
		String td6_0[] = new String[] { "路径点类型", ponittype };
		String td6[] = new String[] { "沟槽宽度[cm]", StringUtils.nullStrToEmpty(ecEntity.getCableWidth()) };
		String td7[] = new String[] { "设备描述", StringUtils.nullStrToEmpty(ecEntity.getDeviceDesc()) };
		String td8[] = new String[] { "同沟电缆回路数", StringUtils.nullStrToEmpty(ecEntity.getCableLoop()) };
		String orgname = "";
		if (treeDataCenter != null) {
			EdpOrgInfoEntity edpOrgInfoEntity = treeDataCenter.getOrgCenter().getOrgName(ecEntity.getOrgid());
			if (null != edpOrgInfoEntity) {
				orgname = "" + edpOrgInfoEntity.getOrgName();
			}
		}
		String td9[] = new String[] { "所属机构", orgname };
		String td10[] = new String[] { "放置时间",
				ecEntity.getPlaceMarkerTime() == null ? "" : DateUtils.date2Str(ecEntity.getPlaceMarkerTime(), dateFormat) };
		String td11[] = new String[] { "最后更新时间",
				ecEntity.getUpdateTime() == null ? "" : DateUtils.date2Str(ecEntity.getUpdateTime(), dateFormat) };
		String td12[] = new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getRemark()) };

		list.add(td1);
		list.add(td2);
		list.add(td3);
		list.add(td4);
		list.add(td_altitude);
		list.add(td5);
		list.add(td5_1);
		list.add(td6);
		list.add(td6_0);
		list.add(td7);
		list.add(td8);
		list.add(tdaz);
//		list.add(td13);
		// 增加同沟电缆信息
		List<EcLineEntity> acrossLineInNode = treeDataCenter.getAcrossLineInNode(ecEntity.getOid(), null, null);
		if (null != acrossLineInNode && !acrossLineInNode.isEmpty()) {
			for (EcLineEntity ecLineEntity : acrossLineInNode) {
				String linemsg[] = new String[] { "同沟电缆", ecLineEntity.getName() };
				list.add(linemsg);
			}
		}
		list.add(td9);
		list.add(td10);
		list.add(td11);
		list.add(td12);
		return list;
	}

	/**
	 * 拿到标签实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcLineLabelEntity(EcLineLabelEntity ecEntity) {
		String bindTarget = "";
		if (ecEntity.getDeviceType() == null) {
			bindTarget = BindTargetType.dlbt.getType();
		} else {
			if (ecEntity.getBindTarget() != null) {
				if (ecEntity.getBindTarget() == 1) {
					bindTarget = BindTargetType.dlbt.getType();
				} else if (ecEntity.getBindTarget() == 2) {
					bindTarget = BindTargetType.zjjt.getType();
				} else {
					bindTarget = BindTargetType.zdsb.getType();
				}
			} else if ((ResourceEnum.DLJ.getValue() + "").equals(ecEntity.getDeviceType())) {
				bindTarget = "工井";
			} else {
				bindTarget = BindTargetType.dlbt.getType();
			}
		}
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "绑扎对象", bindTarget });
		list.add(new String[] {"设备名称",StringUtils.nullStrToEmpty(ecEntity.getDevName())});
		list.add(new String[] { "标签ID", StringUtils.nullStrToEmpty(ecEntity.getLabelNo()) });
//		list.add(new String[] { "安装序号", StringUtils.nullStrToEmpty(ecEntity.getSequence()) });
		list.add(new String[] { "与上一标签距离", StringUtils.nullStrToEmpty(ecEntity.getDistance()) });
		list.add(new String[] { "最近工井方向", StringUtils.nullStrToEmpty(ecEntity.getZjgjfx()) });
		list.add(new String[] { "距最近工井距离", StringUtils.nullStrToEmpty(ecEntity.getJzjgjjl()) });
		list.add(new String[] { "附属设施情况", StringUtils.nullStrToEmpty(ecEntity.getFsssqk()) });
		list.add(new String[] { "位置地图", StringUtils.nullStrToEmpty(ecEntity.getWzdt()) });
		list.add(new String[] { "周围通道分布情况", StringUtils.nullStrToEmpty(ecEntity.getZwtdfbqk()) });
		list.add(new String[] { "通道内电缆情况", StringUtils.nullStrToEmpty(ecEntity.getTdndlqk()) });
		list.add(new String[] { "地标路口信息", StringUtils.nullStrToEmpty(ecEntity.getDblkxx()) });
//		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		return list;
	}

	/**
	 * 拿到线路实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcLineEntity(EcLineEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		String lineType = "";
		if (ecEntity.getLineType() != null) {
			if (ecEntity.getLineType() == 1) {
				lineType = LineTypeEnum.dlgcxl.getType();
			} else if (ecEntity.getLineType() == 2) {
				lineType = LineTypeEnum.jkgcxl.getType();
			} else if (ecEntity.getLineType() == 3) {
				lineType = LineTypeEnum.dl.getType();
			} else {
				lineType = LineTypeEnum.jkdx.getType();
			}
		}

		LongitudeLatitudeEntity geom = parseGeom(ecEntity.getGeom());
		list.add(new String[] { "线路名称", StringUtils.nullStrToEmpty(ecEntity.getName()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getVoltage()) });
		String subName = treeDataCenter.getSubName(ecEntity.getStartStation());
		list.add(new String[] { "所属变电站", StringUtils.nullStrToEmpty(subName) });
		list.add(new String[] { "结束变电站", StringUtils.nullStrToEmpty(ecEntity.getEndStation()) });
		list.add(new String[] { "线路偏移", StringUtils.nullStrToEmpty(ecEntity.getDrawOffset()) });
		list.add(new String[] { "线路分组", StringUtils.nullStrToEmpty(ecEntity.getGroupName()) });
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "经度", geom == null ? "" : geom.longitude + "" });
		list.add(new String[] { "纬度", geom == null ? "" : geom.latitude + "" });
		list.add(new String[] { "维护部门", StringUtils.nullStrToEmpty(ecEntity.getMaintenanceDepartment()) });
		list.add(new String[] { "设备型号", StringUtils.nullStrToEmpty(ecEntity.getEquipmentModel()) });
		list.add(new String[] { "产权性质", StringUtils.nullStrToEmpty(ecEntity.getPropertyRights()) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getManufacturer()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getManufacturingNumber()) });
		list.add(new String[] {
				"投运日期",
				ecEntity.getCommissioningDate() == null ? "" : DateUtils.date2Str(ecEntity.getCommissioningDate(),
						dateFormat) });
		list.add(new String[] { "出厂日期",
				ecEntity.getAccomplishDate() == null ? "" : DateUtils.date2Str(ecEntity.getAccomplishDate(), dateFormat) });
		list.add(new String[] { "图上长度(m)", StringUtils.nullStrToEmpty(ecEntity.getDiagramLength()) });
		list.add(new String[] { "实测长度(m)", StringUtils.nullStrToEmpty(ecEntity.getMeasuredLength()) });
		list.add(new String[] { "敷设方式", StringUtils.nullStrToEmpty(ecEntity.getLayingMode()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getRemarks()) });
		list.add(new String[] { "创建时间",
				ecEntity.getCreateTime() == null ? "" : DateUtils.date2Str(ecEntity.getCreateTime(), dateFormat) });
		list.add(new String[] { "更新时间",
				ecEntity.getUpdateTime() == null ? "" : DateUtils.date2Str(ecEntity.getUpdateTime(), dateFormat) });
		list.add(new String[] { "序号", StringUtils.nullStrToEmpty(ecEntity.getSortNum()) });
		list.add(new String[] { "线路类型", lineType });
		list.add(new String[] { "导线类型", StringUtils.nullStrToEmpty(ecEntity.getWireMaterial()) });

		return list;
	}

	/**
	 * 拿到变电站实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcSubStationEntity(EcSubstationEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		String rightPro = "";
		if (ecEntity.getRightPro() != null) {
			if (ecEntity.getRightPro() == 1) {
				rightPro = RightPro.GY.getName();
			} else {
				rightPro = RightPro.ZY.getName();
			}
		}

		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getVoltage()) });
		list.add(new String[] { "变电站编号", StringUtils.nullStrToEmpty(ecEntity.getStationNo()) });
		list.add(new String[] { "变电站名称", StringUtils.nullStrToEmpty(ecEntity.getName()) });
		list.add(new String[] { "产权属性", StringUtils.nullStrToEmpty(rightPro) });
		return list;
	}

	/**
	 * 拿到电缆中间接头实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcMiddleJointEntity(EcMiddleJointEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		String techfeature = "";
		if (ecEntity.getTechFeature() != null) {
			if (ecEntity.getTechFeature() == 1) {
				techfeature = TechFeatureType.lss.getValue();
			} else if (ecEntity.getTechFeature() == 2) {
				techfeature = TechFeatureType.rss.getValue();
			} else if (ecEntity.getTechFeature() == 3) {
				techfeature = TechFeatureType.yzs.getValue();
			} else {
				techfeature = TechFeatureType.cys.getValue();
			}
		}

		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getManufacutrer()) });
		list.add(new String[] { "设备型号", StringUtils.nullStrToEmpty(ecEntity.getDevModel()) });
		list.add(new String[] {"类型",StringUtils.nullStrToEmpty(ecEntity.getJointType()) });
		list.add(new String[] { "投运日期",
				ecEntity.getTyDate() == null ? "" : DateUtils.date2Str(ecEntity.getTyDate(), dateFormat) });
		list.add(new String[] {"厂家联系方式",StringUtils.nullStrToEmpty(ecEntity.getCjlxfs()) });
		list.add(new String[] {"中间接头故障情况",StringUtils.nullStrToEmpty(ecEntity.getZjjtgzqk()) });
		list.add(new String[] {"上次巡检日期",StringUtils.nullStrToEmpty(ecEntity.getScxjrq()) });
		list.add(new String[] {"电缆载流量",StringUtils.nullStrToEmpty(ecEntity.getDlzll()) });
		list.add(new String[] {"中间接头温度",StringUtils.nullStrToEmpty(ecEntity.getZjjtwd()) });
		list.add(new String[] {"接地电流情况",StringUtils.nullStrToEmpty(ecEntity.getJddlqk()) });
		/*list.add(new String[] { "工艺特征", techfeature });
		list.add(new String[] { "生产日期",
				ecEntity.getScDate() == null ? "" : DateUtils.date2Str(ecEntity.getScDate(), dateFormat) });
		list.add(new String[] { "安装单位", StringUtils.nullStrToEmpty(ecEntity.getInstallationUnit()) });
		list.add(new String[] { "施工员", StringUtils.nullStrToEmpty(ecEntity.getWorker()) });*/
		

		return list;
	}

	/**
	 * 拿到工井实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcWorkWellEntity(EcWorkWellEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "工井名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "工程名称", StringUtils.nullStrToEmpty(ecEntity.getGcmc()) });
		list.add(new String[] { "断面信息", StringUtils.nullStrToEmpty(ecEntity.getDmxx()) });
		list.add(new String[] { "盖板厚度", StringUtils.nullStrToEmpty(ecEntity.getGbhd()) });
		list.add(new String[] { "盖板块数", StringUtils.nullStrToEmpty(ecEntity.getGbks()) });
		list.add(new String[] { "下一工井方向", StringUtils.nullStrToEmpty(ecEntity.getXygjfx()) });
		list.add(new String[] { "距下一工井距离", StringUtils.nullStrToEmpty(ecEntity.getJxygjjl()) });
		list.add(new String[] { "附属设施情况", StringUtils.nullStrToEmpty(ecEntity.getFsssqk()) });
		list.add(new String[] { "通道段名称", StringUtils.nullStrToEmpty(ecEntity.getTddmc()) });
		list.add(new String[] { "距起点位置", StringUtils.nullStrToEmpty(ecEntity.getJqdwz()) });
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "所属责任区", StringUtils.nullStrToEmpty(ecEntity.getSszrq()) });
		list.add(new String[] { "工井形状", StringUtils.nullStrToEmpty(ecEntity.getGjxz()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(ecEntity.getDqtz()) });
		list.add(new String[] { "地理位置", StringUtils.nullStrToEmpty(ecEntity.getDlwz()) });
		list.add(new String[] { "井位置", StringUtils.nullStrToEmpty(ecEntity.getJwz()) });
		list.add(new String[] { "井类型", StringUtils.nullStrToEmpty(ecEntity.getJlx()) });
		list.add(new String[] { "结构", StringUtils.nullStrToEmpty(ecEntity.getJg()) });
		list.add(new String[] { "井面高程", StringUtils.nullStrToEmpty(ecEntity.getJmgc()) });
		list.add(new String[] { "内底高程", StringUtils.nullStrToEmpty(ecEntity.getNdgc()) });
		list.add(new String[] { "电缆离地面距离", StringUtils.nullStrToEmpty(ecEntity.getDlldjl()) });
		list.add(new String[] { "电缆离井底距离", StringUtils.nullStrToEmpty(ecEntity.getDlljdjl()) });
		list.add(new String[] { "电缆离左侧距离", StringUtils.nullStrToEmpty(ecEntity.getDllzcjl()) });
		list.add(new String[] { "电缆离右侧距离", StringUtils.nullStrToEmpty(ecEntity.getDllycjl()) });
		list.add(new String[] { "工井长度", StringUtils.nullStrToEmpty(ecEntity.getGjcd()) });
		list.add(new String[] { "工井宽度", StringUtils.nullStrToEmpty(ecEntity.getGjkd()) });
		list.add(new String[] { "工井深度", StringUtils.nullStrToEmpty(ecEntity.getGjsd()) });
		list.add(new String[] { "井盖形状", StringUtils.nullStrToEmpty(ecEntity.getJgxz()) });
		list.add(new String[] { "井盖尺寸", StringUtils.nullStrToEmpty(ecEntity.getJgcc()) });
		list.add(new String[] { "井盖材质", StringUtils.nullStrToEmpty(ecEntity.getJgcz()) });
		list.add(new String[] { "井盖生产厂家", StringUtils.nullStrToEmpty(ecEntity.getJgsccj()) });
		list.add(new String[] { "井盖出厂日期",
				ecEntity.getJgccrq() == null ? "" : DateUtils.date2Str(ecEntity.getJgccrq(), dateFormat) });
		list.add(new String[] { "平台层数", StringUtils.nullStrToEmpty(ecEntity.getPtcs()) });
		list.add(new String[] { "施工单位", StringUtils.nullStrToEmpty(ecEntity.getSgdw()) });
		list.add(new String[] { "施工日期", ecEntity.getSgrq() == null ? "" : DateUtils.date2Str(ecEntity.getSgrq(), dateFormat) });
		list.add(new String[] { "峻工日期", ecEntity.getJgrq() == null ? "" : DateUtils.date2Str(ecEntity.getJgrq(), dateFormat) });
		list.add(new String[] { "图纸编号", StringUtils.nullStrToEmpty(ecEntity.getTzbh()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "资产编号", StringUtils.nullStrToEmpty(ecEntity.getZcbh()) });
		list.add(new String[] { "专业分类", StringUtils.nullStrToEmpty(ecEntity.getZyfl()) });
		list.add(new String[] { "采集时间", ecEntity.getCjsj() == null ? "" : DateUtils.date2Str(ecEntity.getCjsj(), dateFormat) });
		list.add(new String[] { "更新时间", ecEntity.getGxsj() == null ? "" : DateUtils.date2Str(ecEntity.getGxsj(), dateFormat) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 拿到工井实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcDistBoxEntity(EcDistBoxEntity ecEntity) {
		String jointType = "";
		if (ecEntity.getJointType() != null) {
			if (ecEntity.getJointType() == 1) {
				jointType = LineDeviceType.FJX.getName();
			} else {
				jointType = LineDeviceType.HWG.getName();
			}
		}
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getDevName()) });
		list.add(new String[] { "接线类型", jointType });
		list.add(new String[] { "总回路数", StringUtils.nullStrToEmpty(ecEntity.getLinesCount()) });
		return list;
	}

	/**
	 * 拿到杆塔实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcTowerEntity(EcTowerEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "创建时间",
				ecEntity.getCreateTime() == null ? "" : DateUtils.date2Str(ecEntity.getCreateTime(), dateFormat) });
		list.add(new String[] { "更新时间",
				ecEntity.getUpdateTime() == null ? "" : DateUtils.date2Str(ecEntity.getUpdateTime(), dateFormat) });
		list.add(new String[] { "杆塔编号", StringUtils.nullStrToEmpty(ecEntity.getTowerNo()) });
		list.add(new String[] { "维护部门", StringUtils.nullStrToEmpty(ecEntity.getMaintenanceDepartment()) });
		list.add(new String[] { "运行状态", StringUtils.nullStrToEmpty(ecEntity.getRunningStatus()) });
		list.add(new String[] { "产权性质", StringUtils.nullStrToEmpty(ecEntity.getPropertyRights()) });
		list.add(new String[] {
				"投运日期",
				ecEntity.getCommissioningDate() == null ? "" : DateUtils.date2Str(ecEntity.getCommissioningDate(),
						dateFormat) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getManufacturer()) });
		list.add(new String[] { "设备型号", StringUtils.nullStrToEmpty(ecEntity.getEquipmentModel()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getManufacturingNumber()) });
		list.add(new String[] { "出厂日期",
				ecEntity.getAccomplishDate() == null ? "" : DateUtils.date2Str(ecEntity.getAccomplishDate(), dateFormat) });
		list.add(new String[] { "杆塔材质", StringUtils.nullStrToEmpty(ecEntity.getMaterial()) });
		list.add(new String[] { "转角度数", StringUtils.nullStrToEmpty(ecEntity.getCornerDegree()) });
		list.add(new String[] { "杆塔全高(m)", StringUtils.nullStrToEmpty(ecEntity.getHeight()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getRemarks()) });
		return list;
	}

	private static void addPrjnameAndOrgname(String prjid, String orgid, ArrayList<Object[]> list) {
		EmProjectEntity projectEntity = treeDataCenter.getOrgCenter().getPrjName(prjid);
		list.add(new String[] { "所属项目", null == projectEntity ? "" : projectEntity.getPrjName() + "" });
		EdpOrgInfoEntity orgInfoEntity = treeDataCenter.getOrgCenter().getOrgName(orgid);
		list.add(new String[] { "所属机构", orgInfoEntity == null ? "" : orgInfoEntity.getOrgName() });
	}

	public static GlobalCommonBll globalBLL = null;

	private static void addYwdwAndWhbx(String ywdw, String hwbz, ArrayList<Object[]> list) {
		if (null == globalBLL) {
			return;
		}
		EdpParamInfoEntity ywdwEP = globalBLL.get(ywdw);
		EdpParamInfoEntity hwbzEP = globalBLL.get(hwbz);
		list.add(new String[] { "运维单位", null == ywdwEP ? "" : ywdwEP.getParamName() + "" });
		list.add(new String[] { "维护班组", null == hwbzEP ? "" : hwbzEP.getParamName() + "" });
	}

	/**
	 * 拿到配电室实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcDistributionRoomEntity(EcDistributionRoomEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(ecEntity.getYxbh()) });
		list.add(new String[] { "铭牌ID", StringUtils.nullStrToEmpty(ecEntity.getDxmpid()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getDydj()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(ecEntity.getSbzt()) });
		list.add(new String[] { "投运日期", null == ecEntity.getTyrq() ? "" : dateFormat.format(ecEntity.getTyrq()) });
		list.add(new String[] { "是否农网", StringUtils.nullStrToEmpty(ecEntity.getSfnw()) });
		list.add(new String[] { "是否代维", StringUtils.nullStrToEmpty(ecEntity.getSfdw()) });
		list.add(new String[] { "配变台数", StringUtils.nullStrToEmpty(ecEntity.getPbts()) });
		list.add(new String[] { "配变总容量", StringUtils.nullStrToEmpty(ecEntity.getPbzrl()) });
		list.add(new String[] { "无功补偿容量", StringUtils.nullStrToEmpty(ecEntity.getWgbcrl()) });
		list.add(new String[] { "防误方式", StringUtils.nullStrToEmpty(ecEntity.getFwfs()) });
		list.add(new String[] { "是否独立建筑物", StringUtils.nullStrToEmpty(ecEntity.getSfdljzw()) });
		list.add(new String[] { "是否地下站", StringUtils.nullStrToEmpty(ecEntity.getSfdxz()) });
		list.add(new String[] { "接地电阻(Ω)", StringUtils.nullStrToEmpty(ecEntity.getJddz()) });
		list.add(new String[] { "站址", StringUtils.nullStrToEmpty(ecEntity.getZz()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(ecEntity.getDqtz()) });
		list.add(new String[] { "重要程度", StringUtils.nullStrToEmpty(ecEntity.getZycd()) });
		list.add(new String[] { "工程编号", StringUtils.nullStrToEmpty(ecEntity.getGcbh()) });
		list.add(new String[] { "工程名称", StringUtils.nullStrToEmpty(ecEntity.getGcmc()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "采集时间", null == ecEntity.getCjsj() ? "" : dateFormat.format(ecEntity.getCjsj()) });
		list.add(new String[] { "更新时间", null == ecEntity.getGxsj() ? "" : dateFormat.format(ecEntity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 拿到变压器实体的数据，并将它们放进集合里面去
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcTransformerEntity(EcTransformerEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "创建时间",
				ecEntity.getCreateTime() == null ? "" : DateUtils.date2Str(ecEntity.getCreateTime(), dateFormat) });
		list.add(new String[] { "更新时间",
				ecEntity.getUpdateTime() == null ? "" : DateUtils.date2Str(ecEntity.getUpdateTime(), dateFormat) });
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "维护部门", StringUtils.nullStrToEmpty(ecEntity.getMaintenanceDepartment()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getVoltage()) });
		list.add(new String[] { "运行状态", StringUtils.nullStrToEmpty(ecEntity.getRunningStatus()) });
		list.add(new String[] { "所属站房", StringUtils.nullStrToEmpty(ecEntity.getStationHouse()) });
		list.add(new String[] { "所属间隔", StringUtils.nullStrToEmpty(ecEntity.getSpace()) });
		list.add(new String[] { "设备型号", StringUtils.nullStrToEmpty(ecEntity.getEquipmentModel()) });
		list.add(new String[] { "额定容量(kVA)", StringUtils.nullStrToEmpty(ecEntity.getRatedCapacity()) });
		list.add(new String[] { "产权性质", StringUtils.nullStrToEmpty(ecEntity.getPropertyRights()) });
		list.add(new String[] {
				"投运日期",
				ecEntity.getCommissioningDate() == null ? "" : DateUtils.date2Str(ecEntity.getCommissioningDate(),
						dateFormat) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getManufacturer()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getManufacturingNumber()) });
		list.add(new String[] { "用户重要等级", StringUtils.nullStrToEmpty(ecEntity.getUserImportantGrade()) });
		list.add(new String[] { "专/公标志", StringUtils.nullStrToEmpty(ecEntity.getZgMark()) });
		list.add(new String[] { "客户名称", StringUtils.nullStrToEmpty(ecEntity.getCustomerName()) });
		list.add(new String[] { "客户编号", StringUtils.nullStrToEmpty(ecEntity.getCustomerNumber()) });
		list.add(new String[] { "空载电流(A)", StringUtils.nullStrToEmpty(ecEntity.getNoLoadCurrent()) });
		list.add(new String[] { "空载损耗(kW)", StringUtils.nullStrToEmpty(ecEntity.getNoLoadLoss()) });
		list.add(new String[] { "负载损耗(kW)", StringUtils.nullStrToEmpty(ecEntity.getLoadLoss()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getRemarks()) });
		return list;
	}

	/**
	 * 环网柜
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcHwgEntity(EcHwgEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(ecEntity.getYxbh()) });
		list.add(new String[] { "铭牌ID", StringUtils.nullStrToEmpty(ecEntity.getDxmpid()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getDldj()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(ecEntity.getSbzt()) });
		list.add(new String[] { "投运日期", null == ecEntity.getTyrq() ? "" : dateFormat.format(ecEntity.getTyrq()) });
		list.add(new String[] { "是否农网", StringUtils.nullStrToEmpty(ecEntity.getSfnw()) });
		list.add(new String[] { "是否代维", StringUtils.nullStrToEmpty(ecEntity.getSfdw()) });
		list.add(new String[] { "型号", StringUtils.nullStrToEmpty(ecEntity.getXh()) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getSccj()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getCcbh()) });
		list.add(new String[] { "出厂日期", null == ecEntity.getCcrq() ? "" : dateFormat.format(ecEntity.getCcrq()) });
		list.add(new String[] { "接地电阻(Ω)", StringUtils.nullStrToEmpty(ecEntity.getJddz()) });
		list.add(new String[] { "备用进出线间隔数", StringUtils.nullStrToEmpty(ecEntity.getByjcxjgs()) });
		list.add(new String[] { "站址", StringUtils.nullStrToEmpty(ecEntity.getZz()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(ecEntity.getDqtz()) });
		list.add(new String[] { "重要程度", StringUtils.nullStrToEmpty(ecEntity.getZycd()) });
		list.add(new String[] { "工程编号", StringUtils.nullStrToEmpty(ecEntity.getGcbh()) });
		list.add(new String[] { "工程名称", StringUtils.nullStrToEmpty(ecEntity.getGcmc()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "设备增加方式", StringUtils.nullStrToEmpty(ecEntity.getSbzjfs()) });
		list.add(new String[] { "采集时间", null == ecEntity.getCjsj() ? "" : dateFormat.format(ecEntity.getCjsj()) });
		list.add(new String[] { "更新时间", null == ecEntity.getGxsj() ? "" : dateFormat.format(ecEntity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 箱式变电站
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcXsbdzEntity(EcXsbdzEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(ecEntity.getYxbh()) });
		list.add(new String[] { "铭牌ID", StringUtils.nullStrToEmpty(ecEntity.getDxmpid()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getDydj()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(ecEntity.getSbzt()) });
		list.add(new String[] { "投运日期", null == ecEntity.getTyrq() ? "" : dateFormat.format(ecEntity.getTyrq()) });
		list.add(new String[] { "是否农网", StringUtils.nullStrToEmpty(ecEntity.getSfnw()) });
		list.add(new String[] { "是否代维", StringUtils.nullStrToEmpty(ecEntity.getSfdw()) });
		list.add(new String[] { "箱变类型", StringUtils.nullStrToEmpty(ecEntity.getXblx()) });
		list.add(new String[] { "是否具有环网", StringUtils.nullStrToEmpty(ecEntity.getSfjyhw()) });
		list.add(new String[] { "型号", StringUtils.nullStrToEmpty(ecEntity.getXh()) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getSccj()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getCcbh()) });
		list.add(new String[] { "出厂日期", null == ecEntity.getCcrq() ? "" : dateFormat.format(ecEntity.getCcrq()) });
		list.add(new String[] { "配变总容量", StringUtils.nullStrToEmpty(ecEntity.getPbzrl()) });
		list.add(new String[] { "接地电阻(Ω)", StringUtils.nullStrToEmpty(ecEntity.getJddz()) });
		list.add(new String[] { "站址", StringUtils.nullStrToEmpty(ecEntity.getZz()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(ecEntity.getDqtz()) });
		list.add(new String[] { "重要程度", StringUtils.nullStrToEmpty(ecEntity.getZycd()) });
		list.add(new String[] { "工程编号", StringUtils.nullStrToEmpty(ecEntity.getGcbh()) });
		list.add(new String[] { "工程名称", StringUtils.nullStrToEmpty(ecEntity.getGcmc()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "设备增加方式", StringUtils.nullStrToEmpty(ecEntity.getSbzjfs()) });
		list.add(new String[] { "采集时间", null == ecEntity.getCjsj() ? "" : dateFormat.format(ecEntity.getCjsj()) });
		list.add(new String[] { "更新时间", null == ecEntity.getGxsj() ? "" : dateFormat.format(ecEntity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 电缆分支箱
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcDlfzxEntity(EcDlfzxEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(ecEntity.getYxbh()) });
		list.add(new String[] { "铭牌ID", StringUtils.nullStrToEmpty(ecEntity.getDxmpid()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getDldj()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(ecEntity.getSbzt()) });
		list.add(new String[] { "投运日期", null == ecEntity.getTyrq() ? "" : dateFormat.format(ecEntity.getTyrq()) });
		list.add(new String[] { "是否农网", StringUtils.nullStrToEmpty(ecEntity.getSfnw()) });
		list.add(new String[] { "是否代维", StringUtils.nullStrToEmpty(ecEntity.getSfdw()) });
		list.add(new String[] { "类型", StringUtils.nullStrToEmpty(ecEntity.getLx()) });
		list.add(new String[] { "型号", StringUtils.nullStrToEmpty(ecEntity.getXh()) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getSccj()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getCcbh()) });
		list.add(new String[] { "出厂日期", null == ecEntity.getCcrq() ? "" : dateFormat.format(ecEntity.getCcrq()) });
		list.add(new String[] { "接地电阻(Ω)", StringUtils.nullStrToEmpty(ecEntity.getJddz()) });
		list.add(new String[] { "站址", StringUtils.nullStrToEmpty(ecEntity.getZz()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(ecEntity.getDqtz()) });
		list.add(new String[] { "重要程度", StringUtils.nullStrToEmpty(ecEntity.getZycd()) });
		list.add(new String[] { "工程编号", StringUtils.nullStrToEmpty(ecEntity.getGcbh()) });
		list.add(new String[] { "工程名称", StringUtils.nullStrToEmpty(ecEntity.getGcmc()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "设备增加方式", StringUtils.nullStrToEmpty(ecEntity.getSbzjfs()) });
		list.add(new String[] { "采集时间", null == ecEntity.getCjsj() ? "" : dateFormat.format(ecEntity.getCjsj()) });
		list.add(new String[] { "更新时间", null == ecEntity.getGxsj() ? "" : dateFormat.format(ecEntity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 低压电缆分支箱
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcDydlfzxEntity(EcDydlfzxEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(ecEntity.getYxbh()) });
		list.add(new String[] { "铭牌ID", StringUtils.nullStrToEmpty(ecEntity.getDxmpid()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getDldj()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "设备类型", StringUtils.nullStrToEmpty(ecEntity.getSblx()) });
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(ecEntity.getSbzt()) });
		list.add(new String[] { "投运日期", null == ecEntity.getTyrq() ? "" : dateFormat.format(ecEntity.getTyrq()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "型号", StringUtils.nullStrToEmpty(ecEntity.getXh()) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getSccj()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getCcbh()) });
		list.add(new String[] { "出厂日期", null == ecEntity.getCcrq() ? "" : dateFormat.format(ecEntity.getCcrq()) });
		list.add(new String[] { "额定电压", StringUtils.nullStrToEmpty(ecEntity.getEddy()) });
		list.add(new String[] { "额定电流", StringUtils.nullStrToEmpty(ecEntity.getEddl()) });
		list.add(new String[] { "采集时间", null == ecEntity.getCjsj() ? "" : dateFormat.format(ecEntity.getCjsj()) });
		list.add(new String[] { "更新时间", null == ecEntity.getGxsj() ? "" : dateFormat.format(ecEntity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 低压配电箱
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcDypdxEntity(EcDypdxEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(ecEntity.getYxbh()) });
		list.add(new String[] { "铭牌ID", StringUtils.nullStrToEmpty(ecEntity.getDxmpid()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getDldj()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(ecEntity.getSbzt()) });
		list.add(new String[] { "投运日期", null == ecEntity.getTyrq() ? "" : dateFormat.format(ecEntity.getTyrq()) });
		list.add(new String[] { "接地电阻(Ω)", StringUtils.nullStrToEmpty(ecEntity.getJddz()) });
		list.add(new String[] { "类型", StringUtils.nullStrToEmpty(ecEntity.getLx()) });
		list.add(new String[] { "型号", StringUtils.nullStrToEmpty(ecEntity.getXh()) });
		list.add(new String[] { "生产厂家", StringUtils.nullStrToEmpty(ecEntity.getSccj()) });
		list.add(new String[] { "出厂编号", StringUtils.nullStrToEmpty(ecEntity.getCcbh()) });
		list.add(new String[] { "出厂日期", null == ecEntity.getCcrq() ? "" : dateFormat.format(ecEntity.getCcrq()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "采集时间", null == ecEntity.getCjsj() ? "" : dateFormat.format(ecEntity.getCjsj()) });
		list.add(new String[] { "更新时间", null == ecEntity.getGxsj() ? "" : dateFormat.format(ecEntity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 开关站
	 * 
	 * @param ecEntity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcKgzEntity(EcKgzEntity ecEntity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		addPrjnameAndOrgname(ecEntity.getPrjid(), ecEntity.getOrgid(), list);
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(ecEntity.getSbmc()) });
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(ecEntity.getYxbh()) });
		list.add(new String[] { "铭牌ID", StringUtils.nullStrToEmpty(ecEntity.getDxmpid()) });
		list.add(new String[] { "电压等级", StringUtils.nullStrToEmpty(ecEntity.getDydj()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(ecEntity.getSsds()) });
		}
		addYwdwAndWhbx(ecEntity.getYwdw(), ecEntity.getWhbz(), list);
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(ecEntity.getSbzt()) });
		list.add(new String[] { "投运日期", null == ecEntity.getTyrq() ? "" : dateFormat.format(ecEntity.getTyrq()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(ecEntity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(ecEntity.getZcdw()) });
		list.add(new String[] { "是否智能变电站", StringUtils.nullStrToEmpty(ecEntity.getSfznbdz()) });
		list.add(new String[] { "备用进出线间隔数", StringUtils.nullStrToEmpty(ecEntity.getByjcxjg()) });
		list.add(new String[] { "防误方式", StringUtils.nullStrToEmpty(ecEntity.getFwfs()) });
		list.add(new String[] { "电站重要级别", StringUtils.nullStrToEmpty(ecEntity.getDzzyjb()) });
		list.add(new String[] { "是否代维", StringUtils.nullStrToEmpty(ecEntity.getSfdw()) });
		list.add(new String[] { "是否农网", StringUtils.nullStrToEmpty(ecEntity.getSfnw()) });
		list.add(new String[] { "值班方式", StringUtils.nullStrToEmpty(ecEntity.getZbfs()) });
		list.add(new String[] { "布置方式", StringUtils.nullStrToEmpty(ecEntity.getBzfs()) });
		list.add(new String[] { "污秽等级", StringUtils.nullStrToEmpty(ecEntity.getWhdj()) });
		list.add(new String[] { "工程编号", StringUtils.nullStrToEmpty(ecEntity.getGcbh()) });
		list.add(new String[] { "工程名称", StringUtils.nullStrToEmpty(ecEntity.getGcmc()) });
		list.add(new String[] { "站址", StringUtils.nullStrToEmpty(ecEntity.getAddress()) });
		list.add(new String[] { "采集时间", null == ecEntity.getCjsj() ? "" : dateFormat.format(ecEntity.getCjsj()) });
		list.add(new String[] { "更新时间", null == ecEntity.getGxsj() ? "" : dateFormat.format(ecEntity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(ecEntity.getBz()) });
		return list;
	}

	/**
	 * 通道
	 * 
	 * @param channel
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelEntity(EcChannelEntity channel, String dltd,
			List<EcLineEntity> lineList, EcNodeEntity startNode, EcNodeEntity endNode) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "电力通道", dltd });
		list.add(new String[] { "名称", StringUtils.nullStrToEmpty(channel.getName()) });
		// 所属项目与所属单位
		addPrjnameAndOrgname(channel.getPrjid(), channel.getOrgid(), list);
		list.add(new String[] { "创建时间", channel.getCreateTime() == null ? "" : dateFormat.format(channel.getCreateTime()) });
		list.add(new String[] { "更新时间", channel.getUpdateTime() == null ? "" : dateFormat.format(channel.getUpdateTime()) });

		if (treeDataCenter != null) {
			String start = null != startNode ? StringUtils.nullStrToEmpty(startNode.getMarkerNo()) : StringUtils
					.nullStrToEmpty(channel.getStartDeviceNum());
			String end = null != endNode ? StringUtils.nullStrToEmpty(endNode.getMarkerNo()) : StringUtils
					.nullStrToEmpty(channel.getEndDeviceNum());
			NodeDevicesEntity startNodeDevice = treeDataCenter.getPathNodeDetails(channel.getStartDeviceNum(), null, null);
			Object[] startDevice = DataDealUtil.pickUpFirstNodeDevice(startNodeDevice);
			if (null == startDevice) {
				LineDevicesEntity startNodeLineDevice = treeDataCenter.getNodeLineDevicesList(channel.getStartDeviceNum(),
						null, null);
				Object[] startLineDevice = DataDealUtil.pickUpFirstLineDevice(startNodeLineDevice);
				if (null != startLineDevice) {
					start = startLineDevice[2] + "";
				}
			} else {
				start = startDevice[2] + "";
			}
			NodeDevicesEntity endNodeDevice = treeDataCenter.getPathNodeDetails(channel.getEndDeviceNum(), null, null);
			Object[] endDevice = DataDealUtil.pickUpFirstNodeDevice(endNodeDevice);
			if (null == endDevice) {
				LineDevicesEntity endNodeLineDevice = treeDataCenter.getNodeLineDevicesList(channel.getEndDeviceNum(), null,
						null);
				Object[] endLineDevice = DataDealUtil.pickUpFirstLineDevice(endNodeLineDevice);
				if (null != endLineDevice) {
					end = endLineDevice[2] + "";
				}
			} else {
				end = endDevice[2] + "";
			}
			list.add(new String[] { "起点设备", start });
			list.add(new String[] { "终点设备", end });
		}

		list.add(new String[] { "总容量", StringUtils.nullStrToEmpty(channel.getTotalCapacity()) });
		list.add(new String[] { "剩余容量", StringUtils.nullStrToEmpty(channel.getSurplusCapacity()) });
		list.add(new String[] { "通道类型",
				null == channel.getChannelType() ? "" : ResourceEnumUtil.get(channel.getChannelType()).getName() });
		list.add(new String[] { "所属电力通道",
				null == channel.getSsdltd() ? "" : ResourceEnumUtil.get(channel.getSsdltd()).getName() });
		if (lineList != null && !lineList.isEmpty()) {
			for (int i = 0; i < lineList.size(); i++) {
				list.add(new String[] { "关联线路-" + (i + 1), lineList.get(i) == null ? "" :lineList.get(i).getName() + "" });
			}
		}

		list.add(new String[] { "通道长度(米)", StringUtils.nullStrToEmpty(channel.getChannelLength()) });
		list.add(new String[] { "通道状态",
				channel.getChannelState() == null ? "无状态" : (channel.getChannelState() == 1 ? "已建成的通道" : "规划的通道") });
		list.add(new String[] { "运行单位", StringUtils.nullStrToEmpty(channel.getYxdw()) });
		list.add(new String[] { "所属责任区", StringUtils.nullStrToEmpty(channel.getSszrq()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(channel.getRemarks()) });
		return list;
	}

	/**
	 * 电缆直埋通道
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelDlzm(EcChannelDlzmEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(entity.getYxbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		if (null != globalBLL) {
			EdpParamInfoEntity ywdwEP = globalBLL.get(entity.getYwdw());
			list.add(new String[] { "运维单位", null == ywdwEP ? "" : ywdwEP.getParamName() + "" });
		}
		return list;
	}
	
	/**
	 * 电缆桥通道
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelDlc(EcChannelDlcEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(entity.getYxbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		if (null != globalBLL) {
			EdpParamInfoEntity ywdwEP = globalBLL.get(entity.getYwdw());
			list.add(new String[] { "运维单位", null == ywdwEP ? "" : ywdwEP.getParamName() + "" });
		}
		return list;
	}

	/**
	 * 架空
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelJk(EcChannelJkEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(entity.getYxbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		if (null != globalBLL) {
			EdpParamInfoEntity ywdwEP = globalBLL.get(entity.getYwdw());
			list.add(new String[] { "运维单位", null == ywdwEP ? "" : ywdwEP.getParamName() + "" });
		}
		return list;
	}

	/**
	 * 电缆通道
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelDlgd(EcChannelDlgdEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "模板名称", StringUtils.nullStrToEmpty(entity.getMbmc()) });
		list.add(new String[] { "行数", StringUtils.nullStrToEmpty(entity.getRow()) });
		list.add(new String[] { "列数", StringUtils.nullStrToEmpty(entity.getCol()) });
		list.add(new String[] { "管道编号", StringUtils.nullStrToEmpty(entity.getGdbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		addYwdwAndWhbx(entity.getYwdw(), entity.getWhbz(), list);
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(entity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(entity.getZcdw()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(entity.getDqtz()) });
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(entity.getSbzt()) });
		list.add(new String[] { "投运日期", null == entity.getTyrq() ? "" : dateFormat.format(entity.getTyrq()) });
		list.add(new String[] { "档案名称", StringUtils.nullStrToEmpty(entity.getDamc()) });
		list.add(new String[] { "截面类型", StringUtils.nullStrToEmpty(entity.getJmlx()) });
		list.add(new String[] { "材料", StringUtils.nullStrToEmpty(entity.getCl()) });
		list.add(new String[] { "管径", StringUtils.nullStrToEmpty(entity.getGj()) });
		list.add(new String[] { "管道长度(m)", StringUtils.nullStrToEmpty(entity.getGdcd()) });
		list.add(new String[] { "穿管数量(个)", StringUtils.nullStrToEmpty(entity.getCgsl()) });
		list.add(new String[] { "井数量", StringUtils.nullStrToEmpty(entity.getJsl()) });
		list.add(new String[] { "管道类型", StringUtils.nullStrToEmpty(entity.getGdlx()) });
		list.add(new String[] { "施工单位", StringUtils.nullStrToEmpty(entity.getSgdw()) });
		list.add(new String[] { "施工方式", StringUtils.nullStrToEmpty(entity.getSgfs()) });
		list.add(new String[] { "峻工日期", null == entity.getJgrq() ? "" : dateFormat.format(entity.getJgrq()) });
		list.add(new String[] { "图纸编号", StringUtils.nullStrToEmpty(entity.getTzbh()) });
		list.add(new String[] { "专业分类", StringUtils.nullStrToEmpty(entity.getZyfl()) });
		list.add(new String[] { "管道容量", StringUtils.nullStrToEmpty(entity.getGdrl()) });
		return list;
	}

	/**
	 * 电缆隧道
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelDlsd(EcChannelDlsdEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "隧道编号", StringUtils.nullStrToEmpty(entity.getSdbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		addYwdwAndWhbx(entity.getYwdw(), entity.getYwbz(), list);
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(entity.getSbzt()) });
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(entity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(entity.getZcdw()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(entity.getDqtz()) });
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(entity.getSbzt()) });
		list.add(new String[] { "投运日期", null == entity.getTyrq() ? "" : dateFormat.format(entity.getTyrq()) });
		list.add(new String[] { "档案名称", StringUtils.nullStrToEmpty(entity.getDamc()) });
		list.add(new String[] { "结构型式", StringUtils.nullStrToEmpty(entity.getJgxs()) });
		list.add(new String[] { "悬挂方式", StringUtils.nullStrToEmpty(entity.getXgfs()) });
		list.add(new String[] { "截面类型", StringUtils.nullStrToEmpty(entity.getJmlx()) });
		list.add(new String[] { "断面尺寸", StringUtils.nullStrToEmpty(entity.getDmcc()) });
		list.add(new String[] { "隧道长度(m)", StringUtils.nullStrToEmpty(entity.getSdcd()) });
		list.add(new String[] { "穿管数量(个)", StringUtils.nullStrToEmpty(entity.getCgsl()) });
		list.add(new String[] { "井数量", StringUtils.nullStrToEmpty(entity.getJsl()) });
		list.add(new String[] { "施工单位", StringUtils.nullStrToEmpty(entity.getSgdw()) });
		list.add(new String[] { "峻工日期", null == entity.getJgrq() ? "" : dateFormat.format(entity.getJgrq()) });
		list.add(new String[] { "图纸编号", StringUtils.nullStrToEmpty(entity.getTzbh()) });
		list.add(new String[] { "专业分类", StringUtils.nullStrToEmpty(entity.getZyfl()) });
		list.add(new String[] { "是否安装防火槽盒", StringUtils.nullStrToEmpty(entity.getSfazfhc()) });
		list.add(new String[] { "隧道容量", StringUtils.nullStrToEmpty(entity.getSdrl()) });
		return list;
	}

	/**
	 * 电缆桥
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelDlq(EcChannelDlqEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(entity.getYxbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		addYwdwAndWhbx(entity.getYwdw(), entity.getWhbz(), list);
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(entity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(entity.getZcdw()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(entity.getDqtz()) });
		list.add(new String[] { "材质", StringUtils.nullStrToEmpty(entity.getCz()) });
		list.add(new String[] { "防火材料", StringUtils.nullStrToEmpty(entity.getFhcl()) });
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(entity.getSbzt()) });
		list.add(new String[] { "施工单位", StringUtils.nullStrToEmpty(entity.getSgdw()) });
		list.add(new String[] { "施工日期", null == entity.getSgrq() ? "" : dateFormat.format(entity.getSgrq()) });
		list.add(new String[] { "峻工日期", null == entity.getJgrq() ? "" : dateFormat.format(entity.getJgrq()) });
		list.add(new String[] { "专业分类", StringUtils.nullStrToEmpty(entity.getZyfl()) });
		return list;
	}

	/**
	 * 电缆沟
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelDlg(EcChannelDlgEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "电缆沟编号", StringUtils.nullStrToEmpty(entity.getDlgbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		addYwdwAndWhbx(entity.getYwdw(), entity.getWhbz(), list);
		list.add(new String[] { "资产性质", StringUtils.nullStrToEmpty(entity.getZcxz()) });
		list.add(new String[] { "资产单位", StringUtils.nullStrToEmpty(entity.getZcdw()) });
		list.add(new String[] { "资产编号", StringUtils.nullStrToEmpty(entity.getZcbh()) });
		list.add(new String[] { "地区特征", StringUtils.nullStrToEmpty(entity.getDqtz()) });
		list.add(new String[] { "设备状态", StringUtils.nullStrToEmpty(entity.getSbzt()) });
		list.add(new String[] { "投运日期", null == entity.getTyrq() ? "" : dateFormat.format(entity.getTyrq()) });
		list.add(new String[] { "断面尺寸(mm)", StringUtils.nullStrToEmpty(entity.getDmcc()) });
		list.add(new String[] { "电缆沟长度(m)", StringUtils.nullStrToEmpty(entity.getDlgcd()) });
		list.add(new String[] { "电缆沟盖板数量", StringUtils.nullStrToEmpty(entity.getDlgbgsl()) });
		list.add(new String[] { "盖板材质", StringUtils.nullStrToEmpty(entity.getGbcz()) });
		list.add(new String[] { "施工单位", StringUtils.nullStrToEmpty(entity.getSgdw()) });
		list.add(new String[] { "峻工日期", null == entity.getJgrq() ? "" : dateFormat.format(entity.getJgrq()) });
		list.add(new String[] { "图纸编号", StringUtils.nullStrToEmpty(entity.getTzbh()) });
		list.add(new String[] { "专业分类", StringUtils.nullStrToEmpty(entity.getZyfl()) });
		return list;
	}

	/**
	 * 顶管/拖拉管
	 * 
	 * @param entity
	 * @return
	 */
	public static ArrayList<Object[]> getByEcChannelDg(EcChannelDgEntity entity) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "运行编号", StringUtils.nullStrToEmpty(entity.getYxbh()) });
		if (GlobalEntry.ssds) {
			list.add(new String[] { "所属地市", StringUtils.nullStrToEmpty(entity.getSsds()) });
		}
		if (null != globalBLL) {
			EdpParamInfoEntity ywdwEP = globalBLL.get(entity.getYwdw());
			list.add(new String[] { "运维单位", null == ywdwEP ? "" : ywdwEP.getParamName() + "" });
		}
		list.add(new String[] { "通道名称", StringUtils.nullStrToEmpty(entity.getTdmc()) });
		list.add(new String[] { "总长度", StringUtils.nullStrToEmpty(entity.getZcd()) });
		list.add(new String[] { "结构形式", StringUtils.nullStrToEmpty(entity.getJgxs()) });
		list.add(new String[] { "走向", StringUtils.nullStrToEmpty(entity.getZx()) });
		list.add(new String[] { "连通关系", StringUtils.nullStrToEmpty(entity.getLtgx()) });
		list.add(new String[] { "横向剖面", StringUtils.nullStrToEmpty(entity.getHxpm()) });
		list.add(new String[] { "纵向剖面", StringUtils.nullStrToEmpty(entity.getZxpm()) });
		list.add(new String[] { "宽度", StringUtils.nullStrToEmpty(entity.getKd()) });
		list.add(new String[] { "拖拉管数量", StringUtils.nullStrToEmpty(entity.getTlgsl()) });
		list.add(new String[] { "拖拉管管径", StringUtils.nullStrToEmpty(entity.getTlggj()) });
		list.add(new String[] { "管材材质", StringUtils.nullStrToEmpty(entity.getGccz()) });
		return list;
	}

	/**
	 * 井盖
	 * 
	 * @param entity
	 * @param well
	 * @return
	 */
	public static ArrayList<Object[]> getByEcWorkWellCoverEntity(EcWorkWellCoverEntity entity, EcWorkWellEntity well) {
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "序号", StringUtils.nullStrToEmpty(entity.getXh()) });
		list.add(new String[] { "设备名称", StringUtils.nullStrToEmpty(entity.getSbmc()) });
		list.add(new String[] { "所属工井", null != well ? StringUtils.nullStrToEmpty(well.getSbmc()) : "" });
		list.add(new String[] { "系统中是否存在", StringUtils.nullStrToEmpty(entity.getBiz()) });
		addYwdwAndWhbx(entity.getYwdw(), entity.getWhbz(), list);
		list.add(new String[] { "所属责任区", StringUtils.nullStrToEmpty(entity.getSszrq()) });
		list.add(new String[] { "井盖长度", StringUtils.nullStrToEmpty(entity.getJgcd()) });
		list.add(new String[] { "井盖宽度", StringUtils.nullStrToEmpty(entity.getJgkd()) });
		list.add(new String[] { "井盖厚度", StringUtils.nullStrToEmpty(entity.getJghd()) });
		list.add(new String[] { "采集时间", null == entity.getCjsj() ? "" : dateFormat.format(entity.getCjsj()) });
		list.add(new String[] { "更新时间", null == entity.getGxsj() ? "" : dateFormat.format(entity.getGxsj()) });
		list.add(new String[] { "备注", StringUtils.nullStrToEmpty(entity.getBz()) });
		return list;
	}
	
	@SuppressLint("SimpleDateFormat")
	public static ArrayList<Object[]> getTaskDetails() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String taskStatus = "";
		Integer status = GlobalEntry.currentTask.getStatus();
		if (status == 0) {
			taskStatus = "未确认";
		} else if (status ==1) {
			taskStatus = "发布";
		} else if (status ==3) {
			taskStatus = "采集完成";
		} else if (status ==4) {
			taskStatus = "已审核";
		}	else if (status ==5) {
			taskStatus = "已完结";
		}else {
			taskStatus = "未知";
		}
		ArrayList<Object[]> list = new ArrayList<Object[]>();
		list.add(new String[] { "任务名称", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getTaskName()) });
		list.add(new String[] { "任务状态", StringUtils.nullStrToEmpty(taskStatus) });
		list.add(new String[] { "创建时间", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getCreateTime() == null ? "" :sdf.format(GlobalEntry.currentTask.getCreateTime())) });
		list.add(new String[] { "开始时间", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getBeginTime() == null ? "" :sdf.format(GlobalEntry.currentTask.getBeginTime())) });
		list.add(new String[] { "更新时间", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getUpdateTime() == null ? "" :sdf.format(GlobalEntry.currentTask.getUpdateTime())) });
		list.add(new String[] { "预计完成时间", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getExpeTime() == null ? "" :sdf.format(GlobalEntry.currentTask.getExpeTime())) });
		list.add(new String[] { "完成时间", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getCompleteTime() == null ? "" :sdf.format(GlobalEntry.currentTask.getCompleteTime())) });
		list.add(new String[] { "球型数目", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getBallTypeCount()) });
		list.add(new String[] { "钉形数目", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getNailTypeCount()) });
		list.add(new String[] { "电子标签", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getElecLabelCount()) });
		list.add(new String[] { "线路长度", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getLineLength()) });
		list.add(new String[] { "顶管长度", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getPipeLength()) });
		list.add(new String[] { "顶管段数", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getPipeCounts()) });
		list.add(new String[] { "任务描述", StringUtils.nullStrToEmpty(GlobalEntry.currentTask.getTaskDesc()) });
		return list;
	}
}
