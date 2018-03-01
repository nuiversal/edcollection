package com.winway.android.edcollection.adding.util;

import android.app.Activity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import com.winway.android.edcollection.R;
import com.winway.android.edcollection.adding.controll.BasicInfoControll;
import com.winway.android.edcollection.base.adapter.InputSelectAdapter;
import com.winway.android.ewidgets.input.InputComponent;
import com.winway.android.ewidgets.input.InputSelectComponent;
import com.winway.android.util.DeviceUtils;
import com.winway.android.util.ToastUtils;

public class NamingConventionsFloatWin {
	private View dragView = null;
	// 头部模块
	private ImageView ivClose;
	private LinearLayout llview;
	private TextView tv_nameMethod;
	private TextView tv_namePrinciple;
	private TextView tv_nameConventions;
	private LinearLayout ll_NamingConventions;
	private ImageView iv_NamingConventions_more;
	private InputComponent inCom_namingConventions_xzq;
	private InputComponent inCom_namingConventions_dlm;
	private InputSelectComponent insc_namingConventions_fx;
	private InputComponent inCom_namingConventions_tdlx;
	private InputComponent inCom_namingConventions_d;
	private Button btn_namingConventions_confirm;
	private boolean isShowNamingConventions = true;//是否显示命名规范
	private String nameMethod=null;
	private String namePrinciple=null;
	private String nameConventionsTitle=null;
	private List<String> directionList;//方向
	private String channelType;//通道类型


	// 单例
	private static NamingConventionsFloatWin _instance;

	private NamingConventionsFloatWin() {
	}

	public static NamingConventionsFloatWin getInstance() {
		if (_instance == null) {
			synchronized (NamingConventionsFloatWin.class) {
				if (_instance == null) {
					_instance = new NamingConventionsFloatWin();
				}
			}
		}
		return _instance;
	}

