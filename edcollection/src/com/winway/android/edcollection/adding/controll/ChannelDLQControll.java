package com.winway.android.edcollection.adding.controll;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.lidroid.xutils.ViewUtils;
import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.LableActivity;
import com.winway.android.edcollection.adding.entity.ChannelLabelEntity;
import com.winway.android.edcollection.adding.util.CheckInputChannelNameUtil;
import com.winway.android.edcollection.adding.util.CommentChannelUtil;
import com.winway.android.edcollection.adding.viewholder.ChannelDLQViewHolder;
import com.winway.android.edcollection.adding.viewholder.CommentChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
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
 * 电缆桥
 * 
 * @author lyh
 * @data 2017年2月13日
 */
public class ChannelDLQControll extends BaseControll<ChannelDLQViewHolder> {
	private EcChannelEntity channelEntity;
	private String id = UUIDGen.randomUUID();
	private CommentChannelUtil channelUtil;
	private EcChannelDlqEntity ecChannelDlqEntity;
	private CommentChannelViewHolder commentChannelViewHolder;
	private String endNodeOid;
	private final int BQ_RequestCode = 12; // 标签采集请求码
	private EcLineLabelEntityVo ecLineLabelEntityVo;// 标签数据
	private ChannelLabelEntity channelLabelEntity;
	private GlobalCommonBll commonBll;

