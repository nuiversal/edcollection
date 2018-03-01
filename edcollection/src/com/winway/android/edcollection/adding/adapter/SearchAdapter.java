package com.winway.android.edcollection.adding.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.entity.NodeMarkerType;
import com.winway.android.edcollection.base.util.ResourceEnumUtil;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcNodeEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.util.StringUtils;

/**
 * 搜索结果显示列表适配器
 * 
 * @author mr-lao
 *
 */
public class SearchAdapter extends BaseAdapter {
	private Context context;
	private List<?> datas;

	public SearchAdapter(Context context, List<?> datas) {
		super();
		this.context = context;
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
			convertView = View.inflate(context, R.layout.list_item_search_result, null);
			holder.imgIV = (ImageView) convertView.findViewById(R.id.image);
			holder.textTV = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Object obj = datas.get(position);
		if (obj instanceof EcNodeEntity) {
			int type = ((EcNodeEntity) obj).getMarkerType();
			// 标识器
			if (type == NodeMarkerType.BSD.getValue()) {
				holder.imgIV.setVisibility(View.VISIBLE);
				holder.imgIV.setImageResource(R.drawable.bsd);
			} else if (type == NodeMarkerType.BSQ.getValue()) {
				holder.imgIV.setVisibility(View.VISIBLE);
				holder.imgIV.setImageResource(R.drawable.bsq);
			} else if(type == NodeMarkerType.XND.getValue()){
				holder.imgIV.setVisibility(View.VISIBLE);
				holder.imgIV.setImageResource(R.drawable.ljd);
			} else {
				holder.imgIV.setVisibility(View.VISIBLE);
				holder.imgIV.setImageResource(R.drawable.tower);
			}
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcNodeEntity) obj).getMarkerNo()));
		} else if (obj instanceof EcLineEntity) {
			// 线路
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.dl16);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcLineEntity) obj).getName()));
		} else if (obj instanceof EcWorkWellEntity) {
			// 工井
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.gongjing_n);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcWorkWellEntity) obj).getSbmc()));
		} else if (obj instanceof EcTowerEntity) {
			// 杆塔
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.ganta_n);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcTowerEntity) obj).getTowerNo()));
		} else if (obj instanceof EcSubstationEntity) {
			// 变电站
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.bdz);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcSubstationEntity) obj).getName()));
		} else if (obj instanceof EcTransformerEntity) {
			// 变压器
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.bianyaqi_n);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcTransformerEntity) obj).getSbmc()));
		} else if (obj instanceof EcDistributionRoomEntity) {
			// 配电房
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.pdpdz);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcDistributionRoomEntity) obj).getSbmc()));
		} else if (obj instanceof EcDypdxEntity) {
			// 低压配电箱
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.peidianxiang);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcDypdxEntity) obj).getSbmc()));
		} else if (obj instanceof EcXsbdzEntity) {
			// 箱式变电站
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.pdxb);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcXsbdzEntity) obj).getSbmc()));
		} else if (obj instanceof EcKgzEntity) {
			// 开关站
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.pdkgf);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcKgzEntity) obj).getSbmc()));
		} else if (obj instanceof EcLineLabelEntity) {
			// 电子标签
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.zhadai_n);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcLineLabelEntity) obj).getDevName()));
		} else if (obj instanceof EcMiddleJointEntity) {
			// 中间接头
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.jietou_n);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcMiddleJointEntity) obj).getSbmc()));
		} else if (obj instanceof EcHwgEntity) {
			// 环网柜
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.huanwanggui_n);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcHwgEntity) obj).getSbmc()));
		} else if (obj instanceof EcDlfzxEntity) {
			// 电缆分支箱
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.jiexianxiang_n);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcDlfzxEntity) obj).getSbmc()));
		} else if (obj instanceof EcDydlfzxEntity) {
			// 低压电缆分支箱
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.pdhwkgx);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcDydlfzxEntity) obj).getSbmc()));
		} else if (obj instanceof EcChannelEntity) {
			// 通道
			holder.imgIV.setVisibility(View.GONE);
			String channelType = ((EcChannelEntity) obj).getChannelType();
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcChannelEntity) obj).getName() + " 【 "
					+ ResourceEnumUtil.get(channelType).getName() + " 】"));
		}else if (obj instanceof EcWorkWellCoverEntity) {
			//井盖
			holder.imgIV.setVisibility(View.VISIBLE);
			holder.imgIV.setImageResource(R.drawable.cover);
			holder.textTV.setText(StringUtils.nullStrToEmpty(((EcWorkWellCoverEntity) obj).getSbmc()));
		}
		convertView.setBackgroundResource(R.drawable.list_item_selector);
		return convertView;
	}

	class ViewHolder {
		public ImageView imgIV;
		public TextView textTV;
	}

}
