package com.winway.android.edcollection.task.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.base.bll.CommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.util.EcnodeLocationUtil;
import com.winway.android.edcollection.base.util.EcnodeLocationUtil.LongitudeLatitudeEntity;
import com.winway.android.edcollection.base.util.ResourceDeviceUtil;
import com.winway.android.edcollection.base.util.ResourceEnumUtil;
import com.winway.android.edcollection.datacenter.api.DataCenterImpl;
import com.winway.android.edcollection.map.MapPositionPicker;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EmTaskItemEntity;
import com.winway.android.edcollection.task.entity.TaskDetailItemData;
import com.winway.android.edcollection.task.entity.TaskStateTypeEnum;
import com.winway.android.util.ToastUtils;

/**
 * 任务详情列表适配器
 * 
 * @author zgq
 *
 */
public class TaskDetailListAdapter extends MBaseAdapter<TaskDetailItemData> {
	private DataCenterImpl dataCenter;
	private BaseDBUtil dbUtil;
	private CommonBll commonBll;

	public void setDataCenter(DataCenterImpl dataCenter, BaseDBUtil dbUtil, CommonBll commonBll) {
		this.dataCenter = dataCenter;
		this.dbUtil = dbUtil;
		this.commonBll = commonBll;
	}

	public TaskDetailListAdapter(Context context, ArrayList<TaskDetailItemData> models) {
		super(context, models);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.taskdetail_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvStateState = (TextView) convertView.findViewById(R.id.tv_taskdetail_list_taskState);
			viewHolder.tvTaskType = (TextView) convertView.findViewById(R.id.tv_taskdetail_list_taskType);
			viewHolder.tvDetailName = (TextView) convertView.findViewById(R.id.tv_taskdetail_list_taskName);
			viewHolder.ivTaskState = (ImageView) convertView.findViewById(R.id.iv_taskdetail_list_taskState);
			viewHolder.ivTaskMap = (ImageView) convertView.findViewById(R.id.iv_taskdetail_list_map);
			viewHolder.tvTaskDesc=(TextView)convertView.findViewById(R.id.iv_taskdetail_list_item_taskdesc);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final EmTaskItemEntity item = models.get(position).getItem();
		String name = "";
		if (null == models.get(position).getEntity()) {
			name = item.getObjId() + "";
		} else {
			name = ResourceDeviceUtil.getDeviceName(models.get(position).getEntity(), commonBll);
		}
		final String finalName = name;
		viewHolder.tvDetailName.setText(name);
		// 赋值
		viewHolder.ivTaskMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final EcNodeEntity nodeEntity = ResourceDeviceUtil.getEcNodeEntity(item.getObjId(),
						item.getDeviceType() + "", dbUtil, dataCenter);
				if (null == nodeEntity || TextUtils.isEmpty(nodeEntity.getGeom())) {
					ToastUtils.show(context, "无坐标信息");
					return;
				}
				boolean canModifyPosition = false;// 可以修改位置的
				String type = item.getDeviceType() + "";
				if (ResourceEnum.TLG.getValue().equals(type) || ResourceEnum.GD.getValue().equals(type)
						|| ResourceEnum.PG.getValue().equals(type) || ResourceEnum.QJ.getValue().equals(type)
						|| ResourceEnum.SD.getValue().equals(type) || ResourceEnum.ZM.getValue().equals(type)
						|| ResourceEnum.JKXL.getValue().equals(type)) {
					canModifyPosition = false;
					ToastUtils.show(context, "无坐标信息");
					return;
				} else {
					canModifyPosition = true;
				}
				LongitudeLatitudeEntity location = EcnodeLocationUtil.parseGeom(nodeEntity.getGeom());
				// TaskListControll.location.location = true;
				// TaskListControll.location.longitude = location.longitude;
				// TaskListControll.location.latitude = location.latitude;
				// @SuppressWarnings({ "rawtypes" })
				// DeviceEntity deviceEntity = new DeviceEntity();
				// deviceEntity.setNode(nodeEntity);
				// deviceEntity.setData(models.get(position).getEntity());
				// TaskListControll.location.device = deviceEntity;
				// 定位
				// ((Activity) context).finish();
				//
				// 地图选点
				MapPositionPicker.OnPickListener picker = new MapPositionPicker.OnPickListener() {
					@Override
					public void onPicked(double x, double y) {
						// 保存新坐标
						EcNodeEntity nEntity = nodeEntity;
						nEntity.setGeom(EcnodeLocationUtil.createGeom(x, y));
						try {
							dbUtil.update(nEntity);
							ToastUtils.show(context, "位置更新成功");
						} catch (DbException e) {
							e.printStackTrace();
							ToastUtils.show(context, "位置更新失败");
						}
						if (onPickListener != null) {
							onPickListener.onPick(position);
						}
					}
				};
				MapPositionPicker.getInstance().setOnPickListener(canModifyPosition ? picker : null);
				MapPositionPicker.getInstance().open(context, finalName, location.longitude, location.latitude, 16);
			}
		});
		if (item.getStatus() != null) {
			if (item.getStatus().equals(TaskStateTypeEnum.wcj.getState())) {
				viewHolder.tvStateState.setText(TaskStateTypeEnum.wcj.getValue());
			} else if (item.getStatus().equals(TaskStateTypeEnum.ycj.getState())) {
				viewHolder.tvStateState.setText(TaskStateTypeEnum.ycj.getValue());
			} else if (item.getStatus().equals(TaskStateTypeEnum.ytg.getState())) {
				viewHolder.tvStateState.setText(TaskStateTypeEnum.ytg.getValue());
			}else if (item.getStatus().equals(TaskStateTypeEnum.wtg.getState())) {
				viewHolder.tvStateState.setText(TaskStateTypeEnum.wtg.getValue());
			}
		}
		ResourceEnum resourceEnum = ResourceEnumUtil.get(item.getDeviceType());
		if (null != resourceEnum) {
			viewHolder.tvTaskType.setText("" + resourceEnum.getName());
		} else {
			viewHolder.tvTaskType.setText("未知设备");
		}
		if (!TextUtils.isEmpty(item.getRemark())) {
			viewHolder.tvTaskDesc.setVisibility(View.VISIBLE);
			viewHolder.tvTaskDesc.setText("注："+item.getRemark());
		}else {
			viewHolder.tvTaskDesc.setVisibility(View.GONE);
		}
		convertView.setBackgroundResource(R.drawable.list_item_selector);
		return convertView;
	}

	class ViewHolder {
		TextView tvDetailName;
		TextView tvTaskType;
		ImageView ivTaskState;
		ImageView ivTaskMap;
		TextView tvStateState;
		TextView tvTaskDesc;
	}

	private OnPickListener onPickListener;

	public void setOnPickListener(OnPickListener onPickListener) {
		this.onPickListener = onPickListener;
	}

	public interface OnPickListener {
		void onPick(int position);
	}
}
