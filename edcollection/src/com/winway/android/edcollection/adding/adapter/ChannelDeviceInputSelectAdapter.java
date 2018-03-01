package com.winway.android.edcollection.adding.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;

/**
 * 通道设施下拉适配器
 * 
 * @author zgq
 *
 */
public class ChannelDeviceInputSelectAdapter extends BaseAdapter {

	private Context mContext;
	private List<?> datas;

	public ChannelDeviceInputSelectAdapter(Context context, List<?> datas) {
		this.mContext = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.inse_item_layout, null);
			holder.tvValue = (TextView) convertView.findViewById(R.id.list_val);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Object object = datas.get(position);
		Class<?> cls = object.getClass();
		if (cls.equals(EcChannelDlgdEntity.class)) {// 电缆管道
			EcChannelDlgdEntity ecChannelDlgdEntity = (EcChannelDlgdEntity) object;
			holder.tvValue.setText(ecChannelDlgdEntity.getGdbh());
		} else if (cls.equals(EcChannelDlgEntity.class)) {// 电缆沟

		} else if (cls.equals(EcChannelDlqEntity.class)) {// 电缆桥

		} else if (cls.equals(EcChannelDlsdEntity.class)) {// 电缆隧道

		} else if (cls.equals(EcChannelDlzmEntity.class)) {// 电缆直埋

		} else if (cls.equals(EcChannelDgEntity.class)) {// 顶管
			
		} else if (cls.equals(EcChannelJkEntity.class)) {// 架空

		}

		return convertView;
	}

	private class ViewHolder {
		public TextView tvValue;
	}
}
