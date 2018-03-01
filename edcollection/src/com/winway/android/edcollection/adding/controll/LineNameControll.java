package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.LineDevicePointAdapter;
import com.winway.android.edcollection.adding.bll.CollectCommonBll;
import com.winway.android.edcollection.adding.bll.SameGrooveInformationBll;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.viewholder.LineNameViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtil;

/**
 * 线路名称
 * 
 * @author
 *
 */
public class LineNameControll extends BaseControll<LineNameViewHolder> {

	private SameGrooveInformationBll sameGrooveInformationBll;
	private CollectCommonBll collectCommonBll;
	private LineDevicePointAdapter lineDevicePointAdapter;
	public static final int requestCode_hwg = 10104;// 环网柜
	public static final int requestCode_dzbq = 40102;// 电子标签
	public static final int requestcode_dlfzx = 10106;// 电缆分支箱
	public static final int requestcode_dydlfzx = 10107;// 低压电缆分支箱
	public static final int requestcode_zjjt = 30101;// 中间接头
	private EcHwgEntityVo hwgEntityVo;// 接收环网柜的数据
	private EcLineLabelEntityVo lineLabelEntityVo;// 接收电子标签的数据
	private EcDlfzxEntityVo dlfzxEntityVo;// 接收电缆分支箱的数据
	private EcDydlfzxEntityVo dydlfzxEntityVo;// 接收低压电缆分支箱的数据
	private EcMiddleJointEntityVo middleJointEntityVo;// 电缆中间接头
	public static DitchCable ditchCable;
	private String initSort = null;// 初始序号
	public static String zjjtName;

	@Override
	public void init() {
		String dbUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		sameGrooveInformationBll = new SameGrooveInformationBll(mActivity, dbUrl);
		collectCommonBll = new CollectCommonBll(mActivity, dbUrl);
		initSetting();
		initEvent();
		initData();
	}

	private void initSetting() {
		// 禁止敷设方式人工输入
		viewHolder.getInscLayType().getEditTextView().setEnabled(false);
	}

	// 初始化数据
	private void initData() {

		// 获取intent中传递的同沟电缆
		Intent intent = mActivity.getIntent();
		Bundle bundle = intent.getExtras();
		ditchCable = (DitchCable) bundle.getSerializable("DitchCable");
		initSort = String.valueOf(ditchCable.getLineNodeSort());// 保存初始序号

		// 初始化名称
		viewHolder.getHcHead().getTitleView().setText(ditchCable.getLineName());
		viewHolder.getTvLineName().setText(ditchCable.getLineName());
		viewHolder.getTvLineName().setEnabled(false);
		// 初始化敷设方式下拉列表
		List<String> layList = collectCommonBll.getChannelTypeList();
		final InputSelectAdapter layTypeAdapter = new InputSelectAdapter(mActivity, layList);
		viewHolder.getInscLayType().setAdapter(layTypeAdapter);
		for (int i = 0; i < layList.size(); i++) {
			if (layList.get(i).equals(ditchCable.getLayType())) {
				viewHolder.getInscLayType().setSelect(i);
				break;
			}
		}
		// 初始化于上一节点距离
		Float previousNodeDistance = ditchCable.getPreviousNodeDistance();
		if (previousNodeDistance != null) {
			viewHolder.getIcDistance().setEdtText(previousNodeDistance + "");
		}
		viewHolder.getIcDeviceDesc().setEdtText(ditchCable.getDeviceDescription());
		// 初始化序号
		viewHolder.getInconLineNo().setEdtText(ditchCable.getLineNodeSort() + "");

		// 初始化线路附属设备列表
		ArrayList<ResourceEnum> models = sameGrooveInformationBll.getDevicePointList();
		lineDevicePointAdapter = new LineDevicePointAdapter(mActivity, models, sameGrooveInformationBll, ditchCable);
		viewHolder.getLvListView().setAdapter(lineDevicePointAdapter);

		if (ditchCable.getHwgEntityVo() != null) {
			lineDevicePointAdapter.getCheckedStateList().get(0).put(0, true);
			lineDevicePointAdapter.notifyDataSetInvalidated();
		}
		if (ditchCable.getLineLabelEntityVo() != null) {
			lineDevicePointAdapter.getCheckedStateList().get(1).put(1, true);
			lineDevicePointAdapter.notifyDataSetInvalidated();
		}
		if (ditchCable.getDlfzxEntityVo() != null) {
			lineDevicePointAdapter.getCheckedStateList().get(2).put(2, true);
			lineDevicePointAdapter.notifyDataSetInvalidated();
		}
		if (ditchCable.getDydlfzxEntityVo() != null) {
			lineDevicePointAdapter.getCheckedStateList().get(3).put(3, true);
			lineDevicePointAdapter.notifyDataSetInvalidated();
		}
		if (ditchCable.getMiddleJointEntityVo() != null) {
			lineDevicePointAdapter.getCheckedStateList().get(4).put(4, true);
			lineDevicePointAdapter.notifyDataSetInvalidated();
		}
	}

