package com.winway.android.edcollection.adding.controll;

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
import com.winway.android.edcollection.adding.viewholder.ChanelDLZMViewHolder;
import com.winway.android.edcollection.adding.viewholder.CommentChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.vo.EcLineLabelEntityVo;
import com.winway.android.util.AndroidBasicComponentUtils;
import com.winway.android.util.DateUtils;
import com.winway.android.util.FileUtil;
import com.winway.android.util.StringUtils;
import com.winway.android.util.ToastUtil;
import com.winway.android.util.ToastUtils;
import com.winway.android.util.UUIDGen;

/**
 * 电缆直埋
 * 
 * @author lyh
 * @data 2017年2月13日
 */
public class ChannelDLZMControll extends BaseControll<ChanelDLZMViewHolder> {
	private GlobalCommonBll commonBll;
	private EcChannelEntity channelEntity;
	private String id = UUIDGen.randomUUID();
	private CommentChannelUtil channelUtil;
	private EcChannelDlzmEntity ecChannelDlzmEntity;
	private CommentChannelViewHolder commentChannelViewHolder;
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
		initDatas();
		initEvent();
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		channelUtil.initCommentSetting(commentChannelViewHolder, mActivity, commonBll);
		if (GlobalEntry.loginResult != null) {
			viewHolder.getIncomOperationYwdw()
					.setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		}
		// 所属地市是否显示
//		if (GlobalEntry.ssds) {
//			viewHolder.getIncomCity().setVisibility(View.VISIBLE);
//		}
		// 是否是编辑
		Intent intent = getIntent();
		ecChannelDlzmEntity = (EcChannelDlzmEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
		channelEntity = (EcChannelEntity) intent
				.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
		channelLabelEntity = (ChannelLabelEntity) intent.getSerializableExtra("channelLabelEntity");
		if (channelEntity != null) {
			channelUtil.updateData(channelEntity, commentChannelViewHolder);
		}
		if (ecChannelDlzmEntity != null) {
			viewHolder.getIncomRunningNo().setEdtText(
					StringUtils.nullStrToEmpty(ecChannelDlzmEntity.getYxbh()));
			viewHolder.getCommentChannelViewHolder().getInconSsds().setEdtText(StringUtils.nullStrToEmpty(ecChannelDlzmEntity.getSsds()));
//			viewHolder.getIncomOperationYwdw().setEdtTextValue(
//					StringUtils.nullStrToEmpty(ecChannelDlzmEntity.getYwdw()));
		}
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		// 用户不能输入起点设备编号
		viewHolder.getCommentChannelViewHolder().getInconQdsbbh().getEditTextView()
				.setEnabled(false);
		viewHolder.getIncomOperationYwdw().getEditTextView().setEnabled(false);
		viewHolder.getHcHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getCommentChannelViewHolder().getBtnLinkLabelLink().setOnClickListener(ocl);
		// 命名规范
		viewHolder.getCommentChannelViewHolder().getBtnNamindConventions().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
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
					intent.putExtra("devType", ResourceEnum.ZM.getName());
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
					EcChannelDlzmEntity ecChannelDlzmEntity = saveData();
					saveEndNodeOid();
					if (null == channelEntity) {
						return;
					} else if (null == ecChannelDlzmEntity) {
						return;
					}
					Intent intent = new Intent();
					intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA, ecChannelDlzmEntity);
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
		channelUtil.saveChannelData(channelEntity, EcChannelDlzmEntity.class, id,
				commentChannelViewHolder);
	}

	// 保存
	protected EcChannelDlzmEntity saveData() {
		String yxbh = viewHolder.getIncomRunningNo().getEdtTextValue();
		if (ecChannelDlzmEntity == null) {
			ecChannelDlzmEntity = new EcChannelDlzmEntity();
			ecChannelDlzmEntity.setObjId(id);
			ecChannelDlzmEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecChannelDlzmEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecChannelDlzmEntity.setCjsj(DateUtils.getDate());
		}
		ecChannelDlzmEntity.setGxsj(DateUtils.getDate());
//		if (GlobalEntry.ssds) {
			String ssds = viewHolder.getCommentChannelViewHolder().getInconSsds().getEdtTextValue();
			ecChannelDlzmEntity.setSsds(ssds);
//		}
		ecChannelDlzmEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecChannelDlzmEntity.setYxbh(yxbh);
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.ZM.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(ecChannelDlzmEntity.getObjId());
			channelLabelEntity.setObjId(ecChannelDlzmEntity.getObjId());
			channelLabelEntity.setTypeNo(ResourceEnum.ZM.getValue() + "");
			channelLabelEntity.setEcLineLabelEntityVo(ecLineLabelEntityVo);
		}
		return ecChannelDlzmEntity;
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
