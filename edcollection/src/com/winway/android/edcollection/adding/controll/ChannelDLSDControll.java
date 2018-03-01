package com.winway.android.edcollection.adding.controll;

import java.util.Date;
import java.util.List;

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
import com.winway.android.edcollection.adding.viewholder.ChannelDLSDViewHolder;
import com.winway.android.edcollection.adding.viewholder.CommentChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
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
 * 电缆隧道
 * 
 * @author
 *
 */
public class ChannelDLSDControll extends BaseControll<ChannelDLSDViewHolder> {
	private EcChannelEntity channelEntity;
	private String id = UUIDGen.randomUUID();
	private CommentChannelUtil channelUtil;
	private EcChannelDlsdEntity ecChannelDlsdEntity;
	private CommentChannelViewHolder commentChannelViewHolder;
	private String endNodeOid;
	private final int BQ_RequestCode = 12; // 标签采集请求码
	private EcLineLabelEntityVo ecLineLabelEntityVo;// 标签数据
	private ChannelLabelEntity channelLabelEntity;
	private GlobalCommonBll commonBll;

	@Override
	public void init() {
		// TODO Auto-generated method stub
		ViewUtils.inject(viewHolder.getCommentChannelViewHolder(), mActivity);
		commentChannelViewHolder = viewHolder.getCommentChannelViewHolder();
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		commonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		channelUtil = new CommentChannelUtil(mActivity, prjGDBUrl);
		initData();
		initEvent();
		initSetting();
	}