	private Activity mActivity;
	private RelativeLayout nameView;
	private InputComponent icChannelName;
	public void showNamingConvientionsFloatWin(Activity mActivity,RelativeLayout nameView,InputComponent channelName) {
		this.mActivity = mActivity;
		this.nameView = nameView;
		this.icChannelName = channelName;
		createFloatWin();
		initData();
		initEvent();
	}
	private void initData() {
		// TODO Auto-generated method stub
		if(nameMethod!=null){//命名方式
			tv_nameMethod.setText(nameMethod);
		}
		if(namePrinciple!=null){//命名原则
			tv_namePrinciple.setText(namePrinciple);
		}
		if(nameConventionsTitle!=null){//标题
			tv_nameConventions.setText(nameConventionsTitle);
		}
		if(BasicInfoControll.BMaplocationDistrict!=null){//行政区
			inCom_namingConventions_xzq.setEdtText(BasicInfoControll.BMaplocationDistrict);
		}
		if(BasicInfoControll.BMaplocationStreet!=null){//道路名
			inCom_namingConventions_dlm.setEdtText(BasicInfoControll.BMaplocationStreet);
		}
		if(directionList!=null && directionList.size()>0){//方向
			InputSelectAdapter inputSelectAdapter = new InputSelectAdapter(mActivity, directionList);
			insc_namingConventions_fx.setAdapter(inputSelectAdapter);
		}
		if(channelType!=null){//通道类型
			if(channelType.equals("电缆拖拉管/顶管")){
				inCom_namingConventions_tdlx.setEdtText(channelType.substring(channelType.length()-2));
			}else {
				inCom_namingConventions_tdlx.setEdtText(channelType);
			}
		}
		inCom_namingConventions_d.getEditTextView().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_PHONE);
	}

	private void createFloatWin() {
//		if (dragView == null) {
			dragView = View.inflate(mActivity, R.layout.work_well_conventions, null);
			llview = (LinearLayout) dragView.findViewById(R.id.ll_work_well_conventions_dragView);
			ivClose = (ImageView) dragView.findViewById(R.id.iv_work_well_conventions_close);
			tv_nameConventions = (TextView)dragView.findViewById(R.id.tv_work_well_conventions_title_name);
			tv_nameMethod = (TextView)dragView.findViewById(R.id.tv_name_method);
			tv_namePrinciple = (TextView)dragView.findViewById(R.id.tv_name_principle);
			ll_NamingConventions = (LinearLayout) dragView.findViewById(R.id.ll_NamingConventions);
			iv_NamingConventions_more = (ImageView) dragView.findViewById(R.id.iv_NamingConventions_more);
			inCom_namingConventions_xzq = (InputComponent) dragView.findViewById(R.id.inCom_namingConventions_xzq);
			inCom_namingConventions_dlm = (InputComponent) dragView.findViewById(R.id.inCom_namingConventions_dlm);
			insc_namingConventions_fx = (InputSelectComponent) dragView.findViewById(R.id.insc_namingConventions_fx);
			inCom_namingConventions_tdlx = (InputComponent) dragView.findViewById(R.id.inCom_namingConventions_tdlx);
			inCom_namingConventions_d = (InputComponent) dragView.findViewById(R.id.inCom_namingConventions_d);
			btn_namingConventions_confirm = (Button) dragView.findViewById(R.id.btn_namingConventions_confirm);
			int width = DeviceUtils.getScreenWidth(mActivity);
			int height = DeviceUtils.getScreenHeight(mActivity);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
			dragView.setLayoutParams(params);
			MoveViewUtils.setMoveView(mActivity, llview);
//			nameView.setPadding(0, 40, 0, 0);
			nameView.addView(dragView);
//		}
		dragView.setVisibility(View.VISIBLE);
	}

	private void initEvent() {
		ivClose.setOnClickListener(ocl);
		ll_NamingConventions.setOnClickListener(ocl);
		btn_namingConventions_confirm.setOnClickListener(ocl);
	}

	private View.OnClickListener ocl = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.iv_work_well_conventions_close :{
					dragView.setVisibility(View.GONE);
//					dragView = null;
					break;
				}
				case R.id.ll_NamingConventions:{
					if(isShowNamingConventions){
							iv_NamingConventions_more.setBackgroundResource(R.drawable.more_up);
							tv_nameMethod.setText(R.string.channel_name_method);
							tv_namePrinciple.setText(R.string.workwell_name_principle);
							tv_nameMethod.setVisibility(View.VISIBLE);
							tv_namePrinciple.setVisibility(View.VISIBLE);
							isShowNamingConventions = false;
					}else {
						iv_NamingConventions_more.setBackgroundResource(R.drawable.more_down);
						tv_nameMethod.setVisibility(View.GONE);
						tv_namePrinciple.setVisibility(View.GONE);
						isShowNamingConventions = true;
					}
					break;
				}
				case R.id.btn_namingConventions_confirm:{
					if(inCom_namingConventions_xzq.getEdtTextValue().equals("")){
						ToastUtils.show(mActivity, "行政区未填写");
						return ;
					}
					if(inCom_namingConventions_dlm.getEdtTextValue().equals("")){
						ToastUtils.show(mActivity, "道路名未填写");
						return;
					}
					if(inCom_namingConventions_tdlx.getEdtTextValue().equals("")){
						ToastUtils.show(mActivity, "通道类型未填写");
					}
					if(insc_namingConventions_fx.getEdtTextValue().equals("")){
						ToastUtils.show(mActivity, "方向未填写");
						return;
					}
					if(inCom_namingConventions_d.getEdtTextValue().equals("")){
						ToastUtils.show(mActivity, "通道段未填写");
						return;
					}
					setChannelName();
				}
				default :
					break;
			}
		}
	};
	/**
	 * 设置通道名称
	 */
	public void setChannelName(){
		String channelName = inCom_namingConventions_xzq.getEdtTextValue()+"-"+inCom_namingConventions_dlm.getEdtTextValue()+"-"
				+insc_namingConventions_fx.getEdtTextValue()+"-"+inCom_namingConventions_tdlx.getEdtTextValue()+"-"+inCom_namingConventions_d.getEdtTextValue()+"段";
		icChannelName.setEdtText(channelName);
		dragView.setVisibility(View.GONE);
	}
	
	public String getNameMethod() {
		return nameMethod;
	}

	public void setNameMethod(String nameMethod) {
		this.nameMethod = nameMethod;
	}

	public String getNamePrinciple() {
		return namePrinciple;
	}

	public void setNamePrinciple(String namePrinciple) {
		this.namePrinciple = namePrinciple;
	}

	public String getNameConventionsTitle() {
		return nameConventionsTitle;
	}

	public void setNameConventionsTitle(String nameConventionsTitle) {
		this.nameConventionsTitle = nameConventionsTitle;
	}
	public List<String> getDirectionList() {
		return directionList;
	}

	public void setDirectionList(List<String> directionList) {
		this.directionList = directionList;
	}
	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
}
