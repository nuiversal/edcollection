package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.SelectDeviceAdapter;
import com.winway.android.edcollection.adding.bll.DeviceLinkBll;
import com.winway.android.edcollection.adding.viewholder.SelectDeviceViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcDistributionRoomEntity;
import com.winway.android.edcollection.project.entity.EcDlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDydlfzxEntity;
import com.winway.android.edcollection.project.entity.EcDypdxEntity;
import com.winway.android.edcollection.project.entity.EcHwgEntity;
import com.winway.android.edcollection.project.entity.EcKgzEntity;
import com.winway.android.edcollection.project.entity.EcLineLabelEntity;
import com.winway.android.edcollection.project.entity.EcMiddleJointEntity;
import com.winway.android.edcollection.project.entity.EcSubstationEntity;
import com.winway.android.edcollection.project.entity.EcTowerEntity;
import com.winway.android.edcollection.project.entity.EcTransformerEntity;
import com.winway.android.edcollection.project.entity.EcWorkWellEntity;
import com.winway.android.edcollection.project.entity.EcXsbdzEntity;
import com.winway.android.ewidgets.page.OnPageChangeListener;
import com.winway.android.util.ToastUtil;

/**
 * 关联设备
 * 
 * @author zgq
 *
 */
public class SelectDeviceControll extends BaseControll<SelectDeviceViewHolder> {

	private DeviceLinkBll deviceLinkBll;
	private int pageIndex = 1;// 从1开始
	private int pageSize = 10;
	private SelectDeviceAdapter selectDeviceAdapter;
	private List<Object> datas;// 查询的结果数据
	@Override
	public void init() {
		// TODO Auto-generated method stub
		deviceLinkBll = new DeviceLinkBll(mActivity, GlobalEntry.prjDbUrl);
		initDatas();
		initEvents();
		initSetting();

	}

	private void initSetting() {
		// TODO Auto-generated method stub

	}

	private void initEvents() {
		// TODO Auto-generated method stub
		viewHolder.getHcHead().getReturnView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mActivity.finish();
			}
		});
		viewHolder.getHcHead().getOkView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				saveData();

			}
		});
		viewHolder.getBtnQuery().setOnClickListener(orcl);

	}

	private OnClickListener orcl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_select_device_query: {// 查询
				query();
				break;
			}

			default:
				break;
			}
		}
	};

	private void saveData() {
		// TODO Auto-generated method stub
		String tbName = getTbName();
		Intent intent = new Intent();
		if (selectDeviceAdapter.getCheckItem() == -1) {
			ToastUtil.show(mActivity, "请选择需要关联的设备", 200);
			return;
		}
		Object data = datas.get(selectDeviceAdapter.getCheckItem());
		if (TableNameEnum.BDZ.getTableName().equals(tbName)) {// 变电站
			intent.putExtra("LinkDeviceResult", (EcSubstationEntity) data);
		} else if (TableNameEnum.XSBDZ.getTableName().equals(tbName)) {// 箱式变电站
			intent.putExtra("LinkDeviceResult", (EcXsbdzEntity) data);
		} else if (TableNameEnum.KGZ.getTableName().equals(tbName)) {// 开关站
			intent.putExtra("LinkDeviceResult", (EcKgzEntity) data);
		} else if (TableNameEnum.GT.getTableName().equals(tbName)) {// 杆塔
			intent.putExtra("LinkDeviceResult", (EcTowerEntity) data);
		} else if (TableNameEnum.BYQ.getTableName().equals(tbName)) {// 变压器
			intent.putExtra("LinkDeviceResult", (EcTransformerEntity) data);
		} else if (TableNameEnum.PDF.getTableName().equals(tbName)) {// 配电室
			intent.putExtra("LinkDeviceResult", (EcDistributionRoomEntity) data);
		} else if (TableNameEnum.DYPDX.getTableName().equals(tbName)) {// 低压配电箱
			intent.putExtra("LinkDeviceResult", (EcDypdxEntity) data);
		} else if (TableNameEnum.GJ.getTableName().equals(tbName)) {// 电缆井
			intent.putExtra("LinkDeviceResult", (EcWorkWellEntity) data);
		} else if (TableNameEnum.HWG.getTableName().equals(tbName)) {// 环网柜
			intent.putExtra("LinkDeviceResult", (EcHwgEntity) data);
		} else if (TableNameEnum.DLDZBQ.getTableName().equals(tbName)) {// 电子标签
			intent.putExtra("LinkDeviceResult", (EcLineLabelEntity) data);
		} else if (TableNameEnum.DLFZX.getTableName().equals(tbName)) {// 电缆分支箱
			intent.putExtra("LinkDeviceResult", (EcDlfzxEntity) data);
		} else if (TableNameEnum.DYDLFZX.getTableName().equals(tbName)) {// 低压电缆分支箱
			intent.putExtra("LinkDeviceResult", (EcDydlfzxEntity) data);
		} else if (TableNameEnum.DLZJJT.getTableName().equals(tbName)) {// 中间接头
			intent.putExtra("LinkDeviceResult", (EcMiddleJointEntity) data);
		}
		// 。。。。

		mActivity.setResult(Activity.RESULT_OK, intent);
		mActivity.finish();
	}

	/**
	 * 执行查询
	 */
	@SuppressWarnings("unchecked")
	private void query() {
		// TODO Auto-generated method stub
		String tbName = getTbName();
		String keyWord = viewHolder.getEdtTxtKeywords().getText().toString();
		datas = deviceLinkBll.getDatas(tbName, keyWord, pageIndex, pageSize);
		ArrayList<Object> models = (ArrayList<Object>) datas;
		// 更新数据
		selectDeviceAdapter.update(models);
		selectDeviceAdapter.resetChkMaps();
		selectDeviceAdapter.notifyDataSetChanged();
		viewHolder.getPageControl().initPageShow(deviceLinkBll.getCount(tbName, keyWord));
	}

	@SuppressWarnings("unchecked")
	private void initDatas() {
		// TODO Auto-generated method stub
		String tbName = getTbName();
		datas = deviceLinkBll.getDatas(getTbName(), "", pageIndex, pageSize);
		ArrayList<Object> models = (ArrayList<Object>) datas;
		selectDeviceAdapter = new SelectDeviceAdapter(mActivity, models);
		viewHolder.getLvSelectDeviceList().setAdapter(selectDeviceAdapter);
		// 分页控件
		viewHolder.getPageControl().initPageShow(deviceLinkBll.getCount(tbName, ""));
		viewHolder.getPageControl().setPageChangeListener(new PageChangeListenerImpl());

	}

	class PageChangeListenerImpl implements OnPageChangeListener {

		@Override
		public void pageChanged(int curPage, int numPerPage) {
			// TODO Auto-generated method stub
			datas = deviceLinkBll.getDatas(getTbName(), viewHolder.getEdtTxtKeywords().getText().toString(), curPage,
					numPerPage);
			@SuppressWarnings("unchecked")
			ArrayList<Object> models = (ArrayList<Object>) datas;
			// 更新列表数据
			selectDeviceAdapter.update(models);
			selectDeviceAdapter.resetChkMaps();
			selectDeviceAdapter.notifyDataSetChanged();
		}

	}

	// 获取表名
	private String getTbName() {
		Intent intent = getIntent();
		return intent.getStringExtra("LinkDeviceMark");
	}

}
