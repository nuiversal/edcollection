package com.winway.android.edcollection.login.controll;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.base.GlobalEntry;
import com.winway.android.edcollection.base.view.BaseControll;
import com.winway.android.edcollection.login.bll.SettingServerBill;
import com.winway.android.edcollection.login.entity.ServerInfo;
import com.winway.android.edcollection.login.viewholder.SettingServerAddressHolder;
import com.winway.android.util.FileUtil;
import com.winway.android.util.PreferencesUtils;
import com.winway.android.util.SharedPreferencesUtils;
import com.winway.android.util.ToastUtil;

/**
 * 
 * @author xs
 *
 */
public class SettingServerAddressControll extends BaseControll<SettingServerAddressHolder> {
	private SettingServerBill settingServerBill;// 设置服务器业务逻辑
	private ServerInfo serverInfo;
	private Switch btn;

	@Override
	public void init() {
		settingServerBill = new SettingServerBill(getActivity());
		initData();
		initEvent();
		initSwitch();
	}

	private void initSwitch() {
		btn = new Switch(mActivity);
		btn.setTextColor(0xffffffff);
		btn.setThumbResource(R.drawable.switch_bg);
		btn.setTrackResource(R.drawable.switch_selector);
		btn.setTextSize(18);
		btn.setTextOff("内置");
		btn.setTextOn("外置");
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		btn.setOnTouchListener(new Switch.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}
		});
		btn.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				changeDir(isChecked);
			}
		});
		getViewHolder().getRlChangerDir().addView(btn, lp);
		GlobalEntry.useTfcard = Boolean
				.parseBoolean(SharedPreferencesUtils.get(mActivity, GlobalEntry.IS_USE_TFCARD, false).toString());
		btn.setChecked(GlobalEntry.useTfcard);
	}
	
	
	private void changeDir(boolean isChecked) {
		if (isChecked) { //使用TF卡
			//检查是否有Tf卡
			String path = getTFCardPath(mActivity);
			if (TextUtils.isEmpty(path)) {// 判断tf卡是否存在
				ToastUtil.show(mActivity, "没有找到TF卡", 200);
				SharedPreferencesUtils.put(mActivity, GlobalEntry.IS_USE_TFCARD, false);
				btn.setChecked(false);
			} else {
				GlobalEntry.useTfcard = true;
				SharedPreferencesUtils.put(mActivity, GlobalEntry.IS_USE_TFCARD, true);
			}
		}else { //不使用TF卡
			GlobalEntry.useTfcard = false;
			SharedPreferencesUtils.put(mActivity, GlobalEntry.IS_USE_TFCARD, false);
		}
	}
	
	/**
	 * 获取tf卡路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getTFCardPath(Context context) {
		String sdPathIn = FileUtil.getSDCardPath();// 系统使用的路径
		List<String> sdPaths = FileUtil.getTFCardPaths();// 全部卡的路径
		if (sdPaths != null) {
			if (sdPaths.isEmpty()) {
				// 没有找到tf卡
				return null;
			} else {
				// 有tf卡
				String path = sdPaths.get(sdPaths.size() - 1);// tf卡路径为最后一个
				if (sdPathIn.equals(path)) {
					// tf卡与系统使用的卡路径一样
				} else {
					// tf卡与系统使用的卡路径不一样
				}
				return path;
			}
		}
		return null;
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 初始化输入框
		PreferencesUtils.PREFERENCE_NAME = "serverinfo&port";
		viewHolder.getInConServerLoginAddr()
				.setEdtText(PreferencesUtils.getString(mActivity, "loginServerAddr", GlobalEntry.loginServerUrl));
		viewHolder.getInConServerDataServiceAddr()
				.setEdtText(PreferencesUtils.getString(mActivity, "dataServerAddr", GlobalEntry.dataServerUrl));
		viewHolder.getInConServerFileConnectService()
				.setEdtText(PreferencesUtils.getString(mActivity, "fileConnectServerAddr", GlobalEntry.fileServerUrl));
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		viewHolder.getServerSettingHead().getReturnView().setOnClickListener(ocl);
		viewHolder.getServerSettingHead().getOkView().setOnClickListener(ocl);
		viewHolder.getRgServerZcxz().setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId==viewHolder.getRbServerZcxzNw().getId()){
					GlobalEntry.zcxz=true;
				}else if(checkedId==viewHolder.getRbServerZcxzGw().getId()){
					GlobalEntry.zcxz=false;
				}
			}
		});
	}
	
	OnClickListener ocl = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.tv_head_item_return:{//返回
				mActivity.finish();
				break;
			}
			case R.id.tv_head_item_ok:{//确定
				String regex = "http://(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]):(\\d|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])/";
				String loginAddr = viewHolder.getInConServerLoginAddr().getEdtTextValue().trim();
				String dataAddr = viewHolder.getInConServerDataServiceAddr().getEdtTextValue().trim();
				String fileAddr = viewHolder.getInConServerFileConnectService().getEdtTextValue().trim();
				if (loginAddr.equals("")) {
					ToastUtil.show(mActivity, "请输入登录地址", Toast.LENGTH_LONG);
				} else if (dataAddr.equals("")) {
					ToastUtil.show(mActivity, "请输入数据服务接口地址", Toast.LENGTH_LONG);
				} else if (fileAddr.equals("")) {
					ToastUtil.show(mActivity, "请输入文件通讯服务地址", Toast.LENGTH_LONG);
				} else if (!loginAddr.matches(regex) || !dataAddr.matches(regex) || !fileAddr.matches(regex)) {
					ToastUtil.show(mActivity, "请输入正确的ip和端口号", Toast.LENGTH_LONG);
				} else {
					serverInfo = new ServerInfo();
					serverInfo.setLoginServerAddr(loginAddr);
					serverInfo.setDataServerAddr(dataAddr);
					serverInfo.setFileConnectServerAddr(fileAddr);
					settingServerBill.saveServerInfo(serverInfo);
					GlobalEntry.loginServerUrl = loginAddr;
					GlobalEntry.dataServerUrl = dataAddr;
					GlobalEntry.fileServerUrl = fileAddr;
					mActivity.finish();
				}
				break;
			}
			default:
				break;
			}
		}
	};

}
