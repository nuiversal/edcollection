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
import com.winway.android.edcollection.adding.viewholder.ChannelDLGViewHolder;
import com.winway.android.edcollection.adding.viewholder.CommentChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
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
 * 电缆沟
 * 
 * @author
 *
 */
public class ChannelDLGControll extends BaseControll<ChannelDLGViewHolder> {
	private EcChannelEntity channelEntity;
	private GlobalCommonBll commonBll;
	private EcChannelDlgEntity ecChannelDlgEntity;
	private String id = UUIDGen.randomUUID();
	private CommentChannelViewHolder commentChannelViewHolder;
	private CommentChannelUtil channelUtil;
	private String endNodeOid;
	private final int BQ_RequestCode = 12; // 标签采集请求码
	private EcLineLabelEntityVo ecLineLabelEntityVo;// 标签数据
	private ChannelLabelEntity channelLabelEntity;

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
		initSettings();
	}

	private void initSettings() {
		// TODO Auto-generated method stub
		// 电缆沟盖板数量
		viewHolder.getInconDlgbgsl().setEditTextInputType(InputType.TYPE_CLASS_NUMBER);
		// 投运日期
		viewHolder.getInconTyrq().setEditTextFocus(false);
		viewHolder.getInconTyrq().getEditTextView().setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePickDialogUtil updaUtil = new DateTimePickDialogUtil(mActivity, null);
				updaUtil.dateTimePicKDialog(viewHolder.getInconTyrq().getEditTextView());
			}
		});
		viewHolder.getInconJgrq().setEditTextFocus(false);
		// 峻工日期
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
		viewHolder.getInscYwdw().getEditTextView().setEnabled(false);
		viewHolder.getInscWhbz().getEditTextView().setEnabled(false);
		viewHolder.getCommentChannelViewHolder().getBtnLinkLabelLink().setOnClickListener(ocl);
		viewHolder.getCommentChannelViewHolder().getBtnNamindConventions().setOnClickListener(ocl);
		// 用户不能输入起点设备编号
		commentChannelViewHolder.getInconQdsbbh().getEditTextView().setEnabled(false);
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
		ecChannelDlgEntity = (EcChannelDlgEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
		channelEntity = (EcChannelEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
		channelLabelEntity = (ChannelLabelEntity) intent.getSerializableExtra("channelLabelEntity");
		if (channelEntity != null) {
			channelUtil.updateData(channelEntity, commentChannelViewHolder);
		}
		if (ecChannelDlgEntity != null) {
			// 作用是当从通道修改界面中点击通道类型修改按钮进来此界面时，下拉框行不会为空
			if (ecChannelDlgEntity.getZyfl() == null) {
				return;
			}
			viewHolder.getCommentChannelViewHolder().getInconSsds().setEdtText(StringUtils.nullStrToEmpty(ecChannelDlgEntity.getSsds()));
			viewHolder.getInscZyfl().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getZyfl()));
			viewHolder.getInscDqtz().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getDqtz()));
			viewHolder.getInscSbzt().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getSbzt()));
			viewHolder.getInconDlgbh().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getDlgbh()));
			viewHolder.getInscYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getInscWhbz().setEdtTextValue(GlobalEntry.loginResult.getDeptname());
			viewHolder.getInscZcxz().setEdtTextValue(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getZcxz()));
			viewHolder.getInconZcdw().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getZcdw()));
			viewHolder.getInconZcbh().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getZcbh()));
			viewHolder.getInconDmcc().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getDmcc()));
			viewHolder.getInconDlgbgsl().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getDlgbgsl()));
			viewHolder.getInconGbcz().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getGbcz()));
			viewHolder.getInconSgdw().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getSgdw()));
			viewHolder.getInconTzbh().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlgEntity.getTzbh()));
			String tyrq = DateUtils
					.date2Str(ecChannelDlgEntity.getTyrq(), DateUtils.date_sdf_wz_hm);
			viewHolder.getInconTyrq().setEdtText(tyrq);
			String jgrq = DateUtils
					.date2Str(ecChannelDlgEntity.getJgrq(), DateUtils.date_sdf_wz_hm);
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
					intent.putExtra("devType", ResourceEnum.GD.getName());
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
					if (ecChannelDlgEntity == null) {
						return;
					} else if (channelEntity == null) {
						return;
					} else if (endNodeOid == null) {
						return;
					}
					Intent intent = new Intent();
					intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA, ecChannelDlgEntity);
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
		channelUtil.saveChannelData(channelEntity, EcChannelDlgEntity.class, id,
				commentChannelViewHolder);
	}

	/**
	 * 确定保存
	 * 
	 * @return
	 */
	protected void saveData() {
		// TODO Auto-generated method stub
		if (ecChannelDlgEntity == null) {
			ecChannelDlgEntity = new EcChannelDlgEntity();
			ecChannelDlgEntity.setObjId(id);
			ecChannelDlgEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecChannelDlgEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			Date tyrqDate = DateUtils.str2Date(viewHolder.getInconTyrq().getEdtTextValue(),
					DateUtils.date_sdf_wz_hm);
			if (tyrqDate == null) {
				tyrqDate = DateUtils.getDate();
			}
			ecChannelDlgEntity.setTyrq(tyrqDate);
			ecChannelDlgEntity.setCjsj(DateUtils.getDate());
		}
		ecChannelDlgEntity.setGxsj(DateUtils.getDate());
		String dlgbh = viewHolder.getInconDlgbh().getEdtTextValue();
		String zcxz = viewHolder.getInscZcxz().getEdtTextValue();
		String zcdw = viewHolder.getInconZcdw().getEdtTextValue();
		String zcbh = viewHolder.getInconZcbh().getEdtTextValue();
		String sbzt = viewHolder.getInscSbzt().getEdtTextValue();
		String dqtz = viewHolder.getInscDqtz().getEdtTextValue();
		String dmcc = viewHolder.getInconDmcc().getEdtTextValue();
		String dlgbgsl = viewHolder.getInconDlgbgsl().getEdtTextValue();
		String gbcz = viewHolder.getInconGbcz().getEdtTextValue();
		String sgdw = viewHolder.getInconSgdw().getEdtTextValue();
		String tzbh = viewHolder.getInconTzbh().getEdtTextValue();
		String zyfl = viewHolder.getInscZyfl().getEdtTextValue();
		ecChannelDlgEntity.setDlgbh(dlgbh);
//		if (GlobalEntry.ssds) {
			String ssds = viewHolder.getCommentChannelViewHolder().getInconSsds().getEdtTextValue();
			ecChannelDlgEntity.setSsds(ssds);
//		}
		ecChannelDlgEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecChannelDlgEntity.setWhbz(GlobalEntry.loginResult.getDeptid());
		ecChannelDlgEntity.setZcxz(zcxz);
		ecChannelDlgEntity.setZcdw(zcdw);
		ecChannelDlgEntity.setZcbh(zcbh);
		ecChannelDlgEntity.setSbzt(sbzt);
		ecChannelDlgEntity.setDqtz(dqtz);
		ecChannelDlgEntity.setDmcc(dmcc);
		ecChannelDlgEntity.setDlgcd(null == channelEntity ? null : channelEntity
				.getChannelLength());
		ecChannelDlgEntity.setDlgbgsl(dlgbgsl.isEmpty() ? null : Integer.parseInt(dlgbgsl));
		ecChannelDlgEntity.setGbcz(gbcz);
		ecChannelDlgEntity.setSgdw(sgdw);
		Date jgrqDate = DateUtils.str2Date(viewHolder.getInconJgrq().getEdtTextValue(),
				DateUtils.date_sdf_wz_hm);
		if (jgrqDate == null) {
			jgrqDate = DateUtils.getDate();
		}
		ecChannelDlgEntity.setJgrq(jgrqDate);
		ecChannelDlgEntity.setTzbh(tzbh);
		ecChannelDlgEntity.setZyfl(zyfl);
		ecChannelDlgEntity.setBz(null == channelEntity ? "" : channelEntity.getRemarks());
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.GD.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(ecChannelDlgEntity.getObjId());
			channelLabelEntity.setObjId(ecChannelDlgEntity.getObjId());
			channelLabelEntity.setTypeNo(ResourceEnum.GD.getValue() + "");
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
