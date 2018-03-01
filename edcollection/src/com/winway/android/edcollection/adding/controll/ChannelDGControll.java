package com.winway.android.edcollection.adding.controll;

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
import com.winway.android.edcollection.adding.viewholder.ChannelDGViewHolder;
import com.winway.android.edcollection.adding.viewholder.CommentChannelViewHolder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.bll.GlobalCommonBll;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
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
 * 拖拉管、顶管
 * 
 * @author zgq
 *
 */
public class ChannelDGControll extends BaseControll<ChannelDGViewHolder> {

	private EcChannelEntity channelEntity;
	private String id = UUIDGen.randomUUID();
	private EcChannelDgEntity ecChannelDgEntity;
	private GlobalCommonBll globalCommonBll;
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
		globalCommonBll = new GlobalCommonBll(mActivity, prjGDBUrl);
		channelUtil = new CommentChannelUtil(mActivity, prjGDBUrl);
		initDatas();
		initEvents();
		initSettings();
	}

	private void initSettings() {
		// TODO Auto-generated method stub
		viewHolder.getInComPipeJackKd().getEditTextView()
				.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
		viewHolder.getInComPipeJackTlgsl().getEditTextView().setInputType(InputType.TYPE_CLASS_NUMBER);
		viewHolder.getInComPipeJackTlggj().getEditTextView()
				.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
	}

	private void initEvents() {
		viewHolder.getCommentChannelViewHolder().getBtnLinkLabelLink().setOnClickListener(ocl);
		viewHolder.getInScPipeJackYwdw().getEditTextView().setEnabled(false);
		// 用户不能输入起点设备编号
		commentChannelViewHolder.getInconQdsbbh().getEditTextView().setEnabled(false);
		// TODO Auto-generated method stub
		viewHolder.getHcHead().getReturnView().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_CANCELED, intent);
				mActivity.finish();
			}
		});

		viewHolder.getHcHead().getOkView().setOnClickListener(ocl);
		viewHolder.getCommentChannelViewHolder().getBtnNamindConventions().setOnClickListener(ocl);
	}

	private OnClickListener ocl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: { // 返回
				Intent intent = new Intent();
				// 任务明细设置状态时调用
				intent.putExtra("isTask", true);
				mActivity.setResult(Activity.RESULT_CANCELED, intent);
				mActivity.finish();
				break;
			}

			case R.id.btn_link_label_label: {// 标签采集
				if (viewHolder.getCommentChannelViewHolder().getInconMc().getEdtTextValue().equals("")) {
					ToastUtil.show(mActivity, "请先输入通道名称！", 200);
					break;
				}
				Intent intent = new Intent(mActivity, LableActivity.class);
				if (channelLabelEntity != null) {
					EcLineLabelEntityVo ecLineLabelEntityVo = channelLabelEntity.getEcLineLabelEntityVo();
					if (ecLineLabelEntityVo != null) {
						intent.putExtra("LableEntity", ecLineLabelEntityVo);
					}
				} else {
					intent.putExtra("LableEntity", ecLineLabelEntityVo);
				}
				intent.putExtra("devName",
						viewHolder.getCommentChannelViewHolder().getInconMc().getEdtTextValue().trim());
				intent.putExtra("devType", ResourceEnum.TLG.getName());
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, BQ_RequestCode);
				break;
			}
			case R.id.tv_head_item_ok: { // 确定
				if (!channelUtil.checkIsNull(mActivity, commentChannelViewHolder)) {
					return;
				}
				CheckInputChannelNameUtil nameUtil = CheckInputChannelNameUtil.getInstance(mActivity,
						GlobalEntry.prjDbUrl);
				if (nameUtil.isHasChannelName(viewHolder.getCommentChannelViewHolder().getInconMc().getEdtTextValue(),
						channelEntity)) {
					// channelEntity = null;
					ToastUtils.show(mActivity, "此通道名称已经存在，请重新填写");
					// 获取焦点
					viewHolder.getCommentChannelViewHolder().getInconMc().getEditTextView().requestFocus();
					return;
				}
				saveChannelData();
				saveData();
				saveEndNodeOid();
				if (ecChannelDgEntity == null) {
					return;
				} else if (channelEntity == null) {
					return;
				} else if (endNodeOid == null) {
					return;
				}
				Intent intent = new Intent();
				intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA, ecChannelDgEntity);
				intent.putExtra(GlobalEntry.INTENT_KEY_CHANNEL, channelEntity);
				intent.putExtra("channelLabelEntity", channelLabelEntity);
				intent.putExtra("endNodeOid", endNodeOid);
				mActivity.setResult(Activity.RESULT_OK, intent);
				mActivity.finish();

				break;
			}

			case R.id.btn_namingConventions: {// 命名规范
				globalCommonBll.showNamngConvientionsFloatWin(mActivity, viewHolder.getRlNameConventions(),
						viewHolder.getCommentChannelViewHolder().getInconMc());
				break;
			}

			default:
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
		channelUtil.saveChannelData(channelEntity, EcChannelDgEntity.class, id, commentChannelViewHolder);
	}

	// 保存
	protected void saveData() {
		// TODO Auto-generated method stub
		String yxbh = viewHolder.getInComPipeJackYxbh().getEdtTextValue();
		String tdmc = viewHolder.getCommentChannelViewHolder().getInconMc().getEdtTextValue();
		String jgxs = viewHolder.getInscPipeJackJgxs().getEdtTextValue();
		String zx = viewHolder.getInComPipeJackZx().getEdtTextValue();
		String ltgx = viewHolder.getInComPipeJackLtgx().getEdtTextValue();
		String hxpm = viewHolder.getInComPipeJackHxpm().getEdtTextValue();
		String zxpm = viewHolder.getInComPipeJackZxpm().getEdtTextValue();
		String kd = viewHolder.getInComPipeJackKd().getEdtTextValue();
		String tlgsl = viewHolder.getInComPipeJackTlgsl().getEdtTextValue();
		String tlggj = viewHolder.getInComPipeJackTlggj().getEdtTextValue();
		String gccz = viewHolder.getInComPipeJackGccz().getEdtTextValue();
		if (ecChannelDgEntity == null) {
			ecChannelDgEntity = new EcChannelDgEntity();
			ecChannelDgEntity.setObjId(id);
			ecChannelDgEntity.setOrgid(GlobalEntry.currentProject.getOrgId());
			ecChannelDgEntity.setPrjid(GlobalEntry.currentProject.getEmProjectId());
			ecChannelDgEntity.setCjsj((DateUtils.getDate()));
		}
		ecChannelDgEntity.setGxsj((DateUtils.getDate()));
		ecChannelDgEntity.setYxbh(yxbh);
		// if (GlobalEntry.ssds) {
		String ssds = viewHolder.getCommentChannelViewHolder().getInconSsds().getEdtTextValue();
		ecChannelDgEntity.setSsds(ssds);
		// }
		ecChannelDgEntity.setYwdw(GlobalEntry.loginResult.getOrgid());
		ecChannelDgEntity.setTdmc(tdmc);
		ecChannelDgEntity.setZcd(channelEntity.equals("") ? null : channelEntity.getChannelLength());
		ecChannelDgEntity.setJgxs(jgxs);
		ecChannelDgEntity.setZx(zx);
		ecChannelDgEntity.setLtgx(ltgx);
		ecChannelDgEntity.setHxpm(hxpm);
		ecChannelDgEntity.setZxpm(zxpm);
		ecChannelDgEntity.setKd(kd.isEmpty() ? null : Double.parseDouble(kd));
		ecChannelDgEntity.setTlgsl(tlgsl.isEmpty() ? null : Integer.parseInt(tlgsl));
		ecChannelDgEntity.setTlggj(tlggj.isEmpty() ? null : Double.parseDouble(tlggj));
		ecChannelDgEntity.setGccz(gccz);
		// 保存标签采集的数据
		if (ecLineLabelEntityVo != null) {
			ecLineLabelEntityVo.setDeviceType(ResourceEnum.TLG.getValue() + "");
			ecLineLabelEntityVo.setDevObjId(ecChannelDgEntity.getObjId());
			channelLabelEntity.setObjId(ecChannelDgEntity.getObjId());
			channelLabelEntity.setTypeNo(ResourceEnum.TLG.getValue() + "");
			channelLabelEntity.setEcLineLabelEntityVo(ecLineLabelEntityVo);
		}
	}

	// 初始化数据
	private void initDatas() {
		channelUtil.initCommentSetting(commentChannelViewHolder, mActivity, globalCommonBll);
		if (GlobalEntry.loginResult != null) {
			viewHolder.getInScPipeJackYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
		}
		// 所属地市是否显示
		// if (GlobalEntry.ssds) {
		// viewHolder.getCommentChannelViewHolder().getInconSsds().setVisibility(View.VISIBLE);
		// }
		// 初始结构形式下拉列表
		String paramTypeNo = "010606002";// 结构形式
		List<String> jgxsDatas = globalCommonBll.getDictionaryNameList(paramTypeNo);
		if (jgxsDatas != null) {
			InputSelectAdapter jgxsAdapter = new InputSelectAdapter(mActivity, jgxsDatas);
			viewHolder.getInscPipeJackJgxs().setAdapter(jgxsAdapter);
		}
		// 是否是编辑
		Intent intent = getIntent();
		ecChannelDgEntity = (EcChannelDgEntity) intent.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
		channelEntity = (EcChannelEntity) intent.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
		channelLabelEntity = (ChannelLabelEntity) intent.getSerializableExtra("channelLabelEntity");
		if (channelEntity != null) {
			channelUtil.updateData(channelEntity, commentChannelViewHolder);
		}
		if (ecChannelDgEntity != null) {
			// 作用是当从通道修改界面中点击通道类型修改按钮进来此界面时，下拉框行不会为空
			if (ecChannelDgEntity.getJgxs() == null) {
				return;
			}
			viewHolder.getCommentChannelViewHolder().getInconSsds()
					.setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getSsds()));
			viewHolder.getInComPipeJackYxbh().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getYxbh()));
			viewHolder.getInScPipeJackYwdw().setEdtTextValue(GlobalEntry.loginResult.getOrgname());
			viewHolder.getCommentChannelViewHolder().getInconMc()
					.setEdtText(channelEntity == null ? "" : StringUtils.nullStrToEmpty(channelEntity.getName()));
			viewHolder.getInscPipeJackJgxs().setEdtTextValue(StringUtils.nullStrToEmpty(ecChannelDgEntity.getJgxs()));
			viewHolder.getInComPipeJackZx().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getZx()));
			viewHolder.getInComPipeJackLtgx().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getLtgx()));
			viewHolder.getInComPipeJackHxpm().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getHxpm()));
			viewHolder.getInComPipeJackZxpm().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getZxpm()));
			viewHolder.getInComPipeJackKd().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getKd()));
			viewHolder.getInComPipeJackTlgsl().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getTlgsl()));
			viewHolder.getInComPipeJackTlggj().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getTlggj()));
			viewHolder.getInComPipeJackGccz().setEdtText(StringUtils.nullStrToEmpty(ecChannelDgEntity.getGccz()));
			if (jgxsDatas != null) {
				for (int i = 0; i < jgxsDatas.size(); i++) {
					String jgxs = jgxsDatas.get(i);
					if (jgxs.equals(ecChannelDgEntity.getJgxs())) {
						viewHolder.getInscPipeJackJgxs().setSelect(i);
						break;
					}
				}
			}
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
