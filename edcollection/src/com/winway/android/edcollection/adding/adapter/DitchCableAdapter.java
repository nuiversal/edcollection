package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;
import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.LineNameActivity;
import com.winway.android.edcollection.adding.bll.SameGrooveInformationBll;
import com.winway.android.edcollection.adding.controll.LayingDitchControll;
import com.winway.android.edcollection.adding.entity.DitchCable;
import com.winway.android.edcollection.adding.entity.LineDeviceType;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.project.entity.EcLineDeviceEntity;
import com.winway.android.edcollection.project.entity.EcLineNodesEntity;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.project.entity.OfflineAttach;
import com.winway.android.edcollection.project.vo.EcDlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcDydlfzxEntityVo;
import com.winway.android.edcollection.project.vo.EcHwgEntityVo;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.edcollection.project.vo.EcMiddleJointEntityVo;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.util.FileUtil;
import com.winway.android.util.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * 同沟电缆适配器
 * 
 * @author zgq
 *
 */
public class DitchCableAdapter extends MBaseAdapter<DitchCable> {
	private SameGrooveInformationBll sameGrooveInformationBll;
	private CommonBll commonBll;
	private InputComponent icLoopNum;
	public DitchCableAdapter(Context context, ArrayList<DitchCable> models,InputComponent icLoopNum) {
		super(context, models);
		String projectBDUrl = FileUtil.AppRootPath + "/db/project/" + GlobalEntry.currentProject.getPrjNo() + ".db";
		sameGrooveInformationBll = new SameGrooveInformationBll(context, projectBDUrl);
		commonBll = new CommonBll(context, projectBDUrl);
		this.icLoopNum=icLoopNum;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.laying_ditch_item, parent, false);
			holder.tv_lineName = (TextView) convertView.findViewById(R.id.tv_ditch_item_lineName);
			holder.tv_lineNodeSort = (TextView) convertView.findViewById(R.id.tv_ditch_item_lineNo);
			holder.tv_layType = (TextView) convertView.findViewById(R.id.tv_ditch_item_layTyape);
			holder.item_left = (RelativeLayout) convertView.findViewById(R.id.rl_left);
			holder.iv_dele = (ImageView) convertView.findViewById(R.id.iv_ditch_item_dele);
			convertView.setTag(holder);
		} else {// 有直接获得ViewHolder
			holder = (ViewHolder) convertView.getTag();
		}
		// 赋值
		LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		holder.item_left.setLayoutParams(lp1);
		DitchCable entity = this.models.get(position);
		holder.tv_lineName.setText(entity.getLineName());
		holder.tv_lineNodeSort.setText(entity.getLineNodeSort() + "");
		holder.tv_layType.setText(entity.getLayType());
		// 绑定监听器
		holder.iv_dele.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DitchCable ditchCable = models.get(position);
				if (GlobalEntry.editNode) {
					if (ditchCable.isEditAdd()) {// 编辑状态下的添加
						models.remove(position);
						notifyDataSetChanged();
					} else {
						if (models.size() <= 1) {
							ToastUtil.showShort(context, "至少得包含一条同沟信息");
							return;
						}
						DitchCable ditchCable2 = models.get(position);
						deleteData(ditchCable2);
						models.remove(position);
						notifyDataSetChanged();
						icLoopNum.setEdtText(models.size()+"");
						ToastUtil.show(context, "删除成功", 200);
					}
					return;
				}
				models.remove(position);
				notifyDataSetChanged();
				// new
				// AlertDialog.Builder(context).setTitle("系统提示").setMessage("确定删除？")
				// .setPositiveButton("确定", new
				// DialogInterface.OnClickListener() {
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// DitchCable ditchCable = models.get(position);
				// models.remove(position);
				// if (GlobalEntry.editNode) {
				// // 删除数据
				// // deleteData(ditchCable);
				// }
				// notifyDataSetChanged();
				// }
				//
				// }).setNegativeButton("返回", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				//
				// }
				// }).show();
			}
		});
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(context, LineNameActivity.class);
				bundle.putSerializable("DitchCable", models.get(position));
				intent.putExtras(bundle);
				((Activity) context).startActivityForResult(intent, LayingDitchControll.requestCode_layingditch);
			}

		});
		return convertView;
	}

	/**
	 * 删除数据
	 * 
	 * @param ditchCable
	 */
	private void deleteData(DitchCable ditchCable) {
		List<OfflineAttach> offlineAttach;
		EcLineDeviceEntity lineDeviceEntity;
		// 删除电缆电子标签
		EcLineLabelEntityVo lineLabelEntity = ditchCable.getLineLabelEntityVo();
		if (lineLabelEntity != null) {
			// 删除数据
			sameGrooveInformationBll.deleteById(EcLineLabelEntityVo.class, lineLabelEntity.getObjId());
			// 修改日志表数据
			commonBll.handleDataLog(lineLabelEntity.getObjId(), TableNameEnum.DLDZBQ.getTableName(),
					DataLogOperatorType.delete, WhetherEnum.NO, "删除电缆电子标签", false);
			offlineAttach = lineLabelEntity.getAttr();
			// 删除附件
			if (offlineAttach != null && offlineAttach.size() > 0) {
				for (OfflineAttach comUpload : offlineAttach) {
					FileUtil.deleteFileByFilePath(comUpload.getFilePath());
					commonBll.deleteById(EmCDataLogEntity.class, comUpload.getComUploadId());
				}
			}
			// 删除线路附属设备
			lineDeviceEntity = sameGrooveInformationBll.getDeviceByObjId(lineLabelEntity.getObjId());
			if (lineDeviceEntity != null) {
				sameGrooveInformationBll.deleteById(EcLineDeviceEntity.class, lineDeviceEntity.getEcLineDeviceId());
				commonBll.handleDataLog(lineDeviceEntity.getEcLineDeviceId(), TableNameEnum.XLFSSB.getTableName(),
						DataLogOperatorType.delete, WhetherEnum.NO, "删除线路附属设备:" + LineDeviceType.DZBQ.getName(), false);
			}
		}

		// 删除电缆中间接头
		EcMiddleJointEntityVo middleJointEntity = ditchCable.getMiddleJointEntityVo();
		if (middleJointEntity != null) {
			// 删除数据
			sameGrooveInformationBll.deleteById(EcMiddleJointEntityVo.class, middleJointEntity.getObjId());
			// 修改日志表数据
			commonBll.handleDataLog(middleJointEntity.getObjId(), TableNameEnum.DLZJJT.getTableName(),
					DataLogOperatorType.delete, WhetherEnum.NO, "删除电缆中间接头", false);
			offlineAttach = middleJointEntity.getAttr();
			// 删除附件
			if (offlineAttach != null && offlineAttach.size() > 0) {
				for (OfflineAttach comUpload : offlineAttach) {
					FileUtil.deleteFileByFilePath(comUpload.getFilePath());
					commonBll.deleteById(EmCDataLogEntity.class, comUpload.getComUploadId());
				}
			}
			// 删除线路附属设备
			lineDeviceEntity = sameGrooveInformationBll.getDeviceByObjId(middleJointEntity.getObjId());
			if (lineDeviceEntity != null) {
				sameGrooveInformationBll.deleteById(EcLineDeviceEntity.class, lineDeviceEntity.getEcLineDeviceId());
				commonBll.handleDataLog(lineDeviceEntity.getEcLineDeviceId(), TableNameEnum.XLFSSB.getTableName(),
						DataLogOperatorType.delete, WhetherEnum.NO, "删除线路附属设备:" + LineDeviceType.ZJJT.getName(), false);
			}
		}

		// 删除环网柜
		EcHwgEntityVo hwgEntity = ditchCable.getHwgEntityVo();
		if (hwgEntity != null) {
			// 删除数据
			sameGrooveInformationBll.deleteById(EcHwgEntityVo.class, hwgEntity.getObjId());
			// 修改日志表数据
			commonBll.handleDataLog(hwgEntity.getObjId(), TableNameEnum.HWG.getTableName(), DataLogOperatorType.delete,
					WhetherEnum.NO, "删除电缆中间接头", false);
			offlineAttach = hwgEntity.getAttr();
			// 删除附件
			if (offlineAttach != null && offlineAttach.size() > 0) {
				for (OfflineAttach comUpload : offlineAttach) {
					FileUtil.deleteFileByFilePath(comUpload.getFilePath());
					commonBll.deleteById(EmCDataLogEntity.class, comUpload.getComUploadId());
				}
			}
			// 删除线路附属设备
			lineDeviceEntity = sameGrooveInformationBll.getDeviceByObjId(hwgEntity.getObjId());
			if (lineDeviceEntity != null) {
				sameGrooveInformationBll.deleteById(EcLineDeviceEntity.class, lineDeviceEntity.getEcLineDeviceId());
				commonBll.handleDataLog(lineDeviceEntity.getEcLineDeviceId(), TableNameEnum.XLFSSB.getTableName(),
						DataLogOperatorType.delete, WhetherEnum.NO, "删除线路附属设备:" + LineDeviceType.ZJJT.getName(), false);
			}
		}
		// 删除电缆分支箱
		EcDlfzxEntityVo dlfzxEntity = ditchCable.getDlfzxEntityVo();
		if (dlfzxEntity != null) {
			// 删除数据
			sameGrooveInformationBll.deleteById(EcDlfzxEntityVo.class, dlfzxEntity.getObjId());
			// 修改日志表数据
			commonBll.handleDataLog(dlfzxEntity.getObjId(), TableNameEnum.DLFZX.getTableName(),
					DataLogOperatorType.delete, WhetherEnum.NO, "删除电缆中间接头", false);
			offlineAttach = dlfzxEntity.getAttr();
			// 删除附件
			if (offlineAttach != null && offlineAttach.size() > 0) {
				for (OfflineAttach comUpload : offlineAttach) {
					FileUtil.deleteFileByFilePath(comUpload.getFilePath());
					commonBll.deleteById(EmCDataLogEntity.class, comUpload.getComUploadId());
				}
			}
			// 删除线路附属设备
			lineDeviceEntity = sameGrooveInformationBll.getDeviceByObjId(dlfzxEntity.getObjId());
			if (lineDeviceEntity != null) {
				sameGrooveInformationBll.deleteById(EcLineDeviceEntity.class, lineDeviceEntity.getEcLineDeviceId());
				commonBll.handleDataLog(lineDeviceEntity.getEcLineDeviceId(), TableNameEnum.XLFSSB.getTableName(),
						DataLogOperatorType.delete, WhetherEnum.NO, "删除线路附属设备:" + LineDeviceType.ZJJT.getName(), false);
			}
		}

		// 删除低压电缆分支箱
		EcDydlfzxEntityVo dydlfzxEntity = ditchCable.getDydlfzxEntityVo();
		if (dydlfzxEntity != null) {
			// 删除数据
			sameGrooveInformationBll.deleteById(EcDydlfzxEntityVo.class, dydlfzxEntity.getObjId());
			// 修改日志表数据
			commonBll.handleDataLog(dydlfzxEntity.getObjId(), TableNameEnum.HWG.getTableName(),
					DataLogOperatorType.delete, WhetherEnum.NO, "删除电缆中间接头", false);
			offlineAttach = dydlfzxEntity.getAttr();
			// 删除附件
			if (offlineAttach != null && offlineAttach.size() > 0) {
				for (OfflineAttach comUpload : offlineAttach) {
					FileUtil.deleteFileByFilePath(comUpload.getFilePath());
					commonBll.deleteById(EmCDataLogEntity.class, comUpload.getComUploadId());
				}
			}
			// 删除线路附属设备
			lineDeviceEntity = sameGrooveInformationBll.getDeviceByObjId(dydlfzxEntity.getObjId());
			if (lineDeviceEntity != null) {
				sameGrooveInformationBll.deleteById(EcLineDeviceEntity.class, lineDeviceEntity.getEcLineDeviceId());
				commonBll.handleDataLog(lineDeviceEntity.getEcLineDeviceId(), TableNameEnum.XLFSSB.getTableName(),
						DataLogOperatorType.delete, WhetherEnum.NO, "删除线路附属设备:" + LineDeviceType.ZJJT.getName(), false);
			}
		}
		// 删除线路节点关联表
		sameGrooveInformationBll.deleteById(EcLineNodesEntity.class, ditchCable.getLineNodesId());
		commonBll.handleDataLog(ditchCable.getLineNodesId(), TableNameEnum.XLJDGL.getTableName(),
				DataLogOperatorType.delete, WhetherEnum.NO, "删除线路节点:" + ditchCable.getLineNodesId(), false);
	}

	class ViewHolder {
		RelativeLayout item_left;
		TextView tv_lineName;
		TextView tv_lineNodeSort;
		TextView tv_layType;
		ImageView iv_dele;
	}
}
