package com.winway.android.edcollection.adding.controll;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

import com.lidroid.xutils.db.table.TableUtils;
import com.lidroid.xutils.exception.DbException;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.SelectAdapter;
import com.winway.android.edcollection.adding.util.ListViewScrollerLoader;
import com.winway.android.edcollection.adding.util.ListViewScrollerLoader.LoadListener;
import com.winway.android.edcollection.adding.viewholder.ChannelSelectViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.util.BaseDBUtil;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.util.ListUtil;
import com.winway.android.util.LogUtil;

public class ChannelSelectController extends BaseControll<ChannelSelectViewHolder> {
	private List<EcChannelEntity> channelList = null;
	private SelectAdapter adapter;
	private ListViewScrollerLoader loader = null;
	private BaseDBUtil dbUtil;
	private int currentPage = 1;
	private int pageSize = 30;

	public static final String RESULT_DATA_KEY = "channelid";
	private EcChannelEntity queryEntity = new EcChannelEntity();

	@Override
	public void init() {
		initDatas();
		initEvent();
	}

	void initDatas() {
		Intent intent = getIntent();
		String channelType = intent.getStringExtra("channelType");
		String channelTypeValue = null;
		if (!TextUtils.isEmpty(channelType)) {
			channelTypeValue = ResourceEnum.getValueByName(channelType);
		}
		try {
			dbUtil = new BaseDBUtil(mActivity, new File(GlobalEntry.prjDbUrl));
			queryEntity.setChannelType(channelTypeValue);
			channelList = dbUtil.excuteQuery(EcChannelEntity.class, queryEntity, "like", "or", currentPage, pageSize);
			//String sql = "select * from "+TableUtils.getTableName(EcChannelEntity.class)+"  where CHANNEL_TYPE like '%"+channelTypeValue+"%'  order by UPDATE_TIME desc limit "+(currentPage-1)*currentPage+","+pageSize+"";
			//channelList = dbUtil.excuteQuery(EcChannelEntity.class,sql);
		} catch (DbException e) {
			e.printStackTrace();
		}
		if (null == channelList) {
			channelList = new ArrayList<EcChannelEntity>();
		}
		adapter = new SelectAdapter(channelList, mActivity);
		viewHolder.getChannelLV().setAdapter(adapter);
		loader = new ListViewScrollerLoader(viewHolder.getChannelLV(), viewHolder.getMoreTV(), new LoadListener() {
			@Override
			public boolean loadSyn() {
				// 采用在主线程的方式加载数据
				excuteQuery();
				return false;
			}
		});
		mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	/**
	 * 进行查询
	 */
	void excuteQuery() {
		try {
			currentPage++;
			List<EcChannelEntity> query = dbUtil.excuteQuery(EcChannelEntity.class, queryEntity, "like", "or",
					currentPage, pageSize);
			if (query == null || query.isEmpty()) {
				if (null != loader) {
					loader.needLoad = false;
				}
				return;
			} else {
				if (null != loader) {
					loader.needLoad = true;
				}
			}
			ListUtil.copyList(channelList, query);
		} catch (DbException e) {
			e.printStackTrace();
			currentPage--;
		}
	}

	void initEvent() {
		viewHolder.getHcSubHead().getReturnView().setOnClickListener(ocls);
		viewHolder.getHcSubHead().getOkView().setOnClickListener(ocls);
		viewHolder.getQueryBT().setOnClickListener(ocls);
	}

	private OnClickListener ocls = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok:// 确定
			{
				if (adapter.getCheckPosition() < 0) {
					return;
				}
				String channelID = channelList.get(adapter.getCheckPosition()).getEcChannelId();
				Intent data = new Intent();
				data.putExtra(RESULT_DATA_KEY, channelID);
				mActivity.setResult(Activity.RESULT_OK, data);
				mActivity.finish();
				break;
			}
			case R.id.btn_select_device_query:// 查寻
			{
				// 重置
				channelList.clear();
				currentPage = 0;
				if (TextUtils.isEmpty(viewHolder.getChannelNameET().getText().toString())) {
					queryEntity.setName(null);
				} else {
					queryEntity.setName(viewHolder.getChannelNameET().getText().toString());
				}
				excuteQuery();
				adapter.notifyDataSetChanged();
			}
			default:
				break;
			}
		}
	};
}
