package com.winway.android.edcollection.colist.viewholder;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.BaseViewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 已采ui初始化
 * 
 * @author zgq
 *
 */
public class CollectedFragmentViewHolder extends BaseViewHolder {
	@ViewInject(R.id.tv_sum)
	private TextView tvSum;
	
	@ViewInject(R.id.include_fragment_collected_classify)
	private View includeCollectedClassify;
	// 采集时间
	@ViewInject(R.id.ll_collected_classify_time)
	private LinearLayout llCollectedTime;
	// 路径点类型
	@ViewInject(R.id.ll_collected_classify_pointType)
	private LinearLayout llCollectedPointType;
	// 线路
	@ViewInject(R.id.ll_collected_classify_line)
	private LinearLayout llCollectedLine;

	// 已采集信息列表
	@ViewInject(R.id.lv_fragment_collected_viewpage)
	private ListView lvCollectedViewPage;

	@ViewInject(R.id.tv_collected_classify_time)
	private TextView tv_collected_classify_time;
	
	@ViewInject(R.id.tv_collected_classify_pointType)
	private TextView tv_collected_classify_pointType;
	
	@ViewInject(R.id.tv_collected_classify_line)
	private TextView tv_collected_classify_line;
	
	public TextView getTv_collected_classify_pointType() {
		return tv_collected_classify_pointType;
	}

	public void setTv_collected_classify_pointType(TextView tv_collected_classify_pointType) {
		this.tv_collected_classify_pointType = tv_collected_classify_pointType;
	}

	public TextView getTv_collected_classify_line() {
		return tv_collected_classify_line;
	}

	public void setTv_collected_classify_line(TextView tv_collected_classify_line) {
		this.tv_collected_classify_line = tv_collected_classify_line;
	}

	public TextView getTv_collected_classify_time() {
		return tv_collected_classify_time;
	}

	public void setTv_collected_classify_time(TextView tv_collected_classify_time) {
		this.tv_collected_classify_time = tv_collected_classify_time;
	}

	public ListView getLvCollectedViewPage() {
		return lvCollectedViewPage;
	}

	public void setLvCollectedViewPage(ListView lvCollectedViewPage) {
		this.lvCollectedViewPage = lvCollectedViewPage;
	}

	public View getIncludeCollectedClassify() {
		return includeCollectedClassify;
	}

	public void setIncludeCollectedClassify(View includeCollectedClassify) {
		this.includeCollectedClassify = includeCollectedClassify;
	}

	public LinearLayout getLlCollectedTime() {
		return llCollectedTime;
	}

	public void setLlCollectedTime(LinearLayout llCollectedTime) {
		this.llCollectedTime = llCollectedTime;
	}

	public LinearLayout getLlCollectedPointType() {
		return llCollectedPointType;
	}

	public void setLlCollectedPointType(LinearLayout llCollectedPointType) {
		this.llCollectedPointType = llCollectedPointType;
	}

	public LinearLayout getLlCollectedLine() {
		return llCollectedLine;
	}

	public void setLlCollectedLine(LinearLayout llCollectedLine) {
		this.llCollectedLine = llCollectedLine;
	}

	public TextView getTvSum() {
		return tvSum;
	}

	public void setTvSum(TextView tvSum) {
		this.tvSum = tvSum;
	}

}
