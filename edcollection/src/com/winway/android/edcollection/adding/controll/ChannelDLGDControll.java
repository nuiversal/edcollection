package com.winway.android.edcollection.adding.controll;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;

import com.lidroid.xutils.ViewUtils;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.LableActivity;
import com.winway.android.edcollection.adding.entity.ChannelLabelEntity;
import com.winway.android.edcollection.adding.util.CheckInputChannelNameUtil;
import com.winway.android.edcollection.adding.util.CommentChannelUtil;
import com.winway.android.edcollection.adding.viewholder.ChannelDLGDViewHolder;
import com.winway.android.edcollection.adding.viewholder.CommentChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.ewidgets.datetimepicker.DateTimePickDialogUtil;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.ToastUtils;
import com.winway.android.util.UUIDGen;

/**
 * 电缆管道
 * 
 * @author
 *
 */
public class ChannelDLGDControll extends BaseControll<ChannelDLGDViewHolder> {
	private EcChannelEntity channelEntity;
	private GlobalCommonBll commonBll;
	private EcChannelDlgdEntity channelDlgdEntity;
	private String id = UUIDGen.randomUUID();
	private CommentChannelViewHolder commentChannelViewHolder;
	private CommentChannelUtil channelUtil;
	private String endNodeOid;
	private final int BQ_RequestCode = 12; // 标签采集请求码
	private EcLineLabelEntityVo ecLineLabelEntityVo;// 标签数据
	private ChannelLabelEntity channelLabelEntity;

	@Override
	public void init() {
		ViewUtils.inject(viewHolder.getCommentChannelViewHolder(), mActivity);
		commentChannelViewHolder = viewHolder.getCommentChannelViewHolder();
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		commonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		channelUtil = new CommentChannelUtil(mActivity, prjGDBUrl);
		initData();
		initEvent();
		initSettings();
	}

	private void initSettings() {
		// TODO Auto-generated method stub
		// 行
		viewHolder.getInconRow().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 列
		viewHolder.getInconCol().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 管径
		viewHolder.getInconGj().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 穿管数量
		viewHolder.getInconCgsl().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 管道容量
		viewHolder.getInconGdrl().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 井数量
		viewHolder.getInconJsl().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// 投运日期
		viewHolder.getInconTyrq().setEditTextFocus(false);
		viewHolder.getInconTyrq().getEditTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconTyrq().getEditTextView());
			}
		});
		// 峻工日期
		viewHolder.getInconJgrq().setEditTextFocus(false);
		viewHolder.getInconJgrq().getEditTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconJgrq().getEditTextView());
			}
		});
	}

	// 初始化事件
	private void initEvent() {
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getCommentChannelViewHolder().getBtnLinkLabelLink().setOnClickListener(ocl);
		viewHolder.getInscYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInscWhbz().getEditTextView().setEnabled(false);
		// 用户不能输入起点设备编号
		commentChannelViewHolder.getInconQdsbbh().getEditTextView().setEnabled(false);
		// 命名规范
		viewHolder.getCommentChannelViewHolder().getBtnNamindConventions().setOnClickListener(ocl);
	}

	// 初始化数据
	private void initData() {
		channelUtil.initCommentSetting(commentChannelViewHolder, mActivity, commonBll);
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		}
		// 所属地市是否显示
