package com.winway.android.edcollection.adding.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.LayingDitchControll;
import com.winway.android.edcollection.adding.util.FragmentEntry;
import com.winway.android.edcollection.adding.viewholder.LayingDitchViewHolder;
import com.winway.android.edcollection.base.view.BaseFragment;
import com.winway.android.util.LogUtil;
import com.winway.android.util.StringUtils;

/**
 * 设备信息
 * 
 * @author lyh
 * @version 创建时间：2016年12月12日 上午11:57:43
 * 
 */
public class LayingDitchFragment extends BaseFragment<LayingDitchControll, LayingDitchViewHolder> {

	@Override
	protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.laying_ditch, container, false);
		LogUtil.e("LayingDitchFragment", "initView");
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		LogUtil.e("LayingDitchFragment", "onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LogUtil.e("LayingDitchFragment", "onCreate");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		LogUtil.e("LayingDitchFragment", "onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.e("LayingDitchFragment", "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.e("LayingDitchFragment", "onResume");
		LayingDitchViewHolder viewHolder = this.getAction().getViewHolder();

		if (FragmentEntry.getInstance().layingDitchVo != null) {
			if (FragmentEntry.getInstance().layingDitchVo.getLineName() != null) {
				viewHolder.getInscLineName().setEdtTextValue(
						StringUtils.nullStrToEmpty(FragmentEntry.getInstance().layingDitchVo.getLineName()));
			}
			if (FragmentEntry.getInstance().layingDitchVo.getLineNodeSort() != null) {
				viewHolder.getIcLineNodeSort().setEdtText(
						StringUtils.nullStrToEmpty(FragmentEntry.getInstance().layingDitchVo.getLineNodeSort()));
			}
			if (FragmentEntry.getInstance().layingDitchVo.getLayType() != null) {
				viewHolder.getInsclayType().setEdtTextValue(
						StringUtils.nullStrToEmpty(FragmentEntry.getInstance().layingDitchVo.getLayType()));
			}
			if (FragmentEntry.getInstance().layingDitchVo.getLayingDitchNum() != null) {
				viewHolder.getIcLoopNum()
						.setEdtText(FragmentEntry.getInstance().layingDitchVo.getLayingDitchNum() + "");
			}
			if(FragmentEntry.getInstance().layingDitchVo.getPreviousNodeDistance()!=null){
				viewHolder.getIcDistance().setEdtText(FragmentEntry.getInstance().layingDitchVo.getPreviousNodeDistance()+"");
			}
			if(FragmentEntry.getInstance().layingDitchVo.getDeviceDescription()!=null){
				viewHolder.getIcDeviceDescription().setEdtText(FragmentEntry.getInstance().layingDitchVo.getDeviceDescription()+"");
			}
			if(FragmentEntry.getInstance().layingDitchVo.getUnknownLoopNum()!=null){
				viewHolder.getIcUnknownLoopNum().setEdtText(FragmentEntry.getInstance().layingDitchVo.getUnknownLoopNum()+"");
			}
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		LogUtil.e("LayingDitchFragment", "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		LogUtil.e("LayingDitchFragment", "onStop");
		LayingDitchViewHolder viewHolder = this.getAction().getViewHolder();
		FragmentEntry.getInstance().layingDitchVo
				.setLineName(StringUtils.nullStrToEmpty(viewHolder.getInscLineName().getEdtTextValue()));

		if (!viewHolder.getIcLineNodeSort().getEdtTextValue().isEmpty()) {
			String regex = "\\d{1,9}|1\\d{9}|20\\d{8}|21[0-3]\\d{7}|214[0-6]\\d{6}|2147[0-3]\\d{5}|21474[0-7]\\d{4}|214748[0-2]\\d{3}|2147483[0-5]\\d{2}|21474836[0-3]\\d{1}|214748364[0-7]";
			if (viewHolder.getIcLineNodeSort().getEdtTextValue().matches(regex)) {
				FragmentEntry.getInstance().layingDitchVo
						.setLineNodeSort(Integer.valueOf(viewHolder.getIcLineNodeSort().getEdtTextValue()));
			}
		}

		FragmentEntry.getInstance().layingDitchVo
				.setLayType(StringUtils.nullStrToEmpty(viewHolder.getInsclayType().getEdtTextValue()));
		if (!viewHolder.getIcLoopNum().getEdtTextValue().isEmpty()) {
			FragmentEntry.getInstance().layingDitchVo
					.setLayingDitchNum(Integer.valueOf(viewHolder.getIcLoopNum().getEdtTextValue()));
		}
		if (!viewHolder.getIcUnknownLoopNum().getEdtTextValue().isEmpty()) {
			FragmentEntry.getInstance().layingDitchVo
			.setUnknownLoopNum(Integer.valueOf(viewHolder.getIcUnknownLoopNum().getEdtTextValue()));
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		LogUtil.e("LayingDitchFragment", "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.e("LayingDitchFragment", "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		LogUtil.e("LayingDitchFragment", "onDetach");
	}

}
