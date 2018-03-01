package com.winway.android.edcollection.adding.adapter;

import java.util.ArrayList;

import org.greenrobot.eventbus.EventBus;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.customview.GJDrawView;
import com.winway.android.edcollection.adding.entity.channelplaning.JMEntity;
import com.winway.android.edcollection.base.adapter.MBaseAdapter;
import com.winway.android.util.DensityUtil;
import com.winway.android.util.DialogUtil;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 历史工井截面适配器
 * 
 * @author xs
 *
 */
public class JMsWindowAdapter extends MBaseAdapter<JMEntity> {
	private View parentView;
	private DialogUtil dialog;

	public JMsWindowAdapter(Context context, ArrayList<JMEntity> models, View parentView, DialogUtil dialog) {
		super(context, models);
		this.parentView = parentView;
		this.dialog = dialog;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.layout_dialog_jms_item, null);
			holder.llJMContainer = (LinearLayout) convertView.findViewById(R.id.ll_layout_dialog_jms_container);
			int width = DensityUtil.dip2px(context, 120);
			int height = DensityUtil.dip2px(context, 120);
			// float scale = ((float) width) / ((float) parentView.getWidth());
			holder.drawView = new GJDrawView(context, width, height, 0.25f);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParams.bottomMargin = DensityUtil.dip2px(context, 4);
			layoutParams.topMargin = DensityUtil.dip2px(context, 4);
			layoutParams.leftMargin = DensityUtil.dip2px(context, 4);
			layoutParams.rightMargin = DensityUtil.dip2px(context, 4);
			holder.drawView.setLayoutParams(layoutParams);
			holder.drawView.setTouchEditor(null);
			holder.llJMContainer.addView(holder.drawView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final JMEntity jmEntity = models.get(position);
		holder.drawView.setPoints(jmEntity.getPointList());
		holder.drawView.setLinesLocation(jmEntity.getZjList());
		holder.drawView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(jmEntity);
				dialog.dismissDialog();
			}
		});
		return convertView;
	}

	class ViewHolder {
		GJDrawView drawView;
		LinearLayout llJMContainer;
	}
}
