package com.winway.android.edcollection.adding.controll;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.adapter.JgListAdapter;
import com.winway.android.edcollection.adding.viewholder.JgViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcWorkWellCoverEntity;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtils;
import com.winway.android.util.UUIDGen;

/**
 * 井盖
 * 
 * @author lyh
 * @version 创建时间：2017年3月28日
 */
public class JgControll extends BaseControll<JgViewHolder> {
	private GlobalCommonBll globalCommonBll;
	private EcWorkWellCoverEntity coverEntity;
	private JgListAdapter adapter;
	private ArrayList<EcWorkWellCoverEntity> coverList;
	private boolean isUpdate = false;

	@Override
	public void init() {
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		initDatas();
		initEvent();
	}

	/**
	 * 初始化数据
	 */
	@SuppressWarnings("unchecked")
	private void initDatas() {
		Intent intent = getIntent();
		coverList = (ArrayList<EcWorkWellCoverEntity>) intent.getSerializableExtra("coverList");
		if (coverList != null && coverList.size() > 0) {
			if (adapter == null) {
				adapter = new JgListAdapter(mActivity, coverList);
				viewHolder.getLvJgList().setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}
		}
		// 初始是否系统中存在下拉列表
		String sfTypeNo = "010599";// 是否
		List<String> list = globalCommonBll.getDictionaryNameList(sfTypeNo);
		InputSelectAdapter jfxtzAdapter = new InputSelectAdapter(mActivity, list);
		viewHolder.getInscSfxtzcz().setAdapter(jfxtzAdapter);
		//给运维单位、维护班组赋值
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInconYwdw().setEdtText(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInconWhbz().setEdtText(GlobalEntry.loginResult.getDeptname());
		}
		viewHolder.getInconYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInconWhbz().getEditTextView().setEnabled(false);
		viewHolder.getInconJgcd().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInconJghd().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		viewHolder.getInconJgkd().setEditTextInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}

	/**
	 * 更新
	 */
	private void updateDatas(EcWorkWellCoverEntity ecWorkWellCoverEntity) {
		isUpdate = true;
		viewHolder.getInconXh().setEdtText(ecWorkWellCoverEntity.getXh());
		viewHolder.getInconSbmc().setEdtText(ecWorkWellCoverEntity.getSbmc());
		viewHolder.getInscSfxtzcz().setEdtTextValue(ecWorkWellCoverEntity.getBiz());
		viewHolder.getInconYwdw().setEdtText(GlobalEntry.loginResult.getOrgname());
		viewHolder.getInconWhbz().setEdtText(GlobalEntry.loginResult.getDeptname());
		viewHolder.getInconSszrq().setEdtText(ecWorkWellCoverEntity.getSszrq());
		viewHolder.getInconJgcd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellCoverEntity.getJgcd()));
		viewHolder.getInconJghd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellCoverEntity.getJghd()));
		viewHolder.getInconJgkd().setEdtText(StringUtils.nullStrToEmpty(ecWorkWellCoverEntity.getJgkd()));
		viewHolder.getInconZb().setEdtText(ecWorkWellCoverEntity.getBz());
	}

	/**
	 * 初始化事件操作
	 */
	private void initEvent() {
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getBtnAdd().setOnClickListener(ocl);
		viewHolder.getLvJgList().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				viewHolder.getBtnAdd().setText("修改");
				coverEntity = coverList.get(position);
				updateDatas(coverEntity);
			}
		});
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok: // 确定
			{
				Intent intent = new Intent();
				if (coverList != null && coverList.size() > 0) {
					intent.putExtra("coverList", coverList);
				}
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();
				break;
			}
			case R.id.btn_jg_Add: // 添加
			{
				if (viewHolder.getInconSbmc().getEdtTextValue().isEmpty()) {
					ToastUtils.show(mActivity, "设备名称不能为空！");
					viewHolder.getInconSbmc().getEditTextView().requestFocus();
					return;
				}
				if (viewHolder.getInconSszrq().getEdtTextValue().isEmpty()) {
					ToastUtils.show(mActivity, "所属责任区不能为空！");
					viewHolder.getInconSszrq().getEditTextView().requestFocus();
					return;
				}
				saveDatas();
				if (coverEntity == null) {
					return;
				}
				// 判断井盖列表里面有没这条数据，没有才添加
				if (coverList == null) {
					coverList = new ArrayList<EcWorkWellCoverEntity>();
				}
				if (coverList.contains(coverEntity)) {
					// 修改状态下刷新
					if (isUpdate) {
						adapter.notifyDataSetChanged();
					} else {
						return;
					}

				} else {
					coverList.add(coverEntity);
					// 井盖列表有数据的话就直接刷新，没有则new出来
					if (adapter == null) {
						adapter = new JgListAdapter(mActivity, coverList);
						viewHolder.getLvJgList().setAdapter(adapter);
					} else {
						adapter.notifyDataSetChanged();
					}
				}
				cleanDatas();
			}
			}
		}
	};
	
	/**
	 * 数据添加到井盖列表后清空输入框
	 */
	private void cleanDatas() {
		viewHolder.getInconXh().setEdtText(null);
		viewHolder.getInconSbmc().setEdtText(null);
		viewHolder.getInconSszrq().setEdtText(null);
		viewHolder.getInconJgcd().setEdtText(null);
		viewHolder.getInconJghd().setEdtText(null);
		viewHolder.getInconJgkd().setEdtText(null);
		viewHolder.getInconZb().setEdtText(null);
		viewHolder.getInscSfxtzcz().setEdtTextValue("是");
		isUpdate = false;
		viewHolder.getBtnAdd().setText("添加");
	}

	/**
	 * 保存数据
	 */
	private void saveDatas() {
		if (!isUpdate) {
			coverEntity = new EcWorkWellCoverEntity();
			coverEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			coverEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			coverEntity.setObjId(UUIDGen.randomUUID());
			coverEntity.setCjsj(DateUtils.getDate());
		}
		String xh = viewHolder.getInconXh().getEdtTextValue().trim();
		String sbmc = viewHolder.getInconSbmc().getEdtTextValue().trim();
		String sszrq = viewHolder.getInconSszrq().getEdtTextValue().trim();
		String jgcd = viewHolder.getInconJgcd().getEdtTextValue().trim();
		String jgkd = viewHolder.getInconJgkd().getEdtTextValue().trim();
		String jghd = viewHolder.getInconJghd().getEdtTextValue().trim();
		String bz = viewHolder.getInconZb().getEdtTextValue().trim();
		String sfxtcz = viewHolder.getInscSfxtzcz().getEdtTextValue();
		coverEntity.setXh(xh);
		coverEntity.setSbmc(sbmc);
		coverEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		coverEntity.setWhbz(GlobalEntry.loginResult.getDeptid());
		coverEntity.setSszrq(sszrq);
		coverEntity.setJgcd(jgcd.isEmpty() ? 0.0 : Double.parseDouble(jgcd));
		coverEntity.setJgkd(jgkd.isEmpty() ? 0.0 : Double.parseDouble(jgkd));
		coverEntity.setJghd(jghd.isEmpty() ? 0.0 : Double.parseDouble(jghd));
		coverEntity.setBz(bz);
		coverEntity.setBiz(sfxtcz);
		coverEntity.setGxsj(DateUtils.getDate());
	}
}