	// 初始化事件
	private void initEvent() {
		viewHolder.getHcHead().getReturnView().setOnClickListener(orl);
		viewHolder.getHcHead().getOkView().setOnClickListener(orl);
		viewHolder.getInconLineNo()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getIcDistance()
				.setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}

	private OnClickListener orl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: {
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: {
				if (viewHolder.getInconLineNo().getEdtTextValue().isEmpty()) {
					ToastUtil.show(mActivity, "序号不能为空", 200);
					return;
				}
				saveData();
				break;
			}

			default:
				break;
			}
		}
	};

	// 保存数据
	protected void saveData() {
		String lineName = viewHolder.getTvLineName().getText().toString();
		String sort = viewHolder.getInconLineNo().getEdtTextValue();
		String layType = viewHolder.getInscLayType().getEdtTextValue();
		String distance = viewHolder.getIcDistance().getEdtTextValue();
		// 检查序号的合法性
		if (!sort.equals(initSort) && sameGrooveInformationBll.checkLineNodeSort(sort, ditchCable.getLineId())) {
			ToastUtil.show(mActivity, "线路节点序号不合法", 200);
			return;
		}
		if (!TextUtils.isEmpty(distance)) {
			Float.parseFloat(distance);
			ditchCable.setPreviousNodeDistance(Float.parseFloat(distance));
		}
		ditchCable.setLayType(layType);
		ditchCable.setLineName(lineName);
		ditchCable.setLineNodeSort(Integer.valueOf(sort));
		ditchCable.setDeviceDescription(viewHolder.getIcDeviceDesc().getEdtTextValue());
		Intent intent = new Intent();
		intent.putExtra("ditchCable", ditchCable);
		mActivity.setResult(Activity.RESULT_OK, intent);
		ditchCable = null;
		mActivity.finish();
	}

	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == requestCode_hwg) {// 环网柜
				hwgEntityVo = (EcHwgEntityVo) data.getSerializableExtra("hwg");
				ditchCable.setHwgEntityVo(hwgEntityVo);
				// 修改内存的数据
				if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
					for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
						if (ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
							LayingDitchControll.ditchCableList.get(i).setHwgEntityVo(hwgEntityVo);
						}
					}
				}
			} else if (requestCode == requestCode_dzbq) {// 电子标签
				lineLabelEntityVo = (EcLineLabelEntityVo) data.getSerializableExtra("lable");
				if (lineLabelEntityVo != null) {
					lineLabelEntityVo.setLineNo(ditchCable.getLineId());
					lineLabelEntityVo.setDeviceType(ResourceEnum.XL.getValue() + "");
					lineLabelEntityVo.setDevObjId(ditchCable.getLineId());
				}
				ditchCable.setLineLabelEntityVo(lineLabelEntityVo);
				// 修改内存的数据
				if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
					for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
						if (ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
							LayingDitchControll.ditchCableList.get(i).setLineLabelEntityVo(lineLabelEntityVo);
						}
					}
				}
			} else if (requestCode == requestcode_dlfzx) {// 电缆分支箱
				dlfzxEntityVo = (EcDlfzxEntityVo) data.getSerializableExtra("dlfzx");
				ditchCable.setDlfzxEntityVo(dlfzxEntityVo);
				// 修改内存的数据
				if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
					for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
						if (ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
							LayingDitchControll.ditchCableList.get(i).setDlfzxEntityVo(dlfzxEntityVo);
						}
					}
				}
			} else if (requestCode == requestcode_dydlfzx) {// 低压电缆分支箱
				dydlfzxEntityVo = (EcDydlfzxEntityVo) data.getSerializableExtra("dydlfzx");
				ditchCable.setDydlfzxEntityVo(dydlfzxEntityVo);
				// 修改内存的数据
				if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
					for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
						if (ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
							LayingDitchControll.ditchCableList.get(i).setDydlfzxEntityVo(dydlfzxEntityVo);
						}
					}
				}

			} else if (requestCode == requestcode_zjjt) {// 中间接头
				middleJointEntityVo = (EcMiddleJointEntityVo) data.getSerializableExtra("zjjt");
				ditchCable.setMiddleJointEntityVo(middleJointEntityVo);
				// 修改内存的数据
				if (LayingDitchControll.ditchCableList != null && LayingDitchControll.ditchCableList.size() > 0) {
					for (int i = 0; i < LayingDitchControll.ditchCableList.size(); i++) {
						if (ditchCable.getLineId().equals(LayingDitchControll.ditchCableList.get(i).getLineId())) {
							LayingDitchControll.ditchCableList.get(i).setMiddleJointEntityVo(middleJointEntityVo);
						}
					}
				}
			}

		}
	};

};