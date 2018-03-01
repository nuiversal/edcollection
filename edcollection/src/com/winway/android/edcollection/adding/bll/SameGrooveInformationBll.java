package com.winway.android.edcollection.adding.bll;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.adding.controll.LayingDitchControll;
import com.winway.android.edcollection.adding.controll.LineNameControll;
import com.winway.android.edcollection.adding.controll.MarkerCollectControll;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.entity.LineNodeSort;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.base.BaseBll;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.entity.DataItem;
import com.winway.android.util.DateUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.UUIDGen;

/**
 * 同沟信息业务逻辑
 * 
 * @author zgq
 *
 */
public class SameGrooveInformationBll extends BaseBll<Object> {

	public SameGrooveInformationBll(Context context, String dbUrl) {
		super(context, dbUrl);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取线路附属设备列表
	 * 
	 * @return
	 */
	public ArrayList<ResourceEnum> getDevicePointList() {
		ArrayList<ResourceEnum> list = new ArrayList<ResourceEnum>();
		list.add(ResourceEnum.HWG);
		list.add(ResourceEnum.DZBQ);
		list.add(ResourceEnum.DLFZX);
		list.add(ResourceEnum.DYDLFZX);
		list.add(ResourceEnum.ZJJT);
		return list;
	}

	/**
	 * 根据对象ID查找线路附属设备
	 * 
	 * @param objId
	 * @return
	 */
	public EcLineDeviceEntity getDeviceByObjId(String objId) {
		String expr = "DEV_OBJ_ID ='" + objId + "'";
		List<EcLineDeviceEntity> lineDevices = this.queryByExpr2(EcLineDeviceEntity.class, expr);
		if (lineDevices != null && lineDevices.size() > 0) {
			return lineDevices.get(0);
		}
		return null;
	}

	/**
	 * 检查某条线的线路节点序号的合法性（true表示已经存在，不能使用了；false表示不存在，可以使用）
	 * 
	 * @param sort
	 * @param lineId
	 * @return
	 */
	public boolean checkLineNodeSort(String sort, String lineId) {
		String expr = "N_INDEX=" + Integer.parseInt(sort) + " and EC_LINE_ID='" + lineId + "'";
		List<EcLineNodesEntity> lineNodes = this.queryByExpr2(EcLineNodesEntity.class, expr);
		if (lineNodes != null && lineNodes.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据关键字查询线路
	 * 
	 * @param keyWord
	 * @param context
	 * @return
	 */
	public List<DataItem> searchLineByKeyword(String keyWord, Context context) {
		List<DataItem> dataItems = new ArrayList<>();
		BaseDBUtil baseDBUtil = new BaseDBUtil(context, GlobalEntry.prjDbUrl);
		EcLineEntity queryEntity = new EcLineEntity();
		queryEntity.setName(keyWord);
		queryEntity.setLineNo(keyWord);
		List<EcLineEntity> lines = new ArrayList<>();
		try {
			lines = baseDBUtil.excuteQuery(EcLineEntity.class, queryEntity, "like", "or", null, null);
			if (lines != null && lines.size() > 0) {
				for (EcLineEntity lineEntity : lines) {
					DataItem dataItem = new DataItem();
					dataItem.setBusiObj(lineEntity);
					dataItem.setName(lineEntity.getName());
					dataItems.add(dataItem);
				}
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataItems;
	}
	
	/**
	 * 查询所有线路
	 * 
	 * @param context
	 * @return
	 */
	public List<DataItem> searchAllLines(Context context) {
		List<DataItem> dataItems = new ArrayList<>();
		BaseDBUtil baseDBUtil = new BaseDBUtil(context, GlobalEntry.prjDbUrl);
		List<EcLineEntity> lines = new ArrayList<>();
		try {
			lines = baseDBUtil.getAll(EcLineEntity.class);
			if (lines != null && lines.size() > 0) {
				for (EcLineEntity lineEntity : lines) {
					DataItem dataItem = new DataItem();
					dataItem.setBusiObj(lineEntity);
					dataItem.setName(lineEntity.getName());
					dataItems.add(dataItem);
				}
			}
		} catch (DbException e) {
			e.printStackTrace();
		}
		return dataItems;
	}

	/**
	 * 设置线路节点序号
	 * @param dataCacheBll
	 * @param lineId
	 * @param icLineNodeSort
	 */
	public void setLineNodeSortEdt(DataCacheBll dataCacheBll, String lineId, InputComponent icLineNodeSort) {
		List<LineNodeSort> lineNodeSort = dataCacheBll.findLineNodeSortById(lineId);
		if (lineNodeSort != null && lineNodeSort.size() > 0) {
			if (lineNodeSort.size() == 1) {
				icLineNodeSort.setEdtText((lineNodeSort.get(0).getSort() + GlobalEntry.node_variance) + "");
			} else {
				int variance = lineNodeSort.get(0).getSort() - lineNodeSort.get(1).getSort();
				icLineNodeSort.setEdtText((lineNodeSort.get(0).getSort() + variance) + "");
			}
		} else {
			icLineNodeSort.setEdtText("");
		}
	}
	
	public void setHwgVoData() {
		EcHwgEntityVo ecHwgEntityVo = LineNameControll.ditchCable.getHwgEntityVo();
		if (ecHwgEntityVo== null) {
			ecHwgEntityVo = new EcHwgEntityVo();
			ecHwgEntityVo.setObjId(UUIDGen.randomUUID());
			ecHwgEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecHwgEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecHwgEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecHwgEntityVo.setOperateMark(1);// 编辑下新增
			}
		}
		LineNameControll.ditchCable.setHwgEntityVo(ecHwgEntityVo);
		// 修改内存的数据
		if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
			for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
				if (LineNameControll.ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
					LayingDitchControll.ditchCableList.get(i).setHwgEntityVo(ecHwgEntityVo);
				}
			}
		}
	}
	
	public void setDzbqVoData(Context context) {
		// 初始设备名称为BQ+标识器ID号
		MarkerCollectControll markerCollectControll = MarkerCollectControll.getInstance();
		BasicInfoFragment basicInfoFragment = markerCollectControll.getBasicInfoFragment();
		String markerId = basicInfoFragment.getAction().getViewHolder().getIcMarkerId().getEdtTextValue();
		if (markerId.equals("")) {
			ToastUtil.show(context, "请先输入标识器ID号", 100);
			return;
		}			
		EcLineLabelEntityVo ecLabelEntityVo = LineNameControll.ditchCable.getLineLabelEntityVo();
		if (ecLabelEntityVo== null) {
			ecLabelEntityVo = new EcLineLabelEntityVo();
			ecLabelEntityVo.setObjId(UUIDGen.randomUUID());
			ecLabelEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecLabelEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecLabelEntityVo.setCjsj(DateUtils.getDate());
			ecLabelEntityVo.setDevName("BQ"+markerId);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecLabelEntityVo.setOperateMark(1);// 编辑下新增
			}
		}
		LineNameControll.ditchCable.setLineLabelEntityVo(ecLabelEntityVo);
		// 修改内存的数据
		if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
			for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
				if (LineNameControll.ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
					LayingDitchControll.ditchCableList.get(i).setLineLabelEntityVo(ecLabelEntityVo);
				}
			}
		}
	}
	
	public void setDlfzxVoData() {
		EcDlfzxEntityVo ecDlfzxEntityVo = LineNameControll.ditchCable.getDlfzxEntityVo();
		if (ecDlfzxEntityVo== null) {
			ecDlfzxEntityVo = new EcDlfzxEntityVo();
			ecDlfzxEntityVo.setObjId(UUIDGen.randomUUID());
			ecDlfzxEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecDlfzxEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecDlfzxEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecDlfzxEntityVo.setOperateMark(1);// 编辑下新增
			}
		}
		LineNameControll.ditchCable.setDlfzxEntityVo(ecDlfzxEntityVo);
		// 修改内存的数据
		if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
			for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
				if (LineNameControll.ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
					LayingDitchControll.ditchCableList.get(i).setDlfzxEntityVo(ecDlfzxEntityVo);
				}
			}
		}
	}
	
	public void setDydlfzxVoData() {
		EcDydlfzxEntityVo ecDydlfzxEntityVo = LineNameControll.ditchCable.getDydlfzxEntityVo();
		if (ecDydlfzxEntityVo== null) {
			ecDydlfzxEntityVo = new EcDydlfzxEntityVo();
			ecDydlfzxEntityVo.setObjId(UUIDGen.randomUUID());
			ecDydlfzxEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecDydlfzxEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecDydlfzxEntityVo.setCjsj(DateUtils.getDate());
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecDydlfzxEntityVo.setOperateMark(1);// 编辑下新增
			}
		}
		LineNameControll.ditchCable.setDydlfzxEntityVo(ecDydlfzxEntityVo);
		// 修改内存的数据
		if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
			for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
				if (LineNameControll.ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
					LayingDitchControll.ditchCableList.get(i).setDydlfzxEntityVo(ecDydlfzxEntityVo);
				}
			}
		}
	}
	
	public void setZjjtVoData(DitchCable ditchCable) {
		EcMiddleJointEntityVo ecZjjtEntityVo = LineNameControll.ditchCable.getMiddleJointEntityVo();
		String deviceName=null;
		if (ecZjjtEntityVo== null) {
			ecZjjtEntityVo = new EcMiddleJointEntityVo();
			ecZjjtEntityVo.setObjId(UUIDGen.randomUUID());
			ecZjjtEntityVo.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecZjjtEntityVo.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecZjjtEntityVo.setCjsj(DateUtils.getDate());
			//线路名称+电缆中间接头ZJJT+001
			List<EcLineDeviceEntity> zjjtList = getZjjtList(ditchCable);
			if(zjjtList.size()==0){
				deviceName = ditchCable.getLineName()+"电缆中间接头ZJJT001";
			}else if(zjjtList.size()>0 && zjjtList.size()<9){
				String no = zjjtList.size()+1+"";
				deviceName = ditchCable.getLineName()+"电缆中间接头ZJJT00"+no;
			}else if(zjjtList.size()>=9 && zjjtList.size()<99){
				String no = zjjtList.size()+1+"";
				deviceName = ditchCable.getLineName()+"电缆中间接头ZJJT0"+no;
			}else if(zjjtList.size()>=99 && zjjtList.size()<Integer.MAX_VALUE){
				String no = zjjtList.size()+1+"";
				deviceName = ditchCable.getLineName()+"电缆中间接头ZJJT"+no;
			}
			LineNameControll.zjjtName = deviceName;
			ecZjjtEntityVo.setSbmc(deviceName);
			// 如果是在编辑下添加的设置一个标志在保存数据时区分
			if (GlobalEntry.editNode) {
				ecZjjtEntityVo.setOperateMark(1);// 编辑下新增
			}
		}
		LineNameControll.ditchCable.setMiddleJointEntityVo(ecZjjtEntityVo);
		// 修改内存的数据
		if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
			for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
				if (LineNameControll.ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
					LayingDitchControll.ditchCableList.get(i).setMiddleJointEntityVo(ecZjjtEntityVo);
				}
			}
		}
	}
	/**
	 * 获取具体线路下所有的中间接头
	 * @param ditchCable
	 */
	public List<EcLineDeviceEntity> getZjjtList(DitchCable ditchCable){
		//标签类型30301
		String sql = "select * from ec_line_device where DEVICE_TYPE ='"+ResourceEnum.ZJJT.getValue()+"' and LINE_NO ='"+ditchCable.getLineNo()+"'"; 
		try {
			List<EcLineDeviceEntity> list = excuteQuery(EcLineDeviceEntity.class, sql);
			return list;
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public EcMiddleJointEntityVo findZjjtById() {
		try {
			String objId = LineNameControll.ditchCable.getMiddleJointEntityVo().getObjId();
			return findById(EcMiddleJointEntityVo.class, objId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public EcLineLabelEntityVo findLabelById() {
		try {
			String objId = LineNameControll.ditchCable.getLineLabelEntityVo().getObjId();
			return findById(EcLineLabelEntityVo.class, objId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public EcHwgEntityVo findHwgById() {
		try {
			String objId = LineNameControll.ditchCable.getHwgEntityVo().getObjId();
			return findById(EcHwgEntityVo.class, objId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public EcDlfzxEntityVo findDlfzxById() {
		try {
			String objId = LineNameControll.ditchCable.getDlfzxEntityVo().getObjId();
			return findById(EcDlfzxEntityVo.class, objId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public EcDydlfzxEntityVo findDydlfzxById() {
		try {
			String objId = LineNameControll.ditchCable.getDydlfzxEntityVo().getObjId();
			return findById(EcDydlfzxEntityVo.class, objId);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