	private void initSetting() {
		// TODO Auto-generated method stub
		// 井数量
		viewHolder.getInconJsl().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		// 隧道容量
		viewHolder.getInconSdrl().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		// 穿管数量
		viewHolder.getInconCgsl().setEditTextInputType(
				InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
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
		// 用户不能输入起点设备编号
		viewHolder.getCommentChannelViewHolder().getInconQdsbbh().getEditTextView()
				.setEnabled(false);
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getCommentChannelViewHolder().getBtnLinkLabelLink().setOnClickListener(ocl);
		viewHolder.getInscYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInscWhbz().getEditTextView().setEnabled(false);
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
		// 设备状态
		String sbztParamTypeNo = "010402";
		InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(sbztParamTypeNo));
		viewHolder.getInscSbzt().setAdapter(sbztAdapter);
		// 资产性质
		String zcxzParamTypeNo = "010403";
		InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(zcxzParamTypeNo));
		viewHolder.getInscZcxz().setAdapter(zcxzAdapter);
		// 地区特征
		String ditzParamTypeNo = "0199001";
		InputSelectAdapter dqtzAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(ditzParamTypeNo));
		viewHolder.getInscDqtz().setAdapter(dqtzAdapter);
		// 悬挂方式
		String xgfsParamTypeNo = "010606001";
		InputSelectAdapter xgfsAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(xgfsParamTypeNo));
		viewHolder.getInscXgfs().setAdapter(xgfsAdapter);
		// 结构型式
		String jgxsParamTypeNo = "010606002";
		InputSelectAdapter jgxsAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(jgxsParamTypeNo));
		viewHolder.getInscJgxs().setAdapter(jgxsAdapter);
		// 专业分类
		String zyflParamTypeNo = "zyfl010511";
		InputSelectAdapter zyflAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(zyflParamTypeNo));
		viewHolder.getInscZyfl().setAdapter(zyflAdapter);
		// 初始是否下拉列表
		String sfTypeNo = "010599";// 是否
		List<String> list = commonBll.getDictionaryNameList(sfTypeNo);
		// 初始是否代维下拉列表
		InputSelectAdapter sfazfhchAdapter = new InputSelectAdapter(mActivity, list);
		viewHolder.getInscSfazfhc().setAdapter(sfazfhchAdapter);
		// 是否编辑
		Intent intent = getIntent();
		ecChannelDlsdEntity = (EcChannelDlsdEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
		channelEntity = (EcChannelEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
		channelLabelEntity = (ChannelLabelEntity) intent.getSerializableExtra("channelLabelEntity");
		if (channelEntity != null) {
			channelUtil.updateData(channelEntity, commentChannelViewHolder);
		}

		if (ecChannelDlsdEntity != null) {
			// 作用是当从通道修改界面中点击通道类型修改按钮进来此界面时，下拉框行不会为空
			if (ecChannelDlsdEntity.getSbzt() == null) {
				return;
			}
			viewHolder.getCommentChannelViewHolder().getInconSsds().setEdtText(StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getSsds()));
			viewHolder.getInscSbzt().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getSbzt()));
			viewHolder.getInscZcxz().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getZcxz()));
			viewHolder.getInscDqtz().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getDqtz()));
			viewHolder.getInscXgfs().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getXgfs()));
			viewHolder.getInscSfazfhc().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getSfazfhc()));
			viewHolder.getInconsdbh().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getSdbh()));
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
			viewHolder.getInconZcdw().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getZcdw()));
			viewHolder.getInconDamc().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getDamc()));
			viewHolder.getInscJgxs().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getJgxs()));
			viewHolder.getInconSmcc().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getDmcc()));
			viewHolder.getInconJsl().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getJsl()));
			viewHolder.getInconSgdw().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getSgdw()));
			viewHolder.getInconTzbh().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getTzbh()));
			viewHolder.getInconSdrl().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getSdrl()));
			viewHolder.getInconCgsl().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getCgsl()));
			viewHolder.getInscJmlx().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getJmlx()));
			viewHolder.getInscZyfl().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlsdEntity.getZyfl()));
			String tyrq = DateUtils.date2Str(ecChannelDlsdEntity.getTyrq(),
					DateUtils.date_sdf_wz_hm);
			viewHolder.getInconTyrq().setEdtText(tyrq);
			String jgrq = DateUtils.date2Str(ecChannelDlsdEntity.getJgrq(),
					DateUtils.date_sdf_wz_hm);
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
					intent.putExtra("devType", ResourceEnum.SD.getName());
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
						ToastUtils.show(mActivity, "此通道名称已经存在，请重新填写");
						//获取焦点
						viewHolder.getCommentChannelViewHolder().getInconMc().getEditTextView().requestFocus();
						return;
					}
					saveChannelData();
					EcChannelDlsdEntity ecChannelDlsdEntity = saveData();
					saveEndNodeOid();
					if (ecChannelDlsdEntity == null) {
						return;
					} else if (channelEntity == null) {
						return;
					}
					Intent intent = new Intent();
					intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA, ecChannelDlsdEntity);
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
		channelUtil.saveChannelData(channelEntity, EcChannelDlsdEntity.class, id,
				commentChannelViewHolder);
	}

	/**
	 * 确定保存
	 * 
	 * @return
	 */
	protected EcChannelDlsdEntity saveData() {
		// TODO Auto-generated method stub
		String sdbh = viewHolder.getInconsdbh().getEdtTextValue();
		String sbzt = viewHolder.getInscSbzt().getEdtTextValue();
		String zcxz = viewHolder.getInscZcxz().getEdtTextValue();
		String zcdw = viewHolder.getInconZcdw().getEdtTextValue();
		String dqtz = viewHolder.getInscDqtz().getEdtTextValue();
		String damc = viewHolder.getInconDamc().getEdtTextValue();
		String jgxs = viewHolder.getInscJgxs().getEdtTextValue();
		String xgfs = viewHolder.getInscXgfs().getEdtTextValue();
		String jmlx = viewHolder.getInscJmlx().getEdtTextValue();
		String dmcc = viewHolder.getInconSmcc().getEdtTextValue();
		String jsl = viewHolder.getInconJsl().getEdtTextValue();
		String sgdw = viewHolder.getInconSgdw().getEdtTextValue();
		String tzbh = viewHolder.getInconTzbh().getEdtTextValue();
		String zyfl = viewHolder.getInscZyfl().getEdtTextValue();
		String sfanfh = viewHolder.getInscSfazfhc().getEdtTextValue();
		String sdrl = viewHolder.getInconSdrl().getEdtTextValue();
		String cgsl = viewHolder.getInconCgsl().getEdtTextValue();
		if (ecChannelDlsdEntity == null) {
			ecChannelDlsdEntity = new EcChannelDlsdEntity();
			ecChannelDlsdEntity.setObjId(id);
			ecChannelDlsdEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecChannelDlsdEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecChannelDlsdEntity.setCjsj(DateUtils.getDate());
			Date tyrqDate = DateUtils.str2Date(viewHolder.getInconTyrq().getEdtTextValue(),
					DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			ecChannelDlsdEntity.setTyrq(tyrqDate);
		}
		ecChannelDlsdEntity.setGxsj(DateUtils.getDate());
		ecChannelDlsdEntity.setBz(null == channelEntity ? "" : channelEntity.getRemarks());
		ecChannelDlsdEntity.setCgsl(cgsl.isEmpty() ? null : Integer.parseInt(cgsl));
		ecChannelDlsdEntity.setCjsj(new Date());
		ecChannelDlsdEntity.setDamc(damc);
		ecChannelDlsdEntity.setDqtz(dqtz);
		ecChannelDlsdEntity.setGxsj(new Date());
		Date jgrqDate = DateUtils.str2Date(viewHolder.getInconJgrq().getEdtTextValue(),
				DateUtils.date_sdf_wz_hm);
		if (jgrqDate == null) {
			jgrqDate = DateUtils.getDate();
		}
		ecChannelDlsdEntity.setJgrq(jgrqDate);
		ecChannelDlsdEntity.setJgxs(jgxs);
		ecChannelDlsdEntity.setJmlx(jmlx);
		ecChannelDlsdEntity.setSbzt(sbzt);
		ecChannelDlsdEntity.setSdbh(sdbh);
		ecChannelDlsdEntity.setSdcd(null == channelEntity ? null : channelEntity
				.getChannelLength());
		ecChannelDlsdEntity.setSdrl(sdrl.isEmpty() ? null : Integer.parseInt(sdrl));
		ecChannelDlsdEntity.setSfazfhc(sfanfh);
		ecChannelDlsdEntity.setSgdw(sgdw);
//		if (GlobalEntry.ssds) {
			String ssds = viewHolder.getCommentChannelViewHolder().getInconSsds().getEdtTextValue();
			ecChannelDlsdEntity.setSsds(ssds);
//		}
		ecChannelDlsdEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecChannelDlsdEntity.setYwbz(GlobalEntry.loginResult.getDeptid());
		ecChannelDlsdEntity.setZcxz(zcxz);
		ecChannelDlsdEntity.setZcdw(zcdw);
		ecChannelDlsdEntity.setXgfs(xgfs);
		ecChannelDlsdEntity.setDmcc(dmcc);
		ecChannelDlsdEntity.setJsl(jsl.isEmpty() ? null : Integer.parseInt(jsl));
		ecChannelDlsdEntity.setTzbh(tzbh);
		ecChannelDlsdEntity.setZyfl(zyfl);
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.SD.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(ecChannelDlsdEntity.getObjId());
			channelLabelEntity.setObjId(ecChannelDlsdEntity.getObjId());
			channelLabelEntity.setTypeNo(ResourceEnum.SD.getValue() + "");
			channelLabelEntity.setEcLineLabelEntityVo(ecLineLabelEntityVo);
		}
		return ecChannelDlsdEntity;
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
