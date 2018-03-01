package com.winway.android.edcollection.adding.controll;

import java.io.Serializable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.activity.ChannelDGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLCActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLGDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLQActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLSDActivity;
import com.winway.android.edcollection.adding.activity.ChannelDLZMActivity;
import com.winway.android.edcollection.adding.activity.ChannelJkActivity;
import com.winway.android.edcollection.adding.bll.ChannelBll;
import com.winway.android.edcollection.adding.bll.CollectCommonBll;
import com.winway.android.edcollection.adding.util.TreeRefleshUtil;
import com.winway.android.edcollection.adding.viewholder.ChannelEditViewholder;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.edcollection.base.entity.DataLogOperatorType;
import com.winway.android.edcollection.base.entity.ResourceEnum;
import com.winway.android.edcollection.base.entity.TableNameEnum;
import com.winway.android.edcollection.base.entity.WhetherEnum;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.project.entity.EcChannelDgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlcEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlgdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlqEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlsdEntity;
import com.winway.android.edcollection.project.entity.EcChannelDlzmEntity;
import com.winway.android.edcollection.project.entity.EcChannelEntity;
import com.winway.android.edcollection.project.entity.EcChannelJkEntity;
import com.winway.android.util.AndroidBasicComponentUtils;

/**
 * 通道修改
 * 
 * @author lyh
 * @version 创建时间：2017年4月1日
 *
 */
public class ChannelEditControll extends BaseControll<ChannelEditViewholder> {

	private CollectCommonBll commonBll = null;
	private ChannelBll channelBll = null;
	private EcChannelEntity channel;
	private final int CHANNEL_ATTR_REQUESTCODE = 1;
	private final int CHANNEL_TYPE_REQUESTCODE = 2;
	private Object attrChannelData;
	private Object typeChannelData;
	private Object oldTypeChannelData;
	private String channeltype;
	private int state; // 1表示是通道属性修改 2表示是通道类型修改
	private boolean isclick = false; // 判断通道类型修改是否点击过

	@Override
	public void init() {
		commonBll = new CollectCommonBll(mActivity, GlobalEntry.prjDbUrl);
		channelBll = new ChannelBll(mActivity, GlobalEntry.prjDbUrl);
		initDatas();
		initEvent();
	}

	/**
	 * 初始化数据
	 */
	private void initDatas() {
		viewHolder.getInscChanneyType().getEditTextView().setFocusable(false);
		Intent intent = getIntent();
		channel = (EcChannelEntity) intent.getSerializableExtra("channelEntity");
		attrChannelData = intent.getSerializableExtra("channelTypeEntity");
		oldTypeChannelData = intent.getSerializableExtra("channelTypeEntity");
		channeltype = (String) intent.getSerializableExtra("channelType");
		if (channel != null) {
			viewHolder.getTvChannelName().setText(channel.getName());
		}
		viewHolder.getTvChannelType().setText(channeltype);
		// 初始化通道类型下拉列表
		List<String> channelTypes = commonBll.getChannelTypeList();
		final InputSelectAdapter channelTypeAdapter = new InputSelectAdapter(mActivity, channelTypes);
		viewHolder.getInscChanneyType().setAdapter(channelTypeAdapter);
		viewHolder.getInscChanneyType().setEdtTextValue(channeltype);
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		viewHolder.getHcHead().getOkView().setOnClickListener(orcl);
		viewHolder.getHcHead().getReturnView().setOnClickListener(orcl);
		viewHolder.getBtnAttrEdit().setOnClickListener(orcl);
		viewHolder.getBtnTypeEdit().setOnClickListener(orcl);
	}

