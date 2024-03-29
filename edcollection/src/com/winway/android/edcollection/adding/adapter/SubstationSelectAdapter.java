package com.winway.android.edcollection.adding.adapter;

import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 变电站下拉适配器
 * 
 * @author zgq
 *
 */
public class SubstationSelectAdapter extends BaseAdapter {

	private Context mContext;
	private List<?> datas;

	public SubstationSelectAdapter(Context context, List<?> datas) {
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
		if (cls.equals(EcSubstationEntity.class)) {
			EcSubstationEntity ecSubstationEntity = (EcSubstationEntity) object;
			holder.tvValue.setText(ecSubstationEntity.getName());
		}

		return convertView;
	}

	private class ViewHolder {
		public TextView tvValue;
	}
}
