package com.winway.android.edcollection.adding.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.viewholder.BasicInfoViewHolder;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.util.LogUtil;
import com.winway.android.util.StringUtils;

/**
 * 基本信息
 * 
 * @author lyh
 * @version 创建时间：2016年12月12日 上午11:57:43
 * 
 */
public class BasicInfoFragment extends BaseFragment<BasicInfoControll, BasicInfoViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.marker_basicinfo, container, false);
		LogUtil.e("BasicInfoFragment", "initView");
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtil.e("BasicInfoFragment", "onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.e("BasicInfoFragment", "onCreate");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.e("BasicInfoFragment", "onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.e("BasicInfoFragment", "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.e("BasicInfoFragment", "onResume");
		BasicInfoViewHolder viewHolder = this.getAction().getViewHolder();
		// TODO
		// 标识类型
		if (FragmentEntry.getInstance().ecNodeEntityCache.getMarkerType() != null) {
			int markTypeId = FragmentEntry.getInstance().ecNodeEntityCache.getMarkerType();
			if (markTypeId == NodeMarkerType.BSQ.getValue()) {// 球
				if (null != viewHolder.getRgMarkerType().getChildAt(0)) {
					viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(0).getId());
				}
			} else if (markTypeId == NodeMarkerType.BSD.getValue()) {// 钉
				if (null != viewHolder.getRgMarkerType().getChildAt(1)) {
					viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(1).getId());
				}
			} else if (markTypeId == NodeMarkerType.XND.getValue()) {// 路径点
				if (null != viewHolder.getRgMarkerType().getChildAt(2)) {
					viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(2).getId());
				}
			} else if (markTypeId == NodeMarkerType.GT.getValue()) {// 杆塔
				if (null != viewHolder.getRgMarkerType().getChildAt(3)) {
					viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(3).getId());
				}
			} else if (markTypeId == NodeMarkerType.AJH.getValue()) {// 安键环
				if (null != viewHolder.getRgMarkerType().getChildAt(4)) {
					viewHolder.getRgMarkerType().check(viewHolder.getRgMarkerType().getChildAt(4).getId());
				}
			}
		}
		// 坐标编号
		if (FragmentEntry.getInstance().ecNodeEntityCache.getMarkerNo() != null) {
			viewHolder.getIcMarkerId().setEdtText(FragmentEntry.getInstance().ecNodeEntityCache.getMarkerNo());
		}
		// 敷设方式
		// if (FragmentEntry.getInstance().ecNodeEntityCache.getLayType() != null) {
		// viewHolder.getIscLayType().setEdtTextValue(FragmentEntry.getInstance().ecNodeEntityCache.getLayType());
		// }
		// 坐标
		if (FragmentEntry.getInstance().ecNodeEntityCache.getLongitude() != null) {
			viewHolder.getIcLongitude().setEdtText(FragmentEntry.getInstance().ecNodeEntityCache.getLongitude() + "");
		}
		if (FragmentEntry.getInstance().ecNodeEntityCache.getLatitude() != null) {
			viewHolder.getIcLatitude().setEdtText(FragmentEntry.getInstance().ecNodeEntityCache.getLatitude() + "");
		}
		if (FragmentEntry.getInstance().ecNodeEntityCache.getCoordinateNo() != null) {
			viewHolder.getIcCoordNo().setEdtText(FragmentEntry.getInstance().ecNodeEntityCache.getCoordinateNo());
		}
		// 安转位置、地理位置
		if (FragmentEntry.getInstance().ecNodeEntityCache.getInstallPosition() != null) {
			viewHolder.getIscInstallPath()
					.setEdtTextValue(FragmentEntry.getInstance().ecNodeEntityCache.getInstallPosition());
		}
		if (FragmentEntry.getInstance().ecNodeEntityCache.getGeoLocation() != null) {
			viewHolder.getIcGeographyPos().setEdtText(FragmentEntry.getInstance().ecNodeEntityCache.getGeoLocation());
		}

		// 宽、高
		if (FragmentEntry.getInstance().ecNodeEntityCache.getCableWidth() != null) {
			viewHolder.getIcWidth().setEdtText(FragmentEntry.getInstance().ecNodeEntityCache.getCableWidth() + "");
		}
		if (FragmentEntry.getInstance().ecNodeEntityCache.getCableDeepth() != null) {
			viewHolder.getIcFlootHeight()
					.setEdtText(FragmentEntry.getInstance().ecNodeEntityCache.getCableDeepth() + "");
		}
		// 设备描述、备注
		if (FragmentEntry.getInstance().ecNodeEntityCache.getDeviceDesc() != null) {
			viewHolder.getIscDescribe().setEdtTextValue(FragmentEntry.getInstance().ecNodeEntityCache.getDeviceDesc());
		}
		if (FragmentEntry.getInstance().ecNodeEntityCache.getRemark() != null) {
			viewHolder.getIscOther().setEdtTextValue(FragmentEntry.getInstance().ecNodeEntityCache.getRemark());
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.e("BasicInfoFragment", "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtil.e("BasicInfoFragment", "onStop");
		BasicInfoViewHolder viewHolder = this.getAction().getViewHolder();
		// TODO
		// 标识类型
		int rBtnGroupId = viewHolder.getRgMarkerType().getCheckedRadioButtonId();

		if (null != viewHolder.getRgMarkerType().getChildAt(0) && null != viewHolder.getRgMarkerType().getChildAt(1)
				&& null != viewHolder.getRgMarkerType().getChildAt(2)
				&& null != viewHolder.getRgMarkerType().getChildAt(3)
				&& null != viewHolder.getRgMarkerType().getChildAt(4)) {
			if (rBtnGroupId == viewHolder.getRgMarkerType().getChildAt(0).getId()) {
				FragmentEntry.getInstance().ecNodeEntityCache.setMarkerType(NodeMarkerType.BSQ.getValue());
			} else if (rBtnGroupId == viewHolder.getRgMarkerType().getChildAt(1).getId()) {
				FragmentEntry.getInstance().ecNodeEntityCache.setMarkerType(NodeMarkerType.BSD.getValue());
			} else if (rBtnGroupId == viewHolder.getRgMarkerType().getChildAt(2).getId()) {
				FragmentEntry.getInstance().ecNodeEntityCache.setMarkerType(NodeMarkerType.XND.getValue());
			} else if (rBtnGroupId == viewHolder.getRgMarkerType().getChildAt(3).getId()) {
				FragmentEntry.getInstance().ecNodeEntityCache.setMarkerType(NodeMarkerType.GT.getValue());
			} else if (rBtnGroupId == viewHolder.getRgMarkerType().getChildAt(4).getId()) {
				FragmentEntry.getInstance().ecNodeEntityCache.setMarkerType(NodeMarkerType.AJH.getValue());
			}
		}
		// 标识器编号
		FragmentEntry.getInstance().ecNodeEntityCache
				.setMarkerNo(StringUtils.nullStrToEmpty(viewHolder.getIcMarkerId().getEdtTextValue()));
		// 坐标
		if (!viewHolder.getIcLongitude().getEdtTextValue().isEmpty()) {
			FragmentEntry.getInstance().ecNodeEntityCache
					.setLongitude(Double.parseDouble(viewHolder.getIcLongitude().getEdtTextValue()));
		}
		if (!viewHolder.getIcLatitude().getEdtTextValue().isEmpty()) {
			FragmentEntry.getInstance().ecNodeEntityCache
					.setLatitude(Double.parseDouble(viewHolder.getIcLatitude().getEdtTextValue()));
		}
		// 敷设方式
		// FragmentEntry.getInstance().ecNodeEntityCache.setLayType(StringUtils.nullStrToEmpty(
		// viewHolder.getIscLayType().getEdtTextValue()));
		// 坐标编号
		FragmentEntry.getInstance().ecNodeEntityCache
				.setCoordinateNo(StringUtils.nullStrToEmpty(viewHolder.getIcCoordNo().getEdtTextValue()));
		// 安装位置
		FragmentEntry.getInstance().ecNodeEntityCache
				.setInstallPosition(StringUtils.nullStrToEmpty(viewHolder.getIscInstallPath().getEdtTextValue()));
		// 地理位置
		FragmentEntry.getInstance().ecNodeEntityCache
				.setGeoLocation(StringUtils.nullStrToEmpty(viewHolder.getIcGeographyPos().getEdtTextValue()));
		// 宽、高
		if (!viewHolder.getIcWidth().getEdtTextValue().isEmpty()) {
			FragmentEntry.getInstance().ecNodeEntityCache
					.setCableWidth(Double.parseDouble(viewHolder.getIcWidth().getEdtTextValue()));
		}
		if (!viewHolder.getIcFlootHeight().getEdtTextValue().isEmpty()) {
			FragmentEntry.getInstance().ecNodeEntityCache
					.setCableDeepth(Double.parseDouble(viewHolder.getIcFlootHeight().getEdtTextValue()));
		}
		// 描述、备注
		FragmentEntry.getInstance().ecNodeEntityCache
				.setDeviceDesc(StringUtils.nullStrToEmpty(viewHolder.getIscDescribe().getEdtTextValue()));
		FragmentEntry.getInstance().ecNodeEntityCache
				.setRemark(StringUtils.nullStrToEmpty(viewHolder.getIscOther().getEdtTextValue()));
		// 保存
		BasicInfoControll.markerNo = viewHolder.getIcMarkerId().getEdtTextValue();

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.e("BasicInfoFragment", "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.e("BasicInfoFragment", "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtil.e("BasicInfoFragment", "onDetach");
	}

}