	private OnClickListener orcl = new OnClickListener() {

		@SuppressLint("ResourceAsColor")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_head_item_return: // 返回
			{
				mActivity.finish();
			}
				break;
			case R.id.tv_head_item_ok: // 确定
			{
				if (state == 1) { // 表示是通道属性修改
					updateChannelTypeDatas();
				} else if (state == 2) { // 表示是通道类型修改
					channelBll.updateChannelType(channel, typeChannelData, oldTypeChannelData);
				}
				// 刷新台账树
				TreeRefleshUtil singleton = TreeRefleshUtil.getSingleton();
				if (null != singleton) {
					singleton.refleshWholeTree();
				}
				mActivity.finish();
			}
				break;
			case R.id.btn_update_channel_attrUpdate: // 通道属性修改
			{
				if (isclick == true) {
					viewHolder.getBtnAttrEdit().setBackgroundColor(R.color.gray);
					viewHolder.getBtnAttrEdit().setOnClickListener(null);
					return;
				}
				channel.setChannelType(channeltype);
				launchActivityForResult(channel, CHANNEL_ATTR_REQUESTCODE, attrChannelData);
			}
				break;
			case R.id.btn_update_channel_typeUpdate: // 通道类型修改
			{
				String channeytype = viewHolder.getInscChanneyType().getEdtTextValue();
				channel.setChannelType(channeytype);
				if (channeytype.equals(ResourceEnum.TLG.getName())) {
					typeChannelData = new EcChannelDgEntity();
					((EcChannelDgEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelDgEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelDgEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelDgEntity) typeChannelData).setCjsj(channel.getCreateTime());
				} else if (channeytype.equals(ResourceEnum.ZM.getName())) {
					typeChannelData = new EcChannelDlzmEntity();
					((EcChannelDlzmEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelDlzmEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelDlzmEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelDlzmEntity) typeChannelData).setCjsj(channel.getCreateTime());
				} else if (channeytype.equals(ResourceEnum.GD.getName())) {
					typeChannelData = new EcChannelDlgEntity();
					((EcChannelDlgEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelDlgEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelDlgEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelDlgEntity) typeChannelData).setCjsj(channel.getCreateTime());
				} else if (channeytype.equals(ResourceEnum.PG.getName())) {
					typeChannelData = new EcChannelDlgdEntity();
					((EcChannelDlgdEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelDlgdEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelDlgdEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelDlgdEntity) typeChannelData).setCjsj(channel.getCreateTime());
				} else if (channeytype.equals(ResourceEnum.QJ.getName())) {
					typeChannelData = new EcChannelDlqEntity();
					((EcChannelDlqEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelDlqEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelDlqEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelDlqEntity) typeChannelData).setCjsj(channel.getCreateTime());
				} else if (channeytype.equals(ResourceEnum.SD.getName())) {
					typeChannelData = new EcChannelDlsdEntity();
					((EcChannelDlsdEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelDlsdEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelDlsdEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelDlsdEntity) typeChannelData).setCjsj(channel.getCreateTime());
				} else if (channeytype.equals(ResourceEnum.JKXL.getName())) {
					typeChannelData = new EcChannelJkEntity();
					((EcChannelJkEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelJkEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelJkEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelJkEntity) typeChannelData).setCjsj(channel.getCreateTime());
				} else if (channeytype.equals(ResourceEnum.DLC.getName())) {
					typeChannelData = new EcChannelDlcEntity();
					((EcChannelDlcEntity) typeChannelData).setObjId(channel.getEcChannelId());
					((EcChannelDlcEntity) typeChannelData).setPrjid(channel.getPrjid());
					((EcChannelDlcEntity) typeChannelData).setOrgid(channel.getOrgid());
					((EcChannelDlcEntity) typeChannelData).setCjsj(channel.getCreateTime());
				}
				launchActivityForResult(channel, CHANNEL_TYPE_REQUESTCODE, typeChannelData);
			}
				break;

			default:
				break;
			}
		}

		/**
		 * 更新通道设备数据
		 */
		private void updateChannelTypeDatas() {
			if (attrChannelData != null) {
				if (channel != null) {
					commonBll.saveOrUpdate(channel);
					commonBll.saveOrUpdate(attrChannelData);
					String type = channel.getChannelType();
					if (type.equals(ResourceEnum.ZM.getName())) {
						commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLZM.getTableName(),
								DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
					} else if (type.equals(ResourceEnum.TLG.getName())) {
						commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL_DG.getTableName(),
								DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
					} else if (type.equals(ResourceEnum.GD.getName())) {
						commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLG.getTableName(),
								DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
					} else if (type.equals(ResourceEnum.PG.getName())) {
						commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLGD.getTableName(),
								DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
					} else if (type.equals(ResourceEnum.SD.getName())) {
						commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLSD.getTableName(),
								DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
					} else if (type.equals(ResourceEnum.QJ.getName())) {
						commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL_DLQ.getTableName(),
								DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
					} else if (type.equals(ResourceEnum.JKXL.getName())) {
						commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL_JK.getTableName(),
								DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
					}
					commonBll.handleDataLog(channel.getEcChannelId(), TableNameEnum.EC_CHANNEL.getTableName(),
							DataLogOperatorType.update, WhetherEnum.NO, "编辑" + type, false);
				}
			}
		}

		/**
		 * 界面跳转
		 * 
		 * @param channelEntity
		 *            通道实体
		 * @param requestcode
		 *            请求码
		 * @param channelData
		 *            通道设备数据
		 */
		private void launchActivityForResult(EcChannelEntity channelEntity, int requestcode, Object channelData) {
			if (channelData != null) {
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable(GlobalEntry.INTENT_KEY_CHANNEL_DATA, (Serializable) channelData);
				bundle.putSerializable(GlobalEntry.INTENT_KEY_CHANNEL, channelEntity);
				intent.putExtras(bundle);
				if (channelData instanceof EcChannelDgEntity) {
					intent.setClass(mActivity, ChannelDGActivity.class);
				} else if (channelData instanceof EcChannelDlgEntity) {
					intent.setClass(mActivity, ChannelDLGActivity.class);
				} else if (channelData instanceof EcChannelDlgdEntity) {
					intent.setClass(mActivity, ChannelDLGDActivity.class);
				} else if (channelData instanceof EcChannelDlqEntity) {
					intent.setClass(mActivity, ChannelDLQActivity.class);
				} else if (channelData instanceof EcChannelDlsdEntity) {
					intent.setClass(mActivity, ChannelDLSDActivity.class);
				} else if (channelData instanceof EcChannelDlzmEntity) {
					intent.setClass(mActivity, ChannelDLZMActivity.class);
				} else if (channelData instanceof EcChannelJkEntity) {
					intent.setClass(mActivity, ChannelJkActivity.class);
				} else if (channelData instanceof EcChannelDlcEntity) {
					intent.setClass(mActivity, ChannelDLCActivity.class);
				}
				AndroidBasicComponentUtils.launchActivityForResult(mActivity, intent, requestcode);
			}
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == CHANNEL_ATTR_REQUESTCODE) { // 通道属性修改中传过来的
				if (channel != null) {
					attrChannelData = data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
					channel = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
					if (channel != null) {
						viewHolder.getTvChannelName().setText(channel.getName());
					}
					if (attrChannelData != null) {
						state = 1;
					}
				}
			} else if (requestCode == CHANNEL_TYPE_REQUESTCODE) { // 通道类型修改中传过来的
				typeChannelData = data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL_DATA);
				channel = (EcChannelEntity) data.getSerializableExtra(GlobalEntry.INTENT_KEY_CHANNEL);
				if (typeChannelData != null) {
					state = 2;
					isclick = true;
				}
			}
		}
	};
}
