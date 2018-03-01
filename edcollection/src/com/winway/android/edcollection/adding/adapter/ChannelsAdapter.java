package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.bll.ChannelBll;
import com.winway.android.edcollection.adding.controll.ChannelControll;
import com.winway.android.edcollection.adding.util.LengthTextWatcher;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.edcollection.base.util.ResourceEnumUtil;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.util.DialogUtil;
import com.winway.android.util.ToastUtils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通道列表适配器
 * 
 * @author zgq
 *
 */
public class ChannelsAdapter extends MBaseAdapter<ChannelControll.ChannelDataEntity> {
	private ChannelBll channelBll;
	
	public ChannelsAdapter(Context context, ArrayList<ChannelControll.ChannelDataEntity> models) {
		super(context, models);
		channelBll = new ChannelBll(context, GlobalEntry.prjDbUrl);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.channel_list_item, null);
			viewHolder.tvChannelName = (TextView) convertView
					.findViewById(R.id.tv_channel_list_item_name);
			viewHolder.tvChannelSort = (TextView) convertView
					.findViewById(R.id.tv_channel_list_item_sort);
			viewHolder.tvChannelType = (TextView) convertView
					.findViewById(R.id.tv_channel_list_item_channelType);
			viewHolder.ivChannelDel = (ImageView) convertView
					.findViewById(R.id.iv_channel_list_item_del);
			viewHolder.tvOrderUpdate = (TextView) convertView
					.findViewById(R.id.tv_channel_list_item_order_update);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 赋值
		final EcChannelEntity ecChannelEntity = models.get(position).getData();
		viewHolder.tvChannelName.setText(ecChannelEntity.getName());
		viewHolder.tvChannelSort.setText(models.get(position).getChannelNode().getNIndex() + "");
		viewHolder.tvChannelType
				.setText(ResourceEnumUtil.get(ecChannelEntity.getChannelType()).getName());
		if (models.get(position).isEdit()) {
			// 编辑状态下删除键隐藏
			viewHolder.ivChannelDel.setVisibility(View.INVISIBLE);
		}else{
			viewHolder.ivChannelDel.setVisibility(View.VISIBLE);
		}
		// 删除
		viewHolder.ivChannelDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final DialogUtil dialogUtil = new DialogUtil((Activity) context);
				dialogUtil.showAlertDialog("确定删除通道吗？", new String[] { "确定", "取消" },
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (which == 0) {
									models.remove(position);
									ChannelsAdapter.this.notifyDataSetChanged();
								}
								dialogUtil.dismissDialog();
							}
						}, false);
			}
		});
		// 修改序号
		viewHolder.tvOrderUpdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final DialogUtil dialogUtil = new DialogUtil((Activity) context);
				View view = View.inflate(context, R.layout.dialog_channel_order_update, null);
				final TextView nameTV = (TextView) view.findViewById(R.id.update_channel_name);
				nameTV.setText(models.get(position).getData().getName() + "");
				final InputComponent orderET = (InputComponent) view
						.findViewById(R.id.update_channel_order);
				orderET.setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
				orderET.setEdtText("" + models.get(position).getChannelNode().getNIndex());
				orderET.getEditTextView().addTextChangedListener(new LengthTextWatcher(9, orderET.getEditTextView()));
				view.findViewById(R.id.update_channel_cancle)
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialogUtil.dismissDialog();
								dialogUtil.destroy();
							}
						});
				view.findViewById(R.id.update_channel_sure)
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if (TextUtils.isEmpty(orderET.getEdtTextValue())) {
									ToastUtils.show(context, "通道序号不能为空");
									return;
								}
								if (channelBll.checkChannelNoIndexRepeat(ecChannelEntity.getEcChannelId(), orderET.getEdtTextValue())) {
									ToastUtils.show(context, "通道序号" + orderET.getEdtTextValue() + "已经被使用过了，请换一个序号");
									return;
								}
								int parseInt = Integer.parseInt(orderET.getEdtTextValue());
								models.get(position).getChannelNode().setNIndex(parseInt);
								dialogUtil.dismissDialog();
								dialogUtil.destroy();
								// 更新数据
								notifyDataSetChanged();
							}
						});
				dialogUtil.showAlertDialog(view);
			}
		});
		return convertView;
	}

	class ViewHolder {
		public TextView tvChannelName;// 通道名称
		public TextView tvChannelSort;// 序号
		public TextView tvChannelType;// 类型
		public ImageView ivChannelDel;// 删除
		public TextView tvOrderUpdate;// 类型
	}

}
