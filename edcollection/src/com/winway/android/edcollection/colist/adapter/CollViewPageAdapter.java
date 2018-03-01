package com.winway.android.edcollection.colist.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winway.android.basicbusiness.entity.DataLogOperatorType;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.MarkerCollectActivity;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.adding.util.TreeRefleshUtil;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.colist.entity.CollectedObject;
import com.winway.android.edcollection.main.controll.MainControll;
import com.winway.android.edcollection.project.bll.NodeBll;
import com.winway.android.edcollection.project.entity.EmCDataLogEntity;
import com.winway.android.edcollection.setting.entity.OperateType;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DateUtils;
import com.winway.android.util.ToastUtil;

/**
 * 已采集列表
 * 
 * @author lyh
 * @version 创建时间：2016年12月9日 下午2:44:39
 * 
 */
public class CollViewPageAdapter extends MBaseAdapter<CollectedObject> {
	private int selectedPosition = -1;
	private NodeBll nodeBll;

	public CollViewPageAdapter(Context context, ArrayList<CollectedObject> models, NodeBll nodeBll) {
		super(context, models);
		this.nodeBll = nodeBll;
	}

	public int getSelectedPosition() {
		return this.selectedPosition;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	@SuppressLint("CutPasteId")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.collected_item, null);
			viewHolder.ivBsqIcon = (ImageView) convertView.findViewById(R.id.iv_collected_item_bsq);
			viewHolder.ivisUploadIcon = (ImageView) convertView.findViewById(R.id.iv_collected_item_isupload);
			viewHolder.tvWayPathtype = (TextView) convertView.findViewById(R.id.tv_collected_item_bsq);
			viewHolder.tvBsqId = (TextView) convertView.findViewById(R.id.tv_collected_item_bsq_id);
//			viewHolder.tvlayingMethod = (TextView) convertView.findViewById(R.id.tv_collected_item_laying_method);
			viewHolder.tvOrderNo = (TextView) convertView.findViewById(R.id.tv_collected_item_order_no);
			viewHolder.tvOrderNoTit = (TextView) convertView.findViewById(R.id.tv_collected_item_order_no_title);
			viewHolder.tvUpdateTime = (TextView) convertView.findViewById(R.id.tv_collected_item_update_date);
			viewHolder.tvisUpload = (TextView) convertView.findViewById(R.id.tv_collected_item_upload);
//			viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_collected_item_delete);
			viewHolder.llDescribe = (LinearLayout) convertView.findViewById(R.id.ll_collected_item_describe);
			viewHolder.llDel = (LinearLayout) convertView.findViewById(R.id.ll_collected_item_delete);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final CollectedObject entity = models.get(position);
		if (NodeMarkerType.BSQ.getName().equals(entity.getObjType())) {// 标识球
			viewHolder.ivBsqIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.bsq));
			viewHolder.tvWayPathtype.setText(NodeMarkerType.BSQ.getName());
		} else if (NodeMarkerType.BSD.getName().equals(entity.getObjType())) {// 标识钉
			viewHolder.ivBsqIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.bsd));
			viewHolder.tvWayPathtype.setText(NodeMarkerType.BSD.getName());
		} else if (NodeMarkerType.XND.getName().equals(entity.getObjType())) {// 路径点
			viewHolder.ivBsqIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ljd));
			viewHolder.tvWayPathtype.setText(NodeMarkerType.XND.getName());
		} else if (NodeMarkerType.GT.getName().equals(entity.getObjType())) {// 杆塔
			viewHolder.ivBsqIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.tower));
			viewHolder.tvWayPathtype.setText(NodeMarkerType.GT.getName());
		} else if (NodeMarkerType.AJH.getName().equals(entity.getObjType())) {// 安健环
			viewHolder.ivBsqIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ajh));
			viewHolder.tvWayPathtype.setText(NodeMarkerType.AJH.getName());
		}
		viewHolder.tvBsqId.setText("【" + entity.getNo() + "】");
