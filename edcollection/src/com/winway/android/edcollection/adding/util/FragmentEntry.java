package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.adding.controll.LayingDitchControll;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.base.util.BeanUtils;
import com.winway.android.edcollection.project.entity.EmDangerInfoEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcNodeEntityVo;
import com.winway.android.edcollection.project.vo.LayingDitchVo;
import com.winway.android.media.photo.photoselector.model.PhotoModel;

/**
 * 保存片段里的数据
 * 
 * @author Administrator
 *
 */
public class FragmentEntry {

	private static FragmentEntry instance;

	public static FragmentEntry getInstance() {
		if (instance == null) {
			synchronized (FragmentEntry.class) {
				if (instance == null) {
					instance = new FragmentEntry();
				}
			}
		}

		return instance;
	}

	/**
	 * 判断是否第一次进入编辑
	 */
	public static boolean isFirstIn = true;

	/**
	 * 判断是否地图选点获取坐标
	 */
	public static boolean isSelectMap = false;

	/**
	 * 基本信息片段里的数据
	 */
	public EcNodeEntityVo ecNodeEntityCache = new EcNodeEntityVo();
	/**
	 * 同沟信息片段里的数据
	 */
	public LayingDitchVo layingDitchVo = new LayingDitchVo();
	/**
	 * 现场照片背景图数据
	 */
	public String backgroundPhotoAbsolutelyRoot = null;
	/**
	 * 现场照片位置图数据
	 */
	public String positionPhotoAbsolutelyRoot = null;

	/**
	 * 路径点附件
	 */
	public ArrayList<String> attachs = new ArrayList<>();
	/**
	 * 通道现状片段里的数据
	 */
	public EmDangerInfoEntity emDangerInfoEntity = new EmDangerInfoEntity();

	public ArrayList<com.winway.android.edcollection.adding.controll.ChannelControll.ChannelDataEntity> channelList = new ArrayList<>();

	/**
	 * 临时变电站附件
	 */
	public ArrayList<String> stationAttach = new ArrayList<>();

	/**
	 * 重置所有
	 */
	public void resetAllData() {
		// 基本信息页
		BeanUtils.setObjectFieldsEmpty(this.ecNodeEntityCache);
		BasicInfoControll.ecSubstationEntityVo = null;// 变电站
		BasicInfoControll.ecTowerEntityVo = null;// 杆塔
		BasicInfoControll.ecTransformerEntityVo = null;// 变压器
		BasicInfoControll.ecDistributionRoomEntityVo = null;// 配电房
		BasicInfoControll.ecWorkWellEntityVo = null;// 工井
		BasicInfoControll.ecXsbdzEntityVo = null;// 箱式变电站
		BasicInfoControll.ecKgzEntityVo = null;// 开关站
		BasicInfoControll.ecDypdxEntityVo = null;// 低压配电箱

		// 同沟信息
		BeanUtils.setObjectFieldsEmpty(this.layingDitchVo);
		LayingDitchControll.ditchCableList.clear();
		// 现场照片
		this.backgroundPhotoAbsolutelyRoot = null;
		this.positionPhotoAbsolutelyRoot = null;
		// 通道现状
		BeanUtils.setObjectFieldsEmpty(this.emDangerInfoEntity);
		channelList.clear();
		this.attachs.clear();
		this.stationAttach.clear();
		isFirstIn = true;
	}
}