	@Override
	public void init() {
		ViewUtils.inject(viewHolder.getCommentChannelViewHolder(), mActivity);
		commentChannelViewHolder = viewHolder.getCommentChannelViewHolder();
		String prjGDBUrl = FileUtil.AppRootPath + "/db/common/global.db";
		commonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		channelUtil = new CommentChannelUtil(mActivity, prjGDBUrl);
		initDatas();
		initEvent();
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		channelUtil.initCommentSetting(commentChannelViewHolder, mActivity, commonBll);
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
		}
		// 所属地市是否显示
//		if (GlobalEntry.ssds) {
//			viewHolder.getInscSsds().setVisibility(View.VISIBLE);
//		}
		// 材质
		String czParamTypeNo = "010604081";
		InputSelectAdapter czAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(czParamTypeNo));
		viewHolder.getInSconCz().setAdapter(czAdapter);
		// 地区特征
		String ditzParamTypeNo = "0199001";
		InputSelectAdapter dqtzAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(ditzParamTypeNo));
		viewHolder.getInSelConDqtz().setAdapter(dqtzAdapter);
		// 设备状态
		String sbztParamTypeNo = "010402";
		InputSelectAdapter sbztAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(sbztParamTypeNo));
		viewHolder.getInSconSbzt().setAdapter(sbztAdapter);
		// 资产性质
		String zcxzParamTypeNo = "010403";
		InputSelectAdapter zcxzAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(zcxzParamTypeNo));
		viewHolder.getInscZcxz().setAdapter(zcxzAdapter);
		// 专业分类
		String zyflParamTypeNo = "zyfl010511";
		InputSelectAdapter zyflAdapter = new InputSelectAdapter(mActivity,
				commonBll.getDictionaryNameList(zyflParamTypeNo));
		viewHolder.getInscZyfl().setAdapter(zyflAdapter);
		// 是否编辑
		Intent intent = getIntent();
		ecChannelDlqEntity = (EcChannelDlqEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
		channelEntity = (EcChannelEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
		channelLabelEntity = (ChannelLabelEntity) intent.getSerializableExtra("channelLabelEntity");
		if (channelEntity != null) {
			channelUtil.updateData(channelEntity, commentChannelViewHolder);
		}
		if (ecChannelDlqEntity != null) {
			// 作用是当从通道修改界面中点击通道类型修改按钮进来此界面时，下拉框行不会为空
			if (ecChannelDlqEntity.getCz() == null) {
				return;
			}
			viewHolder.getCommentChannelViewHolder().getInconSsds().setEdtText(StringUtils.nullStrToEmpty(ecChannelDlqEntity.getSsds()));
			viewHolder.getInSconCz().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getCz()));
			viewHolder.getInSelConDqtz().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getDqtz()));
			viewHolder.getInSconSbzt().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getSbzt()));
			viewHolder.getInscZcxz().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getZcxz()));
			viewHolder.getInConYxbh().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getYxbh()));
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
			viewHolder.getInConFhcl().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getFhcl()));
			viewHolder.getInConSgdw().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getSgdw()));
			viewHolder.getInConZcdw().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getZcdw()));
			viewHolder.getInscZyfl().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlqEntity.getZyfl()));
			String sgrq = DateUtils
					.date2Str(ecChannelDlqEntity.getSgrq(), DateUtils.date_sdf_wz_hm);
			viewHolder.getInConSgrq().setEdtText(sgrq);
			String jgrq = DateUtils
					.date2Str(ecChannelDlqEntity.getJgrq(), DateUtils.date_sdf_wz_hm);
			viewHolder.getInConJgrq().setEdtText(jgrq);
		}
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		viewHolder.getInConSgrq().setEditTextFocus(false);
		viewHolder.getInConJgrq().setEditTextFocus(false);
		viewHolder.getInscYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInscWhbz().getEditTextView().setEnabled(false);
		// 用户不能输入起点设备编号
		viewHolder.getCommentChannelViewHolder().getInconQdsbbh().getEditTextView()
				.setEnabled(false);
		viewHolder.getInConSgrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInConSgrq().getEditTextView());
			}
		});
		viewHolder.getInConJgrq().getEditTextView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInConJgrq().getEditTextView());
			}
		});
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getCommentChannelViewHolder().getBtnLinkLabelLink().setOnClickListener(ocl);
		// 命名规范
		viewHolder.getCommentChannelViewHolder().getBtnNamindConventions().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
				case R.id.tv_head_item_return : // 返回
				{
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
					intent.putExtra("devType", ResourceEnum.QJ.getName());
					AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent,
							BQ_RequestCode);
					break;
				}
				case R.id.tv_head_item_ok : // 确定
				{
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
					EcChannelDlqEntity ecChannelDlqEntity = saveData();
					saveEndNodeOid();
					if (ecChannelDlqEntity == null) {
						return;
					} else if (channelEntity == null) {
						return;
					} else if (endNodeOid == null) {
						return;
					}
					Intent intent = new Intent();
					intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA, ecChannelDlqEntity);
					intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL, channelEntity);
					intent.putExtra("channelLabelEntity", channelLabelEntity);
					intent.putExtra("endNodeOid", endNodeOid);
					mActivity.setResult(Activity.RESULT_OK, intent);
					mActivity.finish();
					break;
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
		if (channelEntity == null) {
			channelEntity = new EcChannelEntity();
		}
		channelUtil.saveChannelData(channelEntity, EcChannelDlqEntity.class, id,
				commentChannelViewHolder);
	}

	// 保存
	private EcChannelDlqEntity saveData() {
		String yxbh = viewHolder.getInConYxbh().getEdtTextValue();
		String dqtz = viewHolder.getInSelConDqtz().getEdtTextValue();
		String cz = viewHolder.getInSconCz().getEdtTextValue();
		String fhcl = viewHolder.getInConFhcl().getEdtTextValue();
		String sgdw = viewHolder.getInConSgdw().getEdtTextValue();
		String zcxz = viewHolder.getInscZcxz().getEdtTextValue();
		String zcdw = viewHolder.getInConZcdw().getEdtTextValue();
		String zyfl = viewHolder.getInscZyfl().getEdtTextValue();
		String sbzt = viewHolder.getInSconSbzt().getEdtTextValue();
		if (ecChannelDlqEntity == null) {
			ecChannelDlqEntity = new EcChannelDlqEntity();
			ecChannelDlqEntity.setObjId(id);
			ecChannelDlqEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecChannelDlqEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecChannelDlqEntity.setCjsj(DateUtils.getDate());
			Date sgrqDate = DateUtils.str2Date(viewHolder.getInConSgrq().getEdtTextValue(),
					DateUtils.date_sdf_wz_hm);
			if (sgrqDate == null) {
				sgrqDate = DateUtils.getDate();
			}
			ecChannelDlqEntity.setSgrq(sgrqDate);
		}
		ecChannelDlqEntity.setGxsj(DateUtils.getDate());
		ecChannelDlqEntity.setBz(null == channelEntity ? "" : channelEntity.getRemarks());
		ecChannelDlqEntity.setCjsj(new Date());
		ecChannelDlqEntity.setCz(cz);
		ecChannelDlqEntity.setDqtz(dqtz);
		ecChannelDlqEntity.setFhcl(fhcl);
		ecChannelDlqEntity.setGxsj(new Date());
		Date jgrqDate = DateUtils.str2Date(viewHolder.getInConJgrq().getEdtTextValue(),
				DateUtils.date_sdf_wz_hm);
		if (jgrqDate == null) {
			jgrqDate = DateUtils.getDate();
		}
		ecChannelDlqEntity.setJgrq(jgrqDate);
		ecChannelDlqEntity.setSbzt(sbzt);
		ecChannelDlqEntity.setSgdw(sgdw);
//		if (GlobalEntry.ssds) {
			String ssds = viewHolder.getCommentChannelViewHolder().getInconSsds().getEdtTextValue();
			ecChannelDlqEntity.setSsds(ssds);
//		}
		ecChannelDlqEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecChannelDlqEntity.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecChannelDlqEntity.setYxbh(yxbh);
		ecChannelDlqEntity.setZcdw(zcdw);
		ecChannelDlqEntity.setZcxz(zcxz);
		ecChannelDlqEntity.setZyfl(zyfl);
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.QJ.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(ecChannelDlqEntity.getObjId());
			channelLabelEntity.setObjId(ecChannelDlqEntity.getObjId());
			channelLabelEntity.setTypeNo(ResourceEnum.QJ.getValue() + "");
			channelLabelEntity.setEcLineLabelEntityVo(ecLineLabelEntityVo);
		}
		return ecChannelDlqEntity;
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