//		viewHolder.tvlayingMethod.setText(entity.getLayType());
		if (entity.getOrderNo().equals("")) {
			//隐藏序号
			viewHolder.tvOrderNo.setVisibility(View.GONE);
			viewHolder.tvOrderNoTit.setVisibility(View.GONE);
		}else {
			//显示序号
			viewHolder.tvOrderNo.setVisibility(View.VISIBLE);
			viewHolder.tvOrderNoTit.setVisibility(View.VISIBLE);
			viewHolder.tvOrderNo.setText(entity.getOrderNo());
		}
		viewHolder.tvUpdateTime.setText(DateUtils.date2Str(entity.getUpdateTime(), DateUtils.date_sdf_wz_hm));
		if (WhetherEnum.NO.getValue().equals(entity.getIsUpload())) {
			if (DataLogOperatorType.Add.getValue().equals(entity.getOperatorType())) {
				viewHolder.tvisUpload.setText("未上传");
			} else if (DataLogOperatorType.update.getValue().equals(entity.getOperatorType())) {
				viewHolder.tvisUpload.setText("编辑后未上传");
			}
			viewHolder.ivisUploadIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.upload));
			convertView.setOnClickListener(new OnClickListener() {// 未上传的点击进入编辑界面
				@Override
				public void onClick(View v) {
					openCurrentNode(entity);
				}
			});
			viewHolder.llDel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showDeleteDialog(position);
				}
			});
			viewHolder.llDescribe.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					openCurrentNode(entity);
				}
			});
		} else if (WhetherEnum.YES.getValue().equals(entity.getIsUpload())) {
			viewHolder.ivisUploadIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.uploaded));
			if (DataLogOperatorType.Add.getValue().equals(entity.getOperatorType())) {
				viewHolder.tvisUpload.setText("已上传");
			} else if (DataLogOperatorType.update.getValue().equals(entity.getOperatorType())) {
				viewHolder.tvisUpload.setText("编辑后已上传");
			}
			convertView.setOnClickListener(null);
//			viewHolder.ivDelete.setOnClickListener(null);
			viewHolder.llDel.setOnClickListener(null);
			viewHolder.llDescribe.setOnClickListener(null);
		}
		viewHolder.ivisUploadIcon.setVisibility(View.VISIBLE);
		return convertView;
	}

	/**
	 * 打开当前选择的节点详情
	 * 
	 * @param entity
	 */
	private void openCurrentNode(CollectedObject entity) {
		GlobalEntry.currentEditNode = nodeBll.getNodeEntityByOid(entity.getOid());
		GlobalEntry.editNode = true;
		Intent intent = new Intent(context, MarkerCollectActivity.class);
		intent.putExtra("EcNodeEntity", GlobalEntry.currentEditNode);
		AndroidBasicComponentUtils.launchActivityForResult((Activity) context, intent, MainControll.flag_marker_edit);
	}

	class ViewHolder {
		// 标识球还是标识钉icon
		public ImageView ivBsqIcon;
		// 是否上传
		public ImageView ivisUploadIcon;
		// 标识球还是标识钉文字
		public TextView tvWayPathtype;
		// 标识球或标识钉id
		public TextView tvBsqId;
		// 敷设方式
//		public TextView tvlayingMethod;
		//序号
		public TextView tvOrderNo;
		//序号标题
		public TextView tvOrderNoTit;
		// 更新时间
		public TextView tvUpdateTime;
		// 侍上传还是已上传
		public TextView tvisUpload;
		// 删除
//		public ImageView ivDelete;
		// 描述滚动条
		public LinearLayout llDescribe;
		//删除控件布局
		public LinearLayout llDel;
	}

	private void showDeleteDialog(final int position) {
		final AlertDialog.Builder deletelDialog = new AlertDialog.Builder(context, R.style.dialog_app_style);
		deletelDialog.setTitle("温馨提示");
		deletelDialog.setMessage("确认删除吗?");
		deletelDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CollectedObject collectedObject = models.get(position);
				String oid = collectedObject.getOid();
				EmCDataLogEntity dataLogEntity = nodeBll.getDataLogEntity(oid);
				Integer optType = dataLogEntity.getOptType();
				if(optType==DataLogOperatorType.Add.getValue()){//新增 本地删除，不保存日志
					nodeBll.delMarkerIds(oid);
					nodeBll.deleteNodeAndDevice(oid,true);
					models.remove(position);
				}else if (optType==DataLogOperatorType.update.getValue()) {//更新 本地不删除，日志保存
					nodeBll.delMarkerIds(oid);
					nodeBll.deleteNodeAndDevice(oid, false);
					models.remove(position);
				}
				// 刷新台帐树
				TreeRefleshUtil util = TreeRefleshUtil.getSingleton();
				if (util != null) {
					util.refleshWholeTree();
				}
				notifyDataSetChanged();
				ToastUtil.show(context, "删除成功", 200);
			}
		});
		deletelDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		// 显示
		deletelDialog.show();
	}
}