//		if (GlobalEntry.ssds) {
//			viewHolder.getInscSsds().setVisibility(View.VISIBLE);
//		}
		// 资产性质
		String zcxzParamTypeNo = "010403";
		InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(zcxzParamTypeNo));
		viewHolder.getInscZcxz().setAdapter(zcxzAdapter);
		// 初始地区特征下拉列表
		String ditzParamTypeNo = "0199001";// 地区特征
		InputSelectAdapter dqtzAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(ditzParamTypeNo));
		viewHolder.getInscDqtz().setAdapter(dqtzAdapter);
		// 设备状态
		String sbztParamTypeNo = "010402";
		InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(sbztParamTypeNo));
		viewHolder.getInscSbzt().setAdapter(sbztAdapter);
		// 管道类型
		String gdlxParamTypeNo = "010606003";
		InputSelectAdapter gdlxAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(gdlxParamTypeNo));
		viewHolder.getInscGdlx().setAdapter(gdlxAdapter);
		// s施工方式
		String sgfsParamTypeNo = "sgfx010510";
		InputSelectAdapter sgfsAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(sgfsParamTypeNo));
		viewHolder.getInscSgfs().setAdapter(sgfsAdapter);
		// 专业分类
		String zyflParamTypeNo = "zyfl010511";
		InputSelectAdapter zyflAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(zyflParamTypeNo));
		viewHolder.getInscZyfl().setAdapter(zyflAdapter);
		// 是否编辑
		Intent intent = getIntent();
		channelDlgdEntity = (EcChannelDlgdEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
		channelEntity = (EcChannelEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
		channelLabelEntity = (ChannelLabelEntity) intent.getSerializableExtra("channelLabelEntity");
		if (channelEntity != null) {
			channelUtil.updateData(channelEntity, commentChannelViewHolder);
		}
		if (channelDlgdEntity != null) {
			// 作用是当从通道修改界面中点击通道类型修改按钮进来此界面时，下拉框行不会为空
			if (channelDlgdEntity.getZcxz() == null) {
				return;
			}
			viewHolder.getCommentChannelViewHolder().getInconSsds().setEdtText(StringUtils.nullStrToEmpty(channelDlgdEntity.getSsds()));
			viewHolder.getInscZcxz().setEdtTextValue(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getZcxz()));
			viewHolder.getInscDqtz().setEdtTextValue(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getDqtz()));
			viewHolder.getInscSbzt().setEdtTextValue(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getSbzt()));
			viewHolder.getInscGdlx().setEdtTextValue(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getGdlx()));
			viewHolder.getInconmbmc().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getMbmc()));
			viewHolder.getInconRow().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getRow()));
			viewHolder.getInconCol().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getCol()));
			viewHolder.getInconGdbh().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getGdbh()));
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
			viewHolder.getInconZcdw().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getZcdw()));
			viewHolder.getInconDamc().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getDamc()));
			viewHolder.getInconJmlx().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getJmlx()));
			viewHolder.getInconCl().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getCl()));
			viewHolder.getInconGj().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getGj()));
			viewHolder.getInconCgsl().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getCgsl()));
			viewHolder.getInconJsl().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getJsl()));
			viewHolder.getInconSgdw().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getSgdw()));
			viewHolder.getInscSgfs().setEdtTextValue(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getSgfs()));
			viewHolder.getInconTzbh().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getTzbh()));
			viewHolder.getInscZyfl().setEdtTextValue(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getZyfl()));
			viewHolder.getInconGdrl().setEdtText(
					StringUtils.nullStrToEmpty(channelDlgdEntity.getGdrl()));
			String tyrq = DateUtils.date2Str(channelDlgdEntity.getTyrq(), DateUtils.date_sdf_wz_hm);
			viewHolder.getInconTyrq().setEdtText(tyrq);
			String jgrq = DateUtils.date2Str(channelDlgdEntity.getJgrq(), DateUtils.date_sdf_wz_hm);
			viewHolder.getInconJgrq().setEdtText(jgrq);
		}
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv_head_item_return : {
					Intent intent = new Intent();
					// 任务明细设置状态时调用
					intent.putExtra("isTask", true);
					mActivity.setResult(Activity.RESULT_CANCELED, intent);
					mActivity.finish();
					break;
				}
				case R.id.btn_link_label_label : {// 标签采集
					if (viewHolder.getCommentChannelViewHolder().getInconMc().getEdtTextValue()
							.equals("")) {
						ToastUtil.show(mActivity, "请先输入通道名称！", 200);
						break;
					}
					Intent intent = new Intent(mActivity, LableActivity.class);
					if (channelLabelEntity != null) {
						EcLineLabelEntityVo ecLineLabelEntityVo = channelLabelEntity
								.getEcLineLabelEntityVo();
						if (ecLineLabelEntityVo != null) {
							intent.putExtra("LableEntity", ecLineLabelEntityVo);
						}
					} else {
						intent.putExtra("LableEntity", ecLineLabelEntityVo);
					}
					intent.putExtra("devName", viewHolder.getCommentChannelViewHolder()
							.getInconMc().getEdtTextValue().trim());
					intent.putExtra("devType", ResourceEnum.PG.getName());
					AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent,
							BQ_RequestCode);
					break;
				}
				case R.id.tv_head_item_ok : {
					if(!channelUtil.checkIsNull(mActivity, commentChannelViewHolder)) {
						return;
					}
					CheckInputChannelNameUtil nameUtil = CheckInputChannelNameUtil.getInstance(
							mActivity, GlobalEntry.prjDbUrl);
					if (nameUtil.isHasChannelName(viewHolder.getCommentChannelViewHolder()
							.getInconMc().getEdtTextValue(), channelEntity)) {
						// channelEntity = null;
						ToastUtils.show(mActivity, "此通道名称已经存在，请重新填写");
						//获取焦点
						viewHolder.getCommentChannelViewHolder().getInconMc().getEditTextView().requestFocus();
						return;
					}
					saveChannelData();
					saveData();
					saveEndNodeOid();
					if (channelDlgdEntity == null) {
						return;
					} else if (channelEntity == null) {
						return;
					} else if (endNodeOid == null) {
						return;
					}
					Intent intent = new Intent();
					intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA, channelDlgdEntity);
					intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL, channelEntity);
					intent.putExtra("channelLabelEntity", channelLabelEntity);
					intent.putExtra("endNodeOid", endNodeOid);
					mActivity.setResult(Activity.RESULT_OK, intent);
					mActivity.finish();
				}
				case R.id.btn_namingConventions : {// 命名规范
					commonBll.showNamngConvientionsFloatWin(mActivity, viewHolder.getRlNameConventions(), viewHolder.getCommentChannelViewHolder().getInconMc());
					break;
				}
				default :
					break;
			}
		}
	};

	// 保存终点设备编号
	private void saveEndNodeOid() {
		endNodeOid = channelUtil.getOid();
	}

	// 保存通道数据
	private void saveChannelData() {
		// TODO Auto-generated method stub
		if (channelEntity == null) {
			channelEntity = new EcChannelEntity();
		}
		channelUtil.saveChannelData(channelEntity, EcChannelDlgdEntity.class, id,
				commentChannelViewHolder);
	}

	/**
	 * 确定保存
	 * 
	 * @return
	 */
	protected void saveData() {
		// TODO Auto-generated method stub
		String mbmc = viewHolder.getInconmbmc().getEdtTextValue();
		String row = viewHolder.getInconRow().getEdtTextValue();
		String col = viewHolder.getInconCol().getEdtTextValue();
		String gdbh = viewHolder.getInconGdbh().getEdtTextValue();
		String zcxz = viewHolder.getInscZcxz().getEdtTextValue();
		String zcdw = viewHolder.getInconZcdw().getEdtTextValue();
		String dqtz = viewHolder.getInscDqtz().getEdtTextValue();
		String sbzt = viewHolder.getInscSbzt().getEdtTextValue();
		String damc = viewHolder.getInconDamc().getEdtTextValue();
		String jmlx = viewHolder.getInconJmlx().getEdtTextValue();
		String cl = viewHolder.getInconCl().getEdtTextValue();
		String gj = viewHolder.getInconGj().getEdtTextValue();
		String cgsl = viewHolder.getInconCgsl().getEdtTextValue();
		String jsl = viewHolder.getInconJsl().getEdtTextValue();
		String gdlx = viewHolder.getInscGdlx().getEdtTextValue();
		String sgdw = viewHolder.getInconSgdw().getEdtTextValue();
		String sgfs = viewHolder.getInscSgfs().getEdtTextValue();
		String tzbh = viewHolder.getInconTzbh().getEdtTextValue();
		String zyfl = viewHolder.getInscZyfl().getEdtTextValue();
		String gdrl = viewHolder.getInconGdrl().getEdtTextValue();
		if (channelDlgdEntity == null) {
			channelDlgdEntity = new EcChannelDlgdEntity();
			channelDlgdEntity.setObjId(id);
			channelDlgdEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			channelDlgdEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			channelDlgdEntity.setCjsj(DateUtils.getDate());
			Date tyrqDate = DateUtils.str2Date(viewHolder.getInconTyrq().getEdtTextValue(),
					DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			channelDlgdEntity.setTyrq(tyrqDate);
		}
		channelDlgdEntity.setGxsj(DateUtils.getDate());
		channelDlgdEntity.setBz(null == channelEntity ? "" : channelEntity.getRemarks());
		channelDlgdEntity.setCgsl(cgsl.isEmpty() ? null : Integer.parseInt(cgsl));
		channelDlgdEntity.setCjsj(new Date());
		channelDlgdEntity.setCl(cl);
		channelDlgdEntity.setCol(col.isEmpty() ? null : Integer.parseInt(col));
		channelDlgdEntity.setDamc(damc);
//		if (GlobalEntry.ssds) {
			String ssds = viewHolder.getCommentChannelViewHolder().getInconSsds().getEdtTextValue();
			channelDlgdEntity.setSsds(ssds);
//		}
		channelDlgdEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		channelDlgdEntity.setWhbz(GlobalEntry.loginResult.getDeptid());
		channelDlgdEntity.setDqtz(dqtz);
		channelDlgdEntity.setGdbh(gdbh);
		channelDlgdEntity.setGdcd(null == channelEntity ? null : channelEntity
				.getChannelLength());
		channelDlgdEntity.setGdlx(gdlx);
		channelDlgdEntity.setGdrl(gdrl.isEmpty() ? null : Integer.parseInt(gdrl));
		channelDlgdEntity.setGj(gj.isEmpty() ? null : Double.parseDouble(gj));
		channelDlgdEntity.setGxsj(new Date());
		channelDlgdEntity.setJmlx(jmlx);
		channelDlgdEntity.setJsl(jsl.isEmpty() ? null : Integer.parseInt(jsl));
		channelDlgdEntity.setMbmc(mbmc);
		channelDlgdEntity.setRow(row.isEmpty() ? null : Integer.parseInt(row));
		channelDlgdEntity.setSbzt(sbzt);
		channelDlgdEntity.setSgdw(sgdw);
		channelDlgdEntity.setSgfs(sgfs);
		Date jgrqDate = DateUtils.str2Date(viewHolder.getInconJgrq().getEdtTextValue(),
				DateUtils.date_sdf_wz_hm);
		if (jgrqDate == null) {
			jgrqDate = DateUtils.getDate();
		}
		channelDlgdEntity.setJgrq(jgrqDate);
		channelDlgdEntity.setTzbh(tzbh);
		channelDlgdEntity.setZcxz(zcxz);
		channelDlgdEntity.setZcdw(zcdw);
		channelDlgdEntity.setZyfl(zyfl);
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.PG.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(channelDlgdEntity.getObjId());
			channelLabelEntity.setObjId(channelDlgdEntity.getObjId());
			channelLabelEntity.setTypeNo(ResourceEnum.PG.getValue() + "");
			channelLabelEntity.setEcLineLabelEntityVo(ecLineLabelEntityVo);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == BQ_RequestCode) { // 标签采集
				ecLineLabelEntityVo = (EcLineLabelEntityVo) data.getSerializableExtra("lable");
				if (ecLineLabelEntityVo != null) {
					if (channelLabelEntity == null) {
						channelLabelEntity = new ChannelLabelEntity();
					}
					channelLabelEntity.setEcLineLabelEntityVo(ecLineLabelEntityVo);
				}
			}
		}
	}
}
