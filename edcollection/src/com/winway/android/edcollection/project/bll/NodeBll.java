package com.winway.android.edcollection.project.bll;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.colist.entity.CollectedObject;
import com.winway.android.edcollection.colist.entity.TimeType;
import com.winway.android.edcollection.datacenter.service.LineInfoCenter;
import com.winway.android.edcollection.login.controll.SettingServerAddressControll;
import com.winway.android.edcollection.project.entity.EcChannelNodesEntity;
import com.winway.android.edcollection.project.entity.EcChannelSectionEntity;
import com.winway.android.edcollection.project.entity.EcChannelSectionPointsEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EcMarkerIdsEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeDeviceEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.project.entity.EmDangerInfoEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.LogUtil;

/**
 * 路径点
 * 
 * @author
 *
 */
public class NodeBll extends BaseBll<EcNodeEntity> {
	private CommonBll commonBll;
	private BaseDBUtil dbUtil;
	private ComUploadBll comUploadBll;
	private Context context;
	private String rootPath = "";
	private boolean is;

	private List<CollectedObject> copyAllColl(List<CollectedObject> collList) {
		return collList;
	}

	public NodeBll(Context context, String dbUrl) {
		super(context, dbUrl);
		commonBll = new CommonBll(context, dbUrl);
		dbUtil = new BaseDBUtil(context, new File(GlobalEntry.prjDbUrl));
		comUploadBll = new ComUploadBll(context, dbUrl);
		this.context = context;
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	public List<CollectedObject> getAll() {
		/*
		 * List<EcNodeEntity> nodelists = findAll(); return
		 * changeCollectedObject(nodelists);
		 */
		return changeCollectedObject();
	}

	/**
	 * 查询所有标签
	 * 
	 * @return
	 */
	public List<EcLineLabelEntity> getAllLineLable() {
		String sql = "select * from ec_line_label";
		try {
			List<EcLineLabelEntity> excuteQuery = dbUtil.excuteQuery(EcLineLabelEntity.class, sql);
			return excuteQuery;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 查询一条线路下的所有标签
	 * 
	 * @param lineNo
	 * @return
	 */
	public List<EcLineLabelEntity> getAlineLable(String lineNo) {
		String sql = "select * from ec_line_device where DEVICE_TYPE ='" + ResourceEnum.DZBQ.getValue()
				+ "' and LINE_NO ='" + lineNo + "'";
		try {
			List<EcLineLabelEntity> excuteQuery = dbUtil.excuteQuery(EcLineLabelEntity.class, sql);
			return excuteQuery;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 根据采集时间、路径点类型、采集路线查询
	 * 
	 * @param timeType
	 *            0全部， 1 当天，2 本周 ，3本月
	 * @param lineType
	 *            1 标识球，2 标识钉，0 路径点
	 * @param lineNo线路编号
	 * @return
	 */
	public List<CollectedObject> getTimeOrLineTypeOrLineNo(Integer timeType, String lineType, String lineNo) {
		List<EcNodeEntity> lists = null;
		List<CollectedObject> collLists = null;
		// 如果输入查找的条件都为空则查询所有
		if (timeType == null && lineType == null && lineNo == null) {
			collLists = getAll();
			return collLists;
		}
		StringBuffer expr = new StringBuffer();
		String lineId = "";
		long starDate = 0;
		long endDate = DateUtils.getMillis(new Date());
		// 添加采集时间条件查询
		if (timeType != null) {
			if (timeType == TimeType.QB.getNo()) {// 全部
				starDate = 0;
			} else if (timeType == TimeType.DT.getNo()) {// 当天
				starDate = DateUtils.getMillis(DateUtils.getDayStartOrEndDate(new Date(), 0));
			} else if (timeType == TimeType.BZ.getNo()) {// 本周
				starDate = DateUtils.getMillis(DateUtils.getWeekStartDate());
			} else if (timeType == TimeType.BY.getNo()) {// 本月
				starDate = DateUtils.getMillis(DateUtils.getMouthStartDate());
			}
			expr.append(" UPDATE_TIME between '" + starDate + "' and '" + endDate + "'");
		}
		// 添加路径点条件查询
		if (lineType != null) {// 路径点类型| 1 标识球，2 标识钉，0 路径点
			if (timeType != null) {
				expr.append(" and ");
			}
			if (NodeMarkerType.BSQ.getName().equals(lineType)) {// 标识球
				expr.append(" marker_type = '" + NodeMarkerType.BSQ.getValue() + "'");
			} else if (NodeMarkerType.BSD.getName().equals(lineType)) {// 标识钉
				expr.append(" marker_type = '" + NodeMarkerType.BSD.getValue() + "'");
			} else if (NodeMarkerType.XND.getName().equals(lineType)) {// 路径点
				expr.append(" marker_type = '" + NodeMarkerType.XND.getValue() + "'");
			} else if (NodeMarkerType.GT.getName().equals(lineType)) {// 杆塔
				expr.append(" marker_type = '" + NodeMarkerType.GT.getValue() + "'");
			} else if (NodeMarkerType.AJH.getName().equals(lineType)) {// 安键环
				expr.append(" marker_type = '" + NodeMarkerType.AJH.getValue() + "'");
			} else {
				expr.append(" 1 = 1 ");
			}

		}
		// 添加采集线路条件查询
		if (lineNo != null) {
			if (timeType != null || lineType != null) {
				expr.append(" and ");
			}

			// -1为查找全部线路的标志
			if ("-1".equals(lineNo)) {
				expr.append(" 1=1 ");
			} else {
				expr.append("  oid in (select ec_line_nodes.oid from ec_line, ec_line_nodes where ec_line.EC_LINE_ID = "
						+ "ec_line_nodes.EC_LINE_ID  and ec_line.LINE_NO = '" + lineNo + "') ");
				lineId = queryOrderNo(lineNo);
			}
		}
		// BaseDBUtil baseDBUtil = new BaseDBUtil(this.getDbUtils());
		// try {
		// EcNodeEntity entity = null;
		// List<EmCDataLogEntity> logList = getLogList(baseDBUtil);
		// List<EcNodeEntity> nodelist = new ArrayList<EcNodeEntity>();
		// if (logList == null || logList.isEmpty()) {
		// return null;
		// }
		// for (EmCDataLogEntity emCDataLogEntity : logList) {
		// String dataKey = emCDataLogEntity.getDataKey();
		// nodelist.add(entity);
		// }
		// expr.append("OID in( '0d7df5df4688447c9e356fa6cfe47580
		// ,f9a3110da41d4490bd0ac55b4a391a7a')");
		// } catch (DbException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		expr.append("order by UPDATE_TIME desc");
		// 查找
		lists = this.queryByExpr(EcNodeEntity.class, expr.toString());
		collLists = changeCollectedObject(lists, lineId);
		return collLists;
	}

	/**
	 * 根据线路编号获取线路id
	 * 
	 * @param lineNo
	 * @return
	 */
	private String queryOrderNo(String lineNo) {
		try {
			String sql = "select * from ec_line where LINE_NO = '" + lineNo + "'";
			List<EcLineEntity> excuteQuery = dbUtil.excuteQuery(EcLineEntity.class, sql);
			if (excuteQuery != null && !excuteQuery.isEmpty()) {
				for (EcLineEntity ecLineEntity : excuteQuery) {
					// 拿到线路id
					return ecLineEntity.getEcLineId();
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将EcNodeEntity抽出成CollectedObject
	 * 
	 * @param lists
	 * @return
	 */
	private List<CollectedObject> changeCollectedObject_(List<EcNodeEntity> lists) {
		CollectedObject collectedObject;
		List<CollectedObject> collLists = new ArrayList<CollectedObject>();
		EmCDataLogEntity data;
		String userId = GlobalEntry.loginResult.getUid();
		if (lists != null && lists.size() > 0) {
			for (EcNodeEntity entity : lists) {
				collectedObject = new CollectedObject();
				collectedObject.setOid(entity.getOid());
				data = this.findCDataLog(GlobalEntry.currentProject.getEmProjectId(),
						TableNameEnum.LJD.getTableName().toUpperCase(Locale.CHINA), entity.getOid());
				// 刷选出日志表存在关联的 、是添加的、executor与当前用户名匹配的
				if (data != null && userId.equals(data.getExecutor())) {// &&
																		// DataLogOperatorType.Add.getValue().equals(data.getOptType())
					if (entity.getMarkerType() == (NodeMarkerType.BSQ.getValue())) {// 标识球
						collectedObject.setObjType((NodeMarkerType.BSQ.getName()));
					} else if (entity.getMarkerType() == (NodeMarkerType.BSD.getValue())) {// 标识钉
						collectedObject.setObjType((NodeMarkerType.BSD.getName()));
					} else if (entity.getMarkerType() == (NodeMarkerType.XND.getValue())) {// 路径点
						collectedObject.setObjType((NodeMarkerType.XND.getName()));
					} else if (entity.getMarkerType() == (NodeMarkerType.GT.getValue())) {// 杆塔
						collectedObject.setObjType((NodeMarkerType.GT.getName()));
					}
					collectedObject.setNo(entity.getMarkerNo());// 编号
					// collectedObject.setLayType(entity.getLayType());// 敷设方式
					collectedObject.setUpdateTime(entity.getUpdateTime());// 更新时间
					collectedObject.setIsUpload(data.getIsUpload());
					collLists.add(collectedObject);
				}
			}
		}
		return collLists;
	}

	private List<CollectedObject> changeCollectedObject(List<EcNodeEntity> lists, String lineId) {
		try {
			BaseDBUtil baseDBUtil = new BaseDBUtil(this.getDbUtils());
			String userId = GlobalEntry.loginResult.getUid();
			List<CollectedObject> collLists = new ArrayList<CollectedObject>();
			List<EmCDataLogEntity> logList = getLogList(baseDBUtil);
			CollectedObject collectedObject;
			if (logList == null || logList.isEmpty()) {
				return null;
			}
			for (EmCDataLogEntity emCDataLogEntity : logList) {
				// 根据主键id获取节点实体
				EcNodeEntity entity = baseDBUtil.getById(EcNodeEntity.class, emCDataLogEntity.getDataKey());
				// 判断日志中查询出来的实体是否在lists集合中
				if (entity == null || entity.getMarkerNo() == null) {
					continue;
				}
				boolean inList = isInList(entity, lists);
				if (inList) {
					String lineOrderNo = "";
					// 获取线路序号
					if (!lineId.equals("")) {// 线路id不为空字符串
						// 拿到线路节点实体
						EcLineNodesEntity lineNode = getLineNode(entity.getOid(), lineId, baseDBUtil);
						if (lineNode != null) {
							lineOrderNo = lineNode.getNIndex() + "";
						}
					}
					collectedObject = saveCollectObj(entity, emCDataLogEntity, userId, lineOrderNo);
					if (collectedObject != null) {
						// 去重复
						boolean isinlist = isInList(collectedObject, collLists);
						if (!isinlist) {
							collLists.add(collectedObject);
						}
					}
				}
			}
			return collLists;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取某条线在某个节点的线路节点
	 * 
	 * @param oid
	 * @param lineId
	 * @return
	 */
	public EcLineNodesEntity getLineNode(String oid, String lineId, BaseDBUtil baseDBUtil) {
		EcLineNodesEntity lineNodeIndex = LineInfoCenter.getLineNodeIndex(lineId, oid);
		if (null != lineNodeIndex) {
			return lineNodeIndex;
		}
		try {
			EcLineNodesEntity queryEntity = new EcLineNodesEntity();
			queryEntity.setEcLineId(lineId);
			queryEntity.setOid(oid);
			List<EcLineNodesEntity> lineNodeEntity = baseDBUtil.excuteQuery(EcLineNodesEntity.class, queryEntity);
			if (lineNodeEntity == null || lineNodeEntity.isEmpty()) {
				return null;
			}
			return lineNodeEntity.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private CollectedObject saveCollectObj(EcNodeEntity entity, EmCDataLogEntity emCDataLogEntity, String userId,
			String orderNo) {
		if (emCDataLogEntity != null && userId.equals(emCDataLogEntity.getExecutor())) {// &&
			CollectedObject collectedObject = new CollectedObject();
			// DataLogOperatorType.Add.getValue().equals(data.getOptType())
			if (entity.getMarkerType() == (NodeMarkerType.BSQ.getValue())) {// 标识球
				collectedObject.setObjType((NodeMarkerType.BSQ.getName()));
			} else if (entity.getMarkerType() == (NodeMarkerType.BSD.getValue())) {// 标识钉
				collectedObject.setObjType((NodeMarkerType.BSD.getName()));
			} else if (entity.getMarkerType() == (NodeMarkerType.XND.getValue())) {// 路径点
				collectedObject.setObjType((NodeMarkerType.XND.getName()));
			} else if (entity.getMarkerType() == (NodeMarkerType.GT.getValue())) {// 杆塔
				collectedObject.setObjType((NodeMarkerType.GT.getName()));
			} else if (entity.getMarkerType() == (NodeMarkerType.AJH.getValue())) {// 安键环
				collectedObject.setObjType((NodeMarkerType.AJH.getName()));
			}
			try {// 会抛异常,不捕获将获取不到数据,也不会报错
				collectedObject.setOid(entity.getOid());
				collectedObject.setNo(entity.getMarkerNo());// 编号
				// collectedObject.setLayType(entity.getLayType());// 敷设方式
				collectedObject.setUpdateTime(entity.getUpdateTime());// 更新时间
				collectedObject.setIsUpload(emCDataLogEntity.getIsUpload());// 是否上传
				collectedObject.setOperatorType(emCDataLogEntity.getOptType());// 操作类型
				collectedObject.setOrderNo(orderNo);
				return collectedObject;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}

	/**
	 * 获取日志列表
	 * 
	 * @param baseDBUtil
	 * @return
	 * @throws DbException
	 */
	private List<EmCDataLogEntity> getLogList(BaseDBUtil baseDBUtil) throws DbException {
		EmCDataLogEntity queryLogEntity = new EmCDataLogEntity();
		queryLogEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
		queryLogEntity.setTableName(TableNameEnum.LJD.getTableName().toUpperCase(Locale.CHINA));
		// 拿到日志列表
		List<EmCDataLogEntity> logList = baseDBUtil.excuteQuery(EmCDataLogEntity.class, queryLogEntity);
		return logList;
	}

	private List<CollectedObject> changeCollectedObject() {
		try {
			BaseDBUtil baseDBUtil = new BaseDBUtil(this.getDbUtils());
			List<EmCDataLogEntity> logList = getLogList(baseDBUtil);
			if (logList == null || logList.isEmpty()) {
				return null;
			}
			String userId = GlobalEntry.loginResult.getUid();
			CollectedObject collectedObject;
			List<CollectedObject> collLists = new ArrayList<CollectedObject>();
			for (EmCDataLogEntity data : logList) {
				EcNodeEntity entity = baseDBUtil.getById(EcNodeEntity.class, data.getDataKey());
				if (entity == null || entity.getMarkerNo() == null) {
					continue;
				}
				collectedObject = saveCollectObj(entity, data, userId, "");
				if (collectedObject != null) {
					// 去重复
					boolean inList = isInList(collectedObject, collLists);
					if (!inList) {
						collLists.add(collectedObject);
					}
				}
			}
			copyAllColl(collLists);
			return collLists;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断元素是否在数组中
	 */
	public static boolean isInList(CollectedObject item, List<CollectedObject> items) {
		boolean bRes = false;
		if (item == null || items == null) {
			bRes = false;
		}
		for (int i = 0; i < items.size(); i++) {
			CollectedObject object = items.get(i);
			if (object != null && object.getNo() != null) {
				if (object.getNo().equals(item.getNo())) {
					bRes = true;
					break;
				}
			}
		}
		return bRes;
	}

	public static boolean isInList(EcNodeEntity item, List<EcNodeEntity> items) {
		boolean bRes = false;
		if (item == null || items == null) {
			bRes = false;
		}
		for (int i = 0; i < items.size(); i++) {
			EcNodeEntity object = items.get(i);
			if (object != null && object.getMarkerNo() != null) {
				if (object.getMarkerNo().equals(item.getMarkerNo())) {
					bRes = true;
					break;
				}
			}
		}
		return bRes;
	}

	/**
	 * 更加表名、数据ID查询数据
	 * 
	 * @param tableName数据表
	 * @param dataKey数据ID
	 * @return
	 */
	public EmCDataLogEntity findCDataLog(String prjNo, String tableName, String dataKey) {
		String expr = "table_name = '" + tableName + "' and data_key = '" + dataKey + "'" + " and prjid = '" + prjNo
				+ "'";
		List<EmCDataLogEntity> entities = this.queryByExpr2(EmCDataLogEntity.class, expr);
		if (entities != null && entities.size() > 0) {
			return entities.get(0);
		}
		return null;
	}

	/**
	 * 根据oid获取节点实体对象
	 * 
	 * @param oid
	 * @return
	 */
	public EcNodeEntity getNodeEntityByOid(String oid) {
		return this.findById(EcNodeEntity.class, oid);
	}

	/**
	 * 在标识器id表中将此路径点标识器id删除
	 * 
	 * @param oid
	 * @return
	 */
	public void delMarkerIds(String oid) {
		// 根据oid获取节点实体对象
		EcNodeEntity nodeEntity = getNodeEntityByOid(oid);
		if (nodeEntity == null) {
			return;
		}
		String expr = "EC_MARKER_IDS_ID = '" + nodeEntity.getMarkerNo() + "'";
		// 根据路径点的标识器编号去标识器id表中查找
		List<EcMarkerIdsEntity> list = this.queryByExpr2(EcMarkerIdsEntity.class, expr);
		if (list != null && !list.isEmpty()) {
			for (EcMarkerIdsEntity ecMarkerIdsEntity : list) {
				// 将查询出来的标识器id号从标识器id表中删除
				this.deleteById(EcMarkerIdsEntity.class, ecMarkerIdsEntity.getId());
			}
		}
	}

	/**
	 * 获取当前节点的操作日志
	 * 
	 * @param oid
	 * @return
	 */
	public EmCDataLogEntity getDataLogEntity(String oid) {
		String expr = "DATA_KEY = '" + oid + "'";
		List<EmCDataLogEntity> list = this.queryByExpr2(EmCDataLogEntity.class, expr);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 删除节点及附属设备
	 * @param oid
	 * @param isDelete  未上传true  已上传false
	 */
	public void deleteNodeAndDevice(String oid, boolean isDelete) {
		deleteLineDevice(oid, isDelete);// 删除节点关联及其设备（包括附件）数据
		deleteLineNodes(oid, isDelete);// 删除线路节点关联表
		deleteNodeDevice(oid, isDelete);// 删除路径点设备
		deleteChannel(oid, isDelete);// 删除通道信息
		deleteChannelDanger(oid,isDelete);// 删除通道现状（隐患）
		deleteNode(oid,isDelete);// 删除路径点
	}

	/**
	 * 保存删除节点日志并不删除数据
	 * 
	 * @param oid
	 */
	public void saveDeleteNodeDataLog(String oid) {

	}

	/**
	 * 删除通道现状信息（隐患）
	 * 
	 * @param oid
	 *            路径点id
	 * @author xs
	 */
	private void deleteChannelDanger(String oid,boolean isDelete) {
		if(isDelete){
			String sql = "LNK_OBJ_ID = '" + oid + "'";
			List<EmDangerInfoEntity> channelDangers = queryByExpr2(EmDangerInfoEntity.class, sql);
			if (null != channelDangers && !channelDangers.isEmpty()) {
				for (int i = 0; i < channelDangers.size(); i++) {
					// 通道现状实体
					EmDangerInfoEntity channelDanger = channelDangers.get(i);
					// 删除通道现状数据
					deleteById(EmDangerInfoEntity.class, channelDanger.getEmDangerInfoId());
					deleteDataLog(channelDanger.getEmDangerInfoId());
					// 更新日志
					/*
					 * commonBll.handleDataLog(channelDanger.getEmDangerInfoId(),
					 * TableNameEnum.EM_DANGER_INFO.getTableName(),
					 * DataLogOperatorType.delete, WhetherEnum.NO, "删除了通道现状信息",
					 * false);
					 */
				}
			}
		}else {
			String sql = "LNK_OBJ_ID = '" + oid + "'";
			List<EmDangerInfoEntity> channelDangers = queryByExpr2(EmDangerInfoEntity.class, sql);
			if (null != channelDangers && !channelDangers.isEmpty()) {
				for (int i = 0; i < channelDangers.size(); i++) {
					// 通道现状实体
					EmDangerInfoEntity channelDanger = channelDangers.get(i);
					// 删除通道现状数据
					//deleteById(EmDangerInfoEntity.class, channelDanger.getEmDangerInfoId());
					// 更新日志
					 commonBll.handleDataLog(channelDanger.getEmDangerInfoId(),
					 TableNameEnum.EM_DANGER_INFO.getTableName(),
					 DataLogOperatorType.delete, WhetherEnum.NO, "删除了通道现状信息",
					 false);
				}
			}
		}
		
	}

	/**
	 * 删除路径点
	 * 
	 * @param oid
	 *            路径点id
	 */
	private void deleteNode(String oid,boolean isDelete) {
		if(isDelete){
			// 删除路径点附件
			deleteNodeOfflineAttach(oid);
			// 删除路径点数据
			deleteById(EcNodeEntity.class, oid);
			deleteDataLog(oid);
			// 更新日志
			
		}else {
			deleteNodeOfflineAttach(oid);
			// 删除路径点数据
			//deleteById(EcNodeEntity.class, oid);
			//deleteDataLog(oid);
			 commonBll.handleDataLog(oid, TableNameEnum.LJD.getTableName(),
			 DataLogOperatorType.delete, WhetherEnum.NO, "删除路径点", false);
		}
	}

	/**
	 * 删除于路径点直接关联附件的文件夹
	 * 
	 * @param oid
	 */
	@SuppressLint("NewApi")
	private void deleteNodeOfflineAttach(String oid) {
		String sql = "WORK_NO = '" + oid + "'";
		List<OfflineAttach> attachs = queryByExpr2(OfflineAttach.class, sql);
		EcNodeEntity nodeEntityByOid = getNodeEntityByOid(oid);
		String markerNo = nodeEntityByOid.getMarkerNo();
		if (null != attachs && !attachs.isEmpty()) {
			for (int i = 0; i < attachs.size(); i++) {
				// 附件实体
				OfflineAttach attach = attachs.get(i);
				// 删除附件
				deleteById(OfflineAttach.class, attach.getComUploadId());
			}
		}
		getRootPath();
		String filePath = rootPath + "附件/" + GlobalEntry.currentProject.getPrjNo() + "/路径点/" + markerNo;
		FileUtil.deleteDirectory(filePath);
	}

	@SuppressLint("NewApi")
	private void getRootPath() {
		if (GlobalEntry.useTfcard) {
			// 删除SD卡照片
			String path = SettingServerAddressControll.getTFCardPath(context);
			if (TextUtils.isEmpty(path)) {// 判断tf卡是否存在
				rootPath = FileUtil.AppRootPath;
			} else {
				// 获取所有缓存目录
				File[] fs = context.getExternalCacheDirs();
				// 匹配sd卡目录
				for (int i = 0; i < fs.length; i++) {
					String str = fs[i].getAbsolutePath();
					if (str.contains(path)) {
						path = str;// 找到路径了
						break;
					}
				}
				rootPath = path + File.separator;
			}
		} else {
			rootPath = FileUtil.AppRootPath;
		}
	}

	/**
	 * 删除线路节点关联表
	 * 
	 * @param oid
	 */
	public void deleteLineNodes(String oid, boolean isDelete) {
		if (isDelete) {
			// 删除线路节点关联表
			String expr = "OID ='" + oid + "'";
			List<EcLineNodesEntity> lineNodes = queryByExpr2(EcLineNodesEntity.class, expr);
			if (null != lineNodes && !lineNodes.isEmpty()) {
				for (int i = 0; i < lineNodes.size(); i++) {
					// 线路节点实体
					EcLineNodesEntity lineNode = lineNodes.get(i);
					// 删除单条线路节点数据
					deleteById(EcLineNodesEntity.class, lineNode.getEcLineNodesId());
					deleteDataLog(lineNode.getEcLineNodesId());
					// 更新日志
					/*
					 * commonBll.handleDataLog(lineNode.getEcLineNodesId(),
					 * TableNameEnum.XLJDGL.getTableName(),
					 * DataLogOperatorType.delete, WhetherEnum.NO, "删除线路节点关联",
					 * false);
					 */
				}
			}
		} else {
			// 删除线路节点关联表
			String expr = "OID ='" + oid + "'";
			List<EcLineNodesEntity> lineNodes = queryByExpr2(EcLineNodesEntity.class, expr);
			if (null != lineNodes && !lineNodes.isEmpty()) {
				for (int i = 0; i < lineNodes.size(); i++) {
					// 线路节点实体
					EcLineNodesEntity lineNode = lineNodes.get(i);
					// 删除单条线路节点数据
					// deleteById(EcLineNodesEntity.class,
					// lineNode.getEcLineNodesId());
					// 更新日志
					commonBll.handleDataLog(lineNode.getEcLineNodesId(), TableNameEnum.XLJDGL.getTableName(),
							DataLogOperatorType.delete, WhetherEnum.NO, "删除线路节点关联", false);
				}
			}
		}
	}

	/**
	 * 删除路径点设备
	 * 
	 * @param oid
	 */
	public void deleteNodeDevice(String oid, boolean isDelete) {
		if(isDelete){
			String sql = "OID = '" + oid + "'";
			// 获取到路径点设备
			List<EcNodeDeviceEntity> nodeDevices = this.queryByExpr2(EcNodeDeviceEntity.class, sql);
			if (null != nodeDevices && !nodeDevices.isEmpty()) {
				for (int i = 0; i < nodeDevices.size(); i++) {
					EcNodeDeviceEntity nodeDev = nodeDevices.get(i);
					String devObjId = nodeDev.getDevObjId();
					String resType = nodeDev.getDeviceType();

					if (ResourceEnum.PDS.getValue().equals(resType)) {
						deleteDevice(EcDistributionRoomEntity.class, devObjId, TableNameEnum.PDF.getTableName(), "删除配电房",
								isDelete);
					} else if (ResourceEnum.DLJ.getValue().equals(resType)) {
						deleteWorkWellCover(devObjId,isDelete);// 删除井盖
						deleteDeviceLabel(devObjId, "删除工井标签",isDelete);
						deleteDevice(EcWorkWellEntity.class, devObjId, TableNameEnum.GJ.getTableName(), "删除工井", isDelete);
					} else if (ResourceEnum.DYPDX.getValue().equals(resType)) {
						deleteDevice(EcDypdxEntity.class, devObjId, TableNameEnum.DYPDX.getTableName(), "删除低压配电箱",
								isDelete);
					} else if (ResourceEnum.XSBDZ.getValue().equals(resType)) {
						deleteDevice(EcXsbdzEntity.class, devObjId, TableNameEnum.XSBDZ.getTableName(), "删除箱式变电站",
								isDelete);
					} else if (ResourceEnum.KGZ.getValue().equals(resType)) {
						deleteDevice(EcKgzEntity.class, devObjId, TableNameEnum.KGZ.getTableName(), "删除开关站", isDelete);
					} else if (ResourceEnum.BYQ.getValue().equals(resType)) {
						deleteDevice(EcTransformerEntity.class, devObjId, TableNameEnum.BYQ.getTableName(), "删除变压器",
								isDelete);
					} else if (ResourceEnum.GT.getValue().equals(resType)) {
						deleteDevice(EcTowerEntity.class, devObjId, TableNameEnum.GT.getTableName(), "删除杆塔", isDelete);
					} else if (ResourceEnum.BDZ.getValue().equals(resType)) {
						deleteDevice(EcSubstationEntity.class, devObjId, TableNameEnum.BDZ.getTableName(), "删除变电站",
								isDelete);
					}

					deleteById(EcNodeDeviceEntity.class, nodeDev.getEcNodeDeviceId());
					deleteDataLog(nodeDev.getEcNodeDeviceId());
					/*
					 * commonBll.handleDataLog(nodeDev.getEcNodeDeviceId(),
					 * TableNameEnum.LJDSB.getTableName(),
					 * DataLogOperatorType.delete, WhetherEnum.NO, "删除路径点设备关联",
					 * false);
					 */
				}
			}
		}else {
			String sql = "OID = '" + oid + "'";
			// 获取到路径点设备
			List<EcNodeDeviceEntity> nodeDevices = this.queryByExpr2(EcNodeDeviceEntity.class, sql);
			if (null != nodeDevices && !nodeDevices.isEmpty()) {
				for (int i = 0; i < nodeDevices.size(); i++) {
					EcNodeDeviceEntity nodeDev = nodeDevices.get(i);
					String devObjId = nodeDev.getDevObjId();
					String resType = nodeDev.getDeviceType();

					if (ResourceEnum.PDS.getValue().equals(resType)) {
						deleteDevice(EcDistributionRoomEntity.class, devObjId, TableNameEnum.PDF.getTableName(), "删除配电房",
								isDelete);
					} else if (ResourceEnum.DLJ.getValue().equals(resType)) {
						deleteWorkWellCover(devObjId,isDelete);// 删除井盖
						deleteDeviceLabel(devObjId, "删除工井标签",isDelete);
						deleteDevice(EcWorkWellEntity.class, devObjId, TableNameEnum.GJ.getTableName(), "删除工井", isDelete);
					} else if (ResourceEnum.DYPDX.getValue().equals(resType)) {
						deleteDevice(EcDypdxEntity.class, devObjId, TableNameEnum.DYPDX.getTableName(), "删除低压配电箱",
								isDelete);
					} else if (ResourceEnum.XSBDZ.getValue().equals(resType)) {
						deleteDevice(EcXsbdzEntity.class, devObjId, TableNameEnum.XSBDZ.getTableName(), "删除箱式变电站",
								isDelete);
					} else if (ResourceEnum.KGZ.getValue().equals(resType)) {
						deleteDevice(EcKgzEntity.class, devObjId, TableNameEnum.KGZ.getTableName(), "删除开关站", isDelete);
					} else if (ResourceEnum.BYQ.getValue().equals(resType)) {
						deleteDevice(EcTransformerEntity.class, devObjId, TableNameEnum.BYQ.getTableName(), "删除变压器",
								isDelete);
					} else if (ResourceEnum.GT.getValue().equals(resType)) {
						deleteDevice(EcTowerEntity.class, devObjId, TableNameEnum.GT.getTableName(), "删除杆塔", isDelete);
					} else if (ResourceEnum.BDZ.getValue().equals(resType)) {
						deleteDevice(EcSubstationEntity.class, devObjId, TableNameEnum.BDZ.getTableName(), "删除变电站",
								isDelete);
					}

					//deleteById(EcNodeDeviceEntity.class, nodeDev.getEcNodeDeviceId());
					
					 commonBll.handleDataLog(nodeDev.getEcNodeDeviceId(),
					 TableNameEnum.LJDSB.getTableName(),
					 DataLogOperatorType.delete, WhetherEnum.NO, "删除路径点设备关联",
					 false);
					 
				}
			}
		}
		
	}

	/**
	 * 删除线路附属设备
	 * 
	 * @param oid
	 */
	public void deleteLineDevice(String oid, boolean isDelete) {
		if (isDelete) { // true 删除本地数据及日志
			String sql = "OID = '" + oid + "'";
			// 获取到线路附属设备表
			List<EcLineDeviceEntity> lineDevices = this.queryByExpr2(EcLineDeviceEntity.class, sql);
			if (null != lineDevices && !lineDevices.isEmpty()) {
				for (int i = 0; i < lineDevices.size(); i++) {
					// 线路设备实体
					EcLineDeviceEntity lineDev = lineDevices.get(i);
					// 设备主键
					String devObjId = lineDev.getDevObjId();
					// 设备类型
					String resType = lineDev.getDeviceType();
					
					// 删除对应设备
					if (ResourceEnum.DZBQ.getValue().equals(resType)) {
						deleteDevice(EcLineLabelEntity.class, devObjId, TableNameEnum.DLDZBQ.getTableName(), "删除电缆电子标签",
								isDelete);
					} else if (ResourceEnum.DLFZX.getValue().equals(resType)) {
						deleteDevice(EcDlfzxEntity.class, devObjId, TableNameEnum.DLFZX.getTableName(), "删除电缆分支箱",
								isDelete);
					} else if (ResourceEnum.ZJJT.getValue().equals(resType)) {
						deleteDeviceLabel(devObjId, "删除电缆中间接头标签",isDelete);
						deleteDevice(EcMiddleJointEntity.class, devObjId, TableNameEnum.DLZJJT.getTableName(), "删除电缆中间接头",
								isDelete);
					} else if (ResourceEnum.DYDLFZX.getValue().equals(resType)) {
						deleteDevice(EcDydlfzxEntity.class, devObjId, TableNameEnum.DYDLFZX.getTableName(), "删除低压电缆分支箱",
								isDelete);
					} else if (ResourceEnum.HWG.getValue().equals(resType)) {
						deleteDevice(EcHwgEntity.class, devObjId, TableNameEnum.HWG.getTableName(), "删除环网柜", isDelete);
					}
					
					// 删除线路设备关联表数据
					deleteById(EcLineDeviceEntity.class, lineDev.getEcLineDeviceId());
					deleteDataLog(lineDev.getEcLineDeviceId());
					// 更新日志
					/*
					 * commonBll.handleDataLog(lineDev.getEcLineDeviceId(),
					 * TableNameEnum.XLFSSB.getTableName(),
					 * DataLogOperatorType.delete, WhetherEnum.NO, "删除线路附属设备关联",
					 * false);
					 */
				}
			}
		} else {// 不删除本地数据，保存日志
			String sql = "OID = '" + oid + "'";
			// 获取到线路附属设备表
			List<EcLineDeviceEntity> lineDevices = this.queryByExpr2(EcLineDeviceEntity.class, sql);
			if (null != lineDevices && !lineDevices.isEmpty()) {
				for (int i = 0; i < lineDevices.size(); i++) {
					// 线路设备实体
					EcLineDeviceEntity lineDev = lineDevices.get(i);
					// 设备主键
					String devObjId = lineDev.getDevObjId();
					// 设备类型
					String resType = lineDev.getDeviceType();
					
					// 删除对应设备
					if (ResourceEnum.DZBQ.getValue().equals(resType)) {
						deleteDevice(EcLineLabelEntity.class, devObjId, TableNameEnum.DLDZBQ.getTableName(), "删除电缆电子标签",
								isDelete);
					} else if (ResourceEnum.DLFZX.getValue().equals(resType)) {
						deleteDevice(EcDlfzxEntity.class, devObjId, TableNameEnum.DLFZX.getTableName(), "删除电缆分支箱",
								isDelete);
					} else if (ResourceEnum.ZJJT.getValue().equals(resType)) {
						deleteDeviceLabel(devObjId, "删除电缆中间接头标签",isDelete);
						deleteDevice(EcMiddleJointEntity.class, devObjId, TableNameEnum.DLZJJT.getTableName(), "删除电缆中间接头",
								isDelete);
					} else if (ResourceEnum.DYDLFZX.getValue().equals(resType)) {
						deleteDevice(EcDydlfzxEntity.class, devObjId, TableNameEnum.DYDLFZX.getTableName(), "删除低压电缆分支箱",
								isDelete);
					} else if (ResourceEnum.HWG.getValue().equals(resType)) {
						deleteDevice(EcHwgEntity.class, devObjId, TableNameEnum.HWG.getTableName(), "删除环网柜", isDelete);
					}
					
					// 删除线路设备关联表数据
					//deleteById(EcLineDeviceEntity.class, lineDev.getEcLineDeviceId());
					//deleteDataLog(lineDev.getEcLineDeviceId());
					// 更新日志
					 commonBll.handleDataLog(lineDev.getEcLineDeviceId(),
					 TableNameEnum.XLFSSB.getTableName(),
					 DataLogOperatorType.delete, WhetherEnum.NO, "删除线路附属设备关联",
					 false);
				}
			}
		}
	}

	/**
	 * 删除设备及其附件
	 * 
	 * @param devId
	 *            设备主键
	 * @param tableName
	 *            设备对应表名
	 * @param desc
	 *            描述
	 * @author xs
	 */
	private void deleteDevice(Class<?> clz, String devId, String tableName, String desc, boolean isDelete) {
		if (isDelete) {
			// 删除附件
			deleteOfflineAttach(devId);
			// 删除设备
			deleteById(clz, devId);
			deleteDataLog(devId);
			/*// 更新日志
			 commonBll.handleDataLog(devId, tableName,
			 DataLogOperatorType.delete, WhetherEnum.NO, desc, false);*/
		} else {
			// 删除附件
			deleteOfflineAttach(devId);
			// 删除设备
			// deleteById(clz, devId);
			// 更新日志
			commonBll.handleDataLog(devId, tableName, DataLogOperatorType.delete, WhetherEnum.NO, desc, false);
		}

	}
	/**
	 * 删除日志
	 * @param datakey
	 */
	private void deleteDataLog(String dataKey) {
		String expr = "DATA_KEY='"+dataKey+"'";
		List<EmCDataLogEntity> list = this.queryByExpr2(EmCDataLogEntity.class, expr);
		deleteAll(list);
	}

	/**
	 * 删除附件
	 * 
	 * @param workNo
	 *            设备主键
	 */
	private void deleteOfflineAttach(String workNo) {
		String sql = "WORK_NO = '" + workNo + "'";
		List<OfflineAttach> attachs = queryByExpr2(OfflineAttach.class, sql);
		if (null != attachs && !attachs.isEmpty()) {
			for (int i = 0; i < attachs.size(); i++) {
				// 附件实体
				OfflineAttach attach = attachs.get(i);
				// 删除文件
				FileUtil.deleteFileByFilePath(attach.getFilePath());
				// 删除附件
				deleteById(OfflineAttach.class, attach.getComUploadId());
			}
		}
	}

	/**
	 * 删除设备标签
	 * 
	 * @param devId
	 *            设备主键
	 * @param desc
	 *            描述
	 * @author xs
	 */
	private void deleteDeviceLabel(String devId, String desc,boolean isDelete) {
		if(isDelete){
			String sql = "DEV_OBJ_ID = '" + devId + "'";
			List<EcLineLabelEntity> devLabels = queryByExpr2(EcLineLabelEntity.class, sql);
			if (null != devLabels && !devLabels.isEmpty()) {
				for (int i = 0; i < devLabels.size(); i++) {
					EcLineLabelEntity devLabel = devLabels.get(i);
					// 删除设备标签附件
					deleteOfflineAttach(devLabel.getObjId());
					// 删除设备标签
					deleteById(EcLineLabelEntity.class, devLabel.getObjId());
					deleteDataLog(devLabel.getObjId());
					// 更新日志
					/*
					 * commonBll.handleDataLog(devLabel.getObjId(),
					 * TableNameEnum.DLDZBQ.getTableName(),
					 * DataLogOperatorType.delete, WhetherEnum.NO, desc, false);
					 */
				}
			}
			
		}else {
			String sql = "DEV_OBJ_ID = '" + devId + "'";
			List<EcLineLabelEntity> devLabels = queryByExpr2(EcLineLabelEntity.class, sql);
			if (null != devLabels && !devLabels.isEmpty()) {
				for (int i = 0; i < devLabels.size(); i++) {
					EcLineLabelEntity devLabel = devLabels.get(i);
					// 删除设备标签附件
					deleteOfflineAttach(devLabel.getObjId());
					// 删除设备标签
					//deleteById(EcLineLabelEntity.class, devLabel.getObjId());
					//deleteDataLog(devLabel.getObjId());
					// 更新日志
					 commonBll.handleDataLog(devLabel.getObjId(),
					 TableNameEnum.DLDZBQ.getTableName(),
					 DataLogOperatorType.delete, WhetherEnum.NO, desc, false);
				}
			}
		}
	}

	/**
	 * 删除井盖
	 * 
	 * @param workWellId
	 *            所属工井id
	 * @author xs
	 */
	private void deleteWorkWellCover(String workWellId,boolean isDelete) {
		if(isDelete){
			String sql = "SSGJ ='" + workWellId + "'";
			List<EcWorkWellCoverEntity> covers = queryByExpr2(EcWorkWellCoverEntity.class, sql);
			if (null != covers && !covers.isEmpty()) {
				for (int i = 0; i < covers.size(); i++) {
					// 井盖实体
					EcWorkWellCoverEntity cover = covers.get(i);
					// 删除单条井盖
					deleteById(EcWorkWellCoverEntity.class, cover.getObjId());
					deleteDataLog(cover.getObjId());
					// 更新日志
					/*
					 * commonBll.handleDataLog(cover.getObjId(),
					 * TableNameEnum.JG.getTableName(), DataLogOperatorType.delete,
					 * WhetherEnum.NO, "删除井盖", false);
					 */
				}
			}
		}else {
			String sql = "SSGJ ='" + workWellId + "'";
			List<EcWorkWellCoverEntity> covers = queryByExpr2(EcWorkWellCoverEntity.class, sql);
			if (null != covers && !covers.isEmpty()) {
				for (int i = 0; i < covers.size(); i++) {
					// 井盖实体
					EcWorkWellCoverEntity cover = covers.get(i);
					// 删除单条井盖
					//deleteById(EcWorkWellCoverEntity.class, cover.getObjId());
					// 更新日志
					 commonBll.handleDataLog(cover.getObjId(),
					 TableNameEnum.JG.getTableName(), DataLogOperatorType.delete,
					 WhetherEnum.NO, "删除井盖", false);
				}
			}
		}
		
	}

	/**
	 * 删除通道截面
	 * 
	 * @param objId
	 */
	public void deleteSection(String objId) {
		String expr = "EC_CHANNEL_ID ='" + objId + "'";
		// 获取到通道截面实体集合
		List<EcChannelSectionEntity> sectionList = queryByExpr2(EcChannelSectionEntity.class, expr);
		if (sectionList != null && sectionList.size() > 0) {
			for (EcChannelSectionEntity ecChannelSectionEntity : sectionList) {
				String ecChannelSectionId = ecChannelSectionEntity.getEcChannelSectionId();
				// 获取截面管控实体集合
				String expr2 = "EC_CHANNEL_SECTION_ID ='" + ecChannelSectionId + "'";
				List<EcChannelSectionPointsEntity> sectionPointsList = queryByExpr2(EcChannelSectionPointsEntity.class,
						expr2);
				if (sectionPointsList != null && sectionPointsList.size() > 0) {
					for (EcChannelSectionPointsEntity ecChannelSectionPointsEntity : sectionPointsList) {
						deleteById(EcChannelSectionPointsEntity.class,
								ecChannelSectionPointsEntity.getEcChannelSectionPointsId());
						commonBll.handleDataLog(ecChannelSectionPointsEntity.getEcChannelSectionPointsId(),
								TableNameEnum.EC_CHANNEL_SECTION_POINT.getTableName(), DataLogOperatorType.delete,
								WhetherEnum.NO, "删除截面点", false);
					}
				}
				// 删除附件
				deleteOfflineAttach(ecChannelSectionId);
				deleteById(EcChannelSectionEntity.class, ecChannelSectionId);
				commonBll.handleDataLog(ecChannelSectionEntity.getEcChannelSectionId(),
						TableNameEnum.EC_CHANNEL_SECTION.getTableName(), DataLogOperatorType.delete, WhetherEnum.NO,
						"删除截面", false);
			}
		}
	}

	/**
	 * 删除通道节点关联表
	 * 
	 * @param oid
	 */
	public void deleteChannel(String oid, boolean isDelete) {
		// String expr = "DEV_OBJ_ID ='" + objId + "'";
		// String sql = "select * from ds_danger_device where OID = '" + oid +
		// "'";
		if (isDelete) {
			String sql = "OID = '" + oid + "'";
			// 获取通道节点关联表
			List<EcChannelNodesEntity> channelNodes = this.queryByExpr2(EcChannelNodesEntity.class, sql);
			if (null != channelNodes && !channelNodes.isEmpty()) {
				for (int i = 0; i < channelNodes.size(); i++) {
					EcChannelNodesEntity channelNode = channelNodes.get(i);
					// 删除通道节点关联表
					deleteById(EcChannelNodesEntity.class, channelNode.getEcChannelNodesId());
					deleteDataLog(channelNode.getEcChannelNodesId());
					/*
					 * commonBll.handleDataLog(channelNode.getEcChannelNodesId()
					 * , TableNameEnum.EC_CHANNEL_NODE.getTableName(),
					 * DataLogOperatorType.delete, WhetherEnum.NO, "删除通道节点关联表",
					 * false);
					 */
				}
			} 
			
		}else {
				String sql2 = "OID = '" + oid + "'";
				// 获取通道节点关联表
				List<EcChannelNodesEntity> channelNodes2 = this.queryByExpr2(EcChannelNodesEntity.class, sql2);
				if (null != channelNodes2 && !channelNodes2.isEmpty()) {
					for (int i = 0; i < channelNodes2.size(); i++) {
						EcChannelNodesEntity channelNode = channelNodes2.get(i);
						// 删除通道节点关联表
						//deleteById(EcChannelNodesEntity.class, channelNode.getEcChannelNodesId());
						 commonBll.handleDataLog(channelNode.
						 getEcChannelNodesId(),
						 TableNameEnum.EC_CHANNEL_NODE.getTableName(),
						 DataLogOperatorType.delete, WhetherEnum.NO,
						 "删除通道节点关联表", false);
					}
				}
			}

		}
	

}
