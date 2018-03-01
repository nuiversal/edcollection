package com.winway.android.edcollection.adding.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.opengl.Visibility;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.adding.controll.MarkerCollectControll;
import com.winway.android.edcollection.adding.fragment.BasicInfoFragment;
import com.winway.android.edcollection.adding.viewholder.CommentChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.datacenter.entity.LineDevicesEntity;
import com.winway.android.edcollection.datacenter.entity.NodeDevicesEntity;
import com.winway.android.edcollection.datacenter.util.DataDealUtil;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.util.DateUtils;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.ToastUtils;

/**
 * @author lyh
 * @data 2017年3月5日
 */
public class CommentChannelUtil {
	private Context context;
	private String prjGDBUrl;
	public static DataCenterImpl treeDataCenter;
	private String oid = null;
	public CommentChannelUtil(Context context, String prjGDBUrl) {
		super();
		this.context = context;
		this.prjGDBUrl = prjGDBUrl;
		treeDataCenter = new DataCenterImpl(this.context, this.prjGDBUrl);
	}
	
	/**
	 * 初始通道公共部分
	 * @param commentChannelViewHolder
	 */
	public void initCommentSetting(CommentChannelViewHolder commentChannelViewHolder,Activity mActivity,GlobalCommonBll commonBll) {
		if (BasicInfoControll.BMaplocationDistrict != null&& BasicInfoControll.BMaplocationStreet != null
				&& BasicInfoControll.BMaplocationCity!=null) {
			commentChannelViewHolder.getInconMc()
				.setEdtText(BasicInfoControll.BMaplocationDistrict + "-"+ BasicInfoControll.BMaplocationStreet + "-");
			commentChannelViewHolder.getInconSszrq().setEdtText(BasicInfoControll.BMaplocationDistrict);
			commentChannelViewHolder.getInconSsds().setEdtText(BasicInfoControll.BMaplocationCity);
			if (BasicInfoControll.mLocationClient != null) {
				BasicInfoControll.mLocationClient.stop();
			}
		} /*else{
			ToastUtil.show(mActivity, "定位失败,按照规范填写通道名称", 200);
		}*/
		//初始化通道材质
		String channelMaterialTypeNo = "channel_material";
		List<String> channelMaterialList = commonBll.getDictionaryNameList(channelMaterialTypeNo);
		if(channelMaterialList!=null && channelMaterialList.size()>0){
			InputSelectAdapter channelMaterialAdapter = new InputSelectAdapter(mActivity, channelMaterialList);
			commentChannelViewHolder.getInscTdcz().setAdapter(channelMaterialAdapter);
		}
		// 初始电压等级下拉列表
		String dydjTypeNo = "010401";// 电压等级
		InputSelectAdapter dydjAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(dydjTypeNo));
		commentChannelViewHolder.getInscDydj().setAdapter(dydjAdapter);
		// 状态
		String ztTypeNo = "channel_state";// 状态
		List<String> dictionaryNameList = commonBll.getDictionaryNameList(ztTypeNo);
		if (null != dictionaryNameList) {
			InputSelectAdapter ztAdapter = new InputSelectAdapter(mActivity, dictionaryNameList);
			commentChannelViewHolder.getInscZt().setAdapter(ztAdapter);
		}		
		// 是否为最终路径点下拉列表
		List<String> zdsbbh = new ArrayList<>();
		zdsbbh.add("否");
		zdsbbh.add("是");
		InputSelectAdapter zdAdapter = new InputSelectAdapter(mActivity, zdsbbh);
		commentChannelViewHolder.getInconZdsbbh().setAdapter(zdAdapter);
		//限制输入方式只能是数据或小数点
		commentChannelViewHolder.getInconTdkd().getEditTextView().setInputType
		(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		commentChannelViewHolder.getInconTdsd().getEditTextView().setInputType
		(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		commentChannelViewHolder.getInconSyrl().getEditTextView()
				.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		commentChannelViewHolder.getInconZrl().getEditTextView()
				.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		commentChannelViewHolder.getInconTdcd().getEditTextView()
				.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		if (MarkerCollectControll.getInstance() != null) {
			BasicInfoFragment basicInfoFragment = MarkerCollectControll.getInstance().getBasicInfoFragment();
			//输入框中无值时将基本信息界面中的电缆沟宽度值赋值到此输入框中
			String width = basicInfoFragment.getAction().getViewHolder().getIcWidth().getEdtTextValue();
			if (!width.equals("") && !width.equals("0.0")) {
				commentChannelViewHolder.getInconTdkd().setEdtText(StringUtils.nullStrToEmpty(width));
			}
			//输入框中无值时将基本信息界面中的沟底到地面高度值赋值到输入框中
			String height = basicInfoFragment.getAction().getViewHolder().getIcFlootHeight().getEdtTextValue();
			if (!height.equals("") && !height.equals("0.0")) {
				commentChannelViewHolder.getInconTdsd().setEdtText(StringUtils.nullStrToEmpty(height));
			}
		}
	}

	/**
	 * 保存通道数据
	 * 
	 * @param channelEntity
	 * @param id
	 * @param commentChannelViewHolder
	 */
	public void saveChannelData(EcChannelEntity channelEntity, Class<?> deviceClass, String id,
			CommentChannelViewHolder commentChannelViewHolder) {
		// 通道id存在的话说明执行的是编辑操作，则id，项目id等不需要再继续添加
		if (TextUtils.isEmpty(channelEntity.getEcChannelId())) {
			channelEntity.setEcChannelId(id);
			channelEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			channelEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			channelEntity.setCreateTime((DateUtils.getDate()));
		}
		channelEntity.setUpdateTime((DateUtils.getDate()));
		channelEntity.setChannelType(ResourceDeviceUtil.getDeviceCode(deviceClass));
		channelEntity.setSsdltd(ResourceDeviceUtil.getDeviceCode(deviceClass));
		if (treeDataCenter != null) {
			String start = "";
			NodeDevicesEntity startNodeDevice = treeDataCenter.getPathNodeDetails(channelEntity.getStartDeviceNum(),
					null, null);
			Object[] startDevice = DataDealUtil.pickUpFirstNodeDevice(startNodeDevice);
			if (null == startDevice) {
				LineDevicesEntity startNodeLineDevice = treeDataCenter
						.getNodeLineDevicesList(channelEntity.getStartDeviceNum(), null, null);
				Object[] startLineDevice = DataDealUtil.pickUpFirstLineDevice(startNodeLineDevice);
				if (null != startLineDevice) {
					start = startLineDevice[2] + "";
				}
			} else {
				start = startDevice[2] + "";
			}
			if (startDevice != null) {
				channelEntity.setStartDeviceNum(startDevice[0] + "");
			}
		}
		String end = "";
		NodeDevicesEntity endNodeDevice = treeDataCenter.getPathNodeDetails(channelEntity.getEndDeviceNum(), null,
				null);
		Object[] endDevice = DataDealUtil.pickUpFirstNodeDevice(endNodeDevice);
		if (null == endDevice) {
			LineDevicesEntity endNodeLineDevice = treeDataCenter.getNodeLineDevicesList(channelEntity.getEndDeviceNum(),
					null, null);
			Object[] endLineDevice = DataDealUtil.pickUpFirstLineDevice(endNodeLineDevice);
			if (null != endLineDevice) {
				end = endLineDevice[2] + "";
			}
		} else {
			end = endDevice[2] + "";
		}
		if (endDevice != null) {
			channelEntity.setEndDeviceNum(endDevice[0] + "");
		} else {
			String markerId = commentChannelViewHolder.getInconZdsbbh().getEdtTextValue();
			String markerIdRegex = "\\d{3}-\\d{3}-\\d{4}";
			String yesOrNoRegex = "是|否";
			String oidRegex = "[a-z,0-9]{32}";
			String nullRegex = "";
			if (markerId.matches(markerIdRegex) || markerId.matches(yesOrNoRegex) || markerId.matches(oidRegex)||markerId.matches(nullRegex)) {
				if (markerId.matches(markerIdRegex)) {
					String projectBDUrl = GlobalEntry.prjDbUrl;
					GlobalCommonBll globalCommonBll = new GlobalCommonBll(context, projectBDUrl);
					String sql = "MARKER_NO='" + markerId + "'";
					List<EcNodeEntity> ecNodeEntities = globalCommonBll.queryByExpr2(EcNodeEntity.class, sql);
					if (!ecNodeEntities.isEmpty() && !(ecNodeEntities == null)) {
						for (EcNodeEntity ecNodeEntity : ecNodeEntities) {
							oid = ecNodeEntity.getOid();
						}
					} else {
						ToastUtil.show(context, "没有此标识器的信息，请重新输入！", 200);
					}
				}
				if (markerId.matches(yesOrNoRegex) || markerId.matches(oidRegex) || markerId.matches(nullRegex)) {
					oid = markerId;
				}
				channelEntity.setEndDeviceNum(oid);
			} else {
				ToastUtil.show(context, "输入标识器id不合法，没有进行保存", 200);
			}
		}
		channelEntity.setVoltage(commentChannelViewHolder.getInscDydj().getEdtTextValue().trim());
		channelEntity.setSszrq(commentChannelViewHolder.getInconSszrq().getEdtTextValue());
		channelEntity.setYxdw(commentChannelViewHolder.getInconYxdw().getEdtTextValue());
		channelEntity.setName(commentChannelViewHolder.getInconMc().getEdtTextValue());
		String tdkd = commentChannelViewHolder.getInconTdkd().getEdtTextValue();
		channelEntity.setChannelWidth(tdkd.equals("") ? null : Double.valueOf(tdkd));
		String tdsd = commentChannelViewHolder.getInconTdsd().getEdtTextValue();
		channelEntity.setChannelHeight(tdsd.equals("") ? null : Double.valueOf(tdsd));
		channelEntity.setChannelMaterial(commentChannelViewHolder.getInscTdcz().getEdtTextValue());
		String zt = commentChannelViewHolder.getInscZt().getEdtTextValue();
		if (zt.equals("规划的通道")) {
			channelEntity.setChannelState(0);
		} else if (zt.equals("已建成的通道")) {
			channelEntity.setChannelState(1);
		}
		String syrl = commentChannelViewHolder.getInconSyrl().getEdtTextValue();
		channelEntity.setSurplusCapacity(syrl.isEmpty() ? null : Integer.parseInt(syrl));
		String zrl = commentChannelViewHolder.getInconZrl().getEdtTextValue();
		channelEntity.setTotalCapacity(zrl.isEmpty() ? null : Integer.parseInt(zrl));
		// 通道长度
		channelEntity.setChannelLength(commentChannelViewHolder.getInconTdcd().getEdtTextValue().trim().isEmpty() ? null
				: Double.parseDouble(commentChannelViewHolder.getInconTdcd().getEdtTextValue()));
		// 备注
		channelEntity.setRemarks(commentChannelViewHolder.getInconBz().getEdtTextValue().trim());
	}

	/**
	 * 更新通道数据
	 * 
	 * @param channelEntity
	 * @param commentChannelViewHolder
	 */
	public void updateData(EcChannelEntity channelEntity, CommentChannelViewHolder commentChannelViewHolder ) {

		commentChannelViewHolder.getInconZdsbbh().getEditTextView().setEnabled(false);
		commentChannelViewHolder.getInconMc().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getName()));
		commentChannelViewHolder.getInconQdsbbh().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getStartDeviceNum()));
		commentChannelViewHolder.getInconZdsbbh().setEdtTextValue(StringUtils.nullStrToEmpty(channelEntity.getEndDeviceNum()));
		commentChannelViewHolder.getInconSszrq().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getSszrq()));
		commentChannelViewHolder.getInconSyrl().setEdtText(channelEntity.getSurplusCapacity()==null ? "" : channelEntity.getSurplusCapacity()+"");
		commentChannelViewHolder.getInconYxdw().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getYxdw()));
		if (channelEntity.getChannelState() != null) {
			if (channelEntity.getChannelState() == 0) {
				commentChannelViewHolder.getInscZt().setEdtTextValue("规划的通道");
			} else if (channelEntity.getChannelState() == 1) {
				commentChannelViewHolder.getInscZt().setEdtTextValue("已建成的通道");
			}
		}
		commentChannelViewHolder.getInscDydj().setEdtTextValue(channelEntity.getVoltage() == null ? "" : channelEntity.getVoltage() + "");
		commentChannelViewHolder.getInconZrl().setEdtText(channelEntity.getTotalCapacity() == null ? "" : channelEntity.getTotalCapacity() + "");
		commentChannelViewHolder.getInconBz().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getRemarks()));
		commentChannelViewHolder.getInconTdcd().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getChannelLength()));
		commentChannelViewHolder.getInconTdkd().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getChannelWidth()));
		commentChannelViewHolder.getInconTdsd().setEdtText(StringUtils.nullStrToEmpty(channelEntity.getChannelHeight()));
		commentChannelViewHolder.getInscTdcz().setEdtTextValue(StringUtils.nullStrToEmpty(channelEntity.getChannelMaterial()));
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	/**
	 * 检字段是否为必填字段
	 * @param mActivity
	 * @param commentChannelViewHolder
	 * @return
	 */
	public boolean checkIsNull(Activity mActivity,CommentChannelViewHolder commentChannelViewHolder) {
		if (commentChannelViewHolder.getInconMc().getEdtTextValue().isEmpty()) {
			ToastUtils.show(mActivity, "通道名称不能为空!", 200);
			//获取焦点
			commentChannelViewHolder.getInconMc().getEditTextView().requestFocus();
			return false;
		}
		if (commentChannelViewHolder.getInconTdkd().getEdtTextValue().isEmpty()) {
			ToastUtils.show(mActivity, "通道宽度不能为空!", 200);
			//获取焦点
			commentChannelViewHolder.getInconTdkd().getEditTextView().requestFocus();
			return false;
		}
		if (commentChannelViewHolder.getInconTdsd().getEdtTextValue().isEmpty()) {
			ToastUtils.show(mActivity, "通道深度不能为空!", 200);
			//获取焦点
			commentChannelViewHolder.getInconTdsd().getEditTextView().requestFocus();
			return false;
		}
		if (commentChannelViewHolder.getInscTdcz().getEdtTextValue().isEmpty()) {
			ToastUtils.show(mActivity, "通道材质不能为空!", 200);
			//获取焦点
			commentChannelViewHolder.getInscTdcz().getEditTextView().requestFocus();
			return false;
		}
//		if (commentChannelViewHolder.getInconYxdw().getEdtTextValue().isEmpty()) {
//			ToastUtil.show(mActivity, "运行单位不能为空！", 200);
//			//获取焦点
//			commentChannelViewHolder.getInconYxdw().getEditTextView().requestFocus();
//			return false;
//		}
//		if (commentChannelViewHolder.getInconSsds().getEdtTextValue().isEmpty()) {
//			ToastUtil.show(mActivity, "所属地市不能为空！", 200);
//			//获取焦点
//			commentChannelViewHolder.getInconSsds().getEditTextView().requestFocus();
//			return false;
//		}
//		if (commentChannelViewHolder.getInconSszrq().getEdtTextValue().isEmpty()) {
//			ToastUtil.show(mActivity, "所属责任区不能为空！", 200);
//			//获取焦点
//			commentChannelViewHolder.getInconSszrq().getEditTextView().requestFocus();
//			return false;
//		}
		return true;
	}
}
